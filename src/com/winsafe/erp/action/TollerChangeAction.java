package com.winsafe.erp.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.erp.action.form.SapCodeForm;
import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppSapUploadLog;
import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.UploadSAPLog;
import com.winsafe.sap.service.SapFileUploadService;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.MD5BigFileUtil;

public class TollerChangeAction extends BaseAction {
	private static Logger logger = Logger.getLogger(TollerChangeAction.class);

	SapFileUploadService sapFileUploadService = new SapFileUploadService();
	AppPrepareCode  appp = new AppPrepareCode();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		boolean hasError = false;
		String resultMsg = "";
		
		try {
			SapCodeForm sapCodeFile = (SapCodeForm) form;
	
			//检查上传的文件
			FormFile sapFile = (FormFile) sapCodeFile.getSapFile();
			if (sapFile != null && !sapFile.equals("") && !hasError && sapFile.getContentType() != null) {
				if (!(sapFile.getFileName().toLowerCase().indexOf("txt") >= 0)) {
					hasError = true;
					resultMsg = "文件上传失败,文件后缀名不正确!";
				}
			} else {
				hasError = true;
				resultMsg = "文件未上传";
			}
			//检查是否已上传过
			String md5 = MD5BigFileUtil.md5(sapFile.getInputStream());
//			if(sapFileUploadService.isFileAlreadyExists(md5)) {
//				hasError = true;
//				resultMsg = "上传失败，已上传过相同的文件";
//			}
			String organId = sapCodeFile.getPlantCode();
			
			DBUserLog.addUserLog(request, "文件：" + sapFile.getFileName());
			
			if(hasError) {
				request.setAttribute("result", resultMsg);
				return mapping.findForward("failure");
			} else {
				SapFileType fileType = SapFileType.parse("91");
				String savePath = FileUploadUtil.getLotterTempPath() + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/" + fileType.getSapName() + "/";
		    	String saveName =  DateUtil.getCurrentDateTimeString() +  "_" + sapFile.getFileName().replace("_", "");
						    	
		    	//写文件1 ——源文件
				FileUploadUtil.saveUplodedFile(sapFile.getInputStream(), savePath, saveName);
				
				//写文件2 ——转换文件
				String saveName2 =  DateUtil.getCurrentDateTimeString() + "_result"+ "_" + sapFile.getFileName().replace("_", "");
				BufferedReader br = null;
				br = new BufferedReader(new InputStreamReader(sapFile.getInputStream(), "utf-8"));
				String readLine;
				int linenum = 0;
				int errnum = 0;
				StringBuffer turnsb = new StringBuffer();
				//HashSet判断重复上传
				Set<String> setCode = new HashSet<String>();
				Set<String> setBoxCode = new HashSet<String>();
				Map<String,String> cbmap = new HashMap<String,String>();
				while ((readLine = br.readLine()) != null) {
					linenum = linenum+1;
					if(readLine.length()<=22||!readLine.contains(" ")){
						turnsb.append(readLine+" 原文件第"+linenum+"行：该行格式错误！") ;
						errnum = errnum +1;
//						if(br!=null){
//							try {
//								br.close();
//							} catch (IOException e) {
//								logger.error("",e);
//							}
//						}
//						return mapping.findForward("failure");
						turnsb.append("\r\n");
					}else{
						String[]ss = readLine.split(" ");
						if(ss.length!=3){
							turnsb.append(readLine+" 原文件第"+linenum+"行：该行格式错误！") ;
							turnsb.append("\r\n");
							errnum = errnum +1;
							continue;
						}
						String flag = ss[1];
						String code = ss[2];
						
						if(StringUtil.isEmpty(ss[0])){
							turnsb.append(readLine+" 原文件第"+linenum+"行：错误,单号为空！") ;
							turnsb.append("\r\n");
							errnum = errnum +1;
						}else  if(StringUtil.isEmpty(flag)){
							turnsb.append(readLine+" 原文件第"+linenum+"行：错误,托箱标志为空！") ;
							turnsb.append("\r\n");
							errnum = errnum +1;
						}else  if(!flag.equals("C")&&!flag.equals("P")){
							turnsb.append(readLine+" 原文件第"+linenum+"行：错误,托箱标志错误！") ;
							turnsb.append("\r\n");
							errnum = errnum +1;
						}
						else  if(StringUtil.isEmpty(code)||code.length()!=20){
							turnsb.append(readLine+" 原文件第"+linenum+"行：条码"+code+"格式错误！") ;
							turnsb.append("\r\n");
							errnum = errnum +1;
						}
						else  if(!setCode.add(code)){
							turnsb.append(readLine+" 原文件第"+linenum+"行：错误,条码"+code+"重复上传！") ;
							turnsb.append("\r\n");
							errnum = errnum +1;
						}
						else  if(!setBoxCode.add(code)){
							turnsb.append(readLine+" 原文件第"+linenum+"行：错误,条码"+code+"所在托"+cbmap.get(code)+"已上传！") ;
							turnsb.append("\r\n");
							errnum = errnum +1;
						}
//						else if(!code.startsWith("0054052296")){
//							turnsb.append(readLine+" 原文件第"+linenum+"行：条码"+code+"错误！") ;
//							turnsb.append("\r\n");
//							errnum = errnum +1;
//						}
						else{
							AppCartonCode appCartonCode = new AppCartonCode();
							if(flag.equals("C")){
								if(!appCartonCode.isCartonCodeExists(code)){
									turnsb.append(readLine+" 原文件第"+linenum+"行：箱码"+code+" 查无信息！") ;
									turnsb.append("\r\n");
									errnum = errnum +1;
								}else{
									turnsb.append(ss[0]+"\t"+code);
									turnsb.append("\r\n");
								}
							}else{
								List<CartonCode> codes=appCartonCode.getByPalletCode(code);
								if(codes.size()>0){
									Set<String> setCodeNew = new HashSet<String>();
									setCodeNew.addAll(setCode);
									boolean temp = true;
									Set<String> setcartonCodeIn = new LinkedHashSet<String>();
									for(CartonCode cartonCode:codes){
										
										setBoxCode.add(cartonCode.getCartonCode());
										cbmap.put(cartonCode.getCartonCode(), code);
										
										if(!setCodeNew.add(cartonCode.getCartonCode())){
											setcartonCodeIn.add(cartonCode.getCartonCode());
//											turnsb.append(readLine+" 原文件第"+linenum+"行：托码"+code+"内箱码"+cartonCode.getCartonCode()+"已上传！") ;
//											turnsb.append("\r\n");
//											errnum = errnum +1;
											temp = false;
//											break;
										}
									}
									if(temp){
										for(CartonCode cartonCode:codes){
											turnsb.append(ss[0]+"\t"+cartonCode.getCartonCode());
											turnsb.append("\r\n");
											setBoxCode.add(cartonCode.getCartonCode());
											cbmap.put(cartonCode.getCartonCode(), code);
										}
									}else{
										String codeadd = "";
										for(String recode:setcartonCodeIn){
											codeadd = codeadd + recode + " ";
										}
										turnsb.append(readLine+" 原文件第"+linenum+"行：托码"+code+"内箱码 "+codeadd+"已上传！") ;
										turnsb.append("\r\n");
										errnum = errnum +1;
									}
								}else{
									turnsb.append(readLine+" 原文件第"+linenum+"行：托码"+code+" 查无信息！") ;
									turnsb.append("\r\n");
									errnum = errnum +1;
								}
							}
						}
					}
				}
				CreateFileWithMessage(turnsb.toString(),savePath,saveName2);
				
				// 生成SAP文件上传日志记录
				AppSapUploadLog appUploadSAPlog = new AppSapUploadLog();
				UploadSAPLog uploadSAPLog = new UploadSAPLog();
				uploadSAPLog.setFileName(sapFile.getFileName());
				uploadSAPLog.setFilePath(savePath + saveName);
				uploadSAPLog.setLogFilePath(savePath+saveName2);
				uploadSAPLog.setFileType(fileType.getDatabaseValue());
				uploadSAPLog.setErrorCount(errnum);
				uploadSAPLog.setTotalCount(linenum);
				uploadSAPLog.setMakeId(users.getUserid());
				uploadSAPLog.setMakeOrganId(users.getMakeorganid());
				uploadSAPLog.setMakeDeptId(users.getMakedeptid());
				uploadSAPLog.setMakeDate(Dateutil.getCurrentDate());
				uploadSAPLog.setIsDeal(SapUploadLogStatus.PROCESS_SUCCESS
						.getDatabaseValue());
				uploadSAPLog.setFileHaseCode(md5);
				appUploadSAPlog.addUploadSAPlog(uploadSAPLog);
				
				resultMsg = "文件上传成功， 请通过箱码托码转换日志查看处理结果!";
//				System.out.println("filePath=="+savePath+saveName);
//				System.out.println("filePath2=="+savePath+saveName2);
//				request.setAttribute("filePath", savePath+saveName);
//				request.setAttribute("filePath2", savePath+saveName2);
				request.setAttribute("result", resultMsg);
				//return mapping.findForward("success");
				return mapping.findForward("failure");
			}
			
		} catch (Exception e) {
			logger.error("文件上传失败", e);
			resultMsg = "文件上传失败!";
			request.setAttribute("result", resultMsg);
			return mapping.findForward("failure");
		}
	}
	
    /**
     * 主要功能:以文件流的方式复制文件
     * 
     * @param src:文件源流
     * @param dest:文件目的目录
     * @throws IOException
     */
    public static void CreateFileWithMessage(String Message, String savePath, String fileName) throws Exception {
//    	System.out.println("savePath="+savePath);
    	OutputStreamWriter ow = null;
    	BufferedWriter bw = null;
    	try {
			File filePath = new File(savePath);
			if (!filePath.exists())
				filePath.mkdirs();
			File file = new File(savePath + fileName);
			if(!file.exists()) 
				file.createNewFile();
			ow = new OutputStreamWriter(new FileOutputStream(file), Constants.DEFAULT_FILE_ENCODE);
			bw = new BufferedWriter(ow);
			bw.write(Message.toString());
		} catch (Exception e) {
			logger.debug("failed to create file :" +savePath+ fileName);
			throw e;
		} finally {
			if(bw != null) {bw.close();}
		}
    }
	
}
