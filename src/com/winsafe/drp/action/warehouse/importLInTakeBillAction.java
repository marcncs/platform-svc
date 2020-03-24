package com.winsafe.drp.action.warehouse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeUploadLog;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Scanner;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.pojo.CartonCode;

public class importLInTakeBillAction extends BaseAction {
	private AppTakeTicketIdcode app = new AppTakeTicketIdcode();
	private String lcode;
	AppTakeTicket att = new AppTakeTicket();
	protected AppIdcode appidcode = new AppIdcode();
	private TakeTicket tt = null;
	private AppIdcodeUploadLog appIdcodeUploadLog = new AppIdcodeUploadLog();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化
		initdata(request);
		// TTid编号
		String productid = request.getParameter("prid");
		String ttid = request.getParameter("billid");
		
		// 保存报错信息
		StringBuffer errMsg = new StringBuffer();
		int CCount = 0, SCount = 0;
		int linenum = 0,codenum=0;
		try {
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			boolean bool = false;
			if (idcodefile != null && !idcodefile.equals("")) {
				if (idcodefile.getContentType() != null) {
					if (idcodefile.getFileName().indexOf("txt") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				tt = att.getTakeTicketById(ttid);
				// 该发货单是否已作废判断
				if (tt.getIsblankout() == 1) {
					errMsg.append("该发货单编号已作废<br />");
					bool = false;
				}
				// 该发货单是否已复核判断
				if (tt.getIsaudit() == 1) {
					errMsg.append("该发货单编号已复核<br />");
					bool = false;
				}
			}
			if (bool) {
				
				//根据单据号，获取获取出货机构， 检查相关信息
				//机构为toller--modeltyp=2
				//
				DBUserLog.addUserLog(request, "文件：" + idcodefile.getFileName());
				
			
				
				//获取文件
				BufferedReader br = null;
				br = new BufferedReader(new InputStreamReader(idcodefile.getInputStream(), "utf-8"));
				String readLine;
				
				StringBuffer turnsb = new StringBuffer(); //错误信息
				//HashSet判断重复上传
				Set<String> setCode = new HashSet<String>();
				Set<String> setBoxCode = new HashSet<String>();
				Map<String,String> cbmap = new HashMap<String,String>();
				
				//按行处理， 每行只有1个code
				while ((readLine = br.readLine()) != null) {
					linenum = linenum+1;
					
					if(readLine.length()<=22||!readLine.contains(" ")){
						errMsg.append(readLine+" 原文件第"+linenum+"行：该行格式错误！") ;
						CCount = CCount +1;
						errMsg.append("<br />");
					}else{
						String[]ss = readLine.split(" ");
						if(ss.length!=3){
							errMsg.append(readLine+" 原文件第"+linenum+"行：该行格式错误！") ;
							errMsg.append("<br />");
							CCount = CCount +1;
							continue;
						}
						String flag = ss[1];
						String code = ss[2];
						
						if(StringUtil.isEmpty(ss[0])){
							errMsg.append(readLine+" 原文件第"+linenum+"行：错误,单号为空！") ;
							errMsg.append("<br />");
							CCount = CCount +1;
						}else  if(StringUtil.isEmpty(flag)){
							errMsg.append(readLine+" 原文件第"+linenum+"行：错误,托箱标志为空！") ;
							errMsg.append("<br />");
							CCount = CCount +1;
						}else  if(!flag.equals("C")&&!flag.equals("P")){
							errMsg.append(readLine+" 原文件第"+linenum+"行：错误,托箱标志错误！") ;
							errMsg.append("<br />");
							CCount = CCount +1;
						}
						else  if(StringUtil.isEmpty(code)||code.length()!=20){
							errMsg.append(readLine+" 原文件第"+linenum+"行：条码"+code+"格式错误！") ;
							errMsg.append("<br />");
							CCount = CCount +1;
						}
						else  if(!setCode.add(code)){
							errMsg.append(readLine+" 原文件第"+linenum+"行：错误,条码"+code+"重复上传！") ;
							errMsg.append("<br />");
							CCount = CCount +1;
						}
						else  if(!setBoxCode.add(code)){
							errMsg.append(readLine+" 原文件第"+linenum+"行：错误,条码"+code+"所在托"+cbmap.get(code)+"已上传！") ;
							errMsg.append("<br />");
							CCount = CCount +1;
						} else if (!ss[0].equalsIgnoreCase(tt.getNccode())){
							errMsg.append(readLine+" 原文件第"+linenum+"行：错误,单号"+ss[0]+"不是本单据的") ;
							errMsg.append("<br />");
							CCount = CCount +1;
						}
						else{
							AppCartonCode appCartonCode = new AppCartonCode();
							if(flag.equals("C")){
								if(!appCartonCode.isCartonCodeExists(code)){
									errMsg.append(readLine+" 原文件第"+linenum+"行：箱码"+code+" 查无信息！") ;
									errMsg.append("<br />");
									CCount = CCount +1;
								}else{ //成功
									
									// 通过条码新增service进行条码的检查及新增
									String errorString = dealIdcode(tt.getId(), code, productid, userid + "", users
											.getMakeorganid());
									if (isSuccess(errorString)) {
										// 每行数据手动提交事务,防止事务事件过长
										HibernateUtil.commitTransaction();
										SCount++;
										
									}else {
										errMsg.append("第[" + linenum + "]行"+errorString+"<br />");
										CCount = CCount +1;
									}
									
									//errMsg.append(ss[0]+"\t"+code);
									//errMsg.append("<br />");
								}
							}else{
								codenum++;
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
											temp = false;
										}
									}
									if(temp){  //成功
										for(CartonCode cartonCode:codes){
										
											// 通过条码新增service进行条码的检查及新增
											String errorString = dealIdcode(tt.getId(), cartonCode.getCartonCode(), productid, userid + "", users
													.getMakeorganid());
											if (isSuccess(errorString)) {
												// 每行数据手动提交事务,防止事务事件过长
												HibernateUtil.commitTransaction();
												SCount++;
											}else {
												errMsg.append("第[" + linenum + "]行托转箱"+cartonCode.getCartonCode()+"时错误，"+errorString+"<br />");
												CCount = CCount +1;
											}
											setBoxCode.add(cartonCode.getCartonCode());
											cbmap.put(cartonCode.getCartonCode(), code);
										}
									
									}else{
										String codeadd = "";
										for(String recode:setcartonCodeIn){
											codeadd = codeadd + recode + " ";
										}
										errMsg.append(readLine+" 原文件第"+linenum+"行：托码"+code+"内箱码 "+codeadd+"已上传！") ;
										errMsg.append("<br />");
										CCount = CCount +1;
									}
								}else{
									errMsg.append(readLine+" 原文件第"+linenum+"行：托码"+code+" 查无信息！") ;
									errMsg.append("<br />");
									CCount = CCount +1;
								}
							}
						}
					}
				}
				
				
				//完成处理
			} else {
				HibernateUtil.rollbackTransaction();
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String resultMsg = "上传码资料成功,本次总: " + (linenum) + "行! 成功:" + SCount + "条! 失败："
					+ CCount + "条!  其中托盘："+ codenum+" 个";
			if ( CCount > 0) {
				resultMsg = resultMsg + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>" + "<div >"
						+ errMsg.toString() + "</div>";
			}
			request.setAttribute("result", resultMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			System.out.println("异常出现在第 " + CCount + "行");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
	}

	public String dealIdcode(String billid, String idcode, String productid, String userid,
			String organId) throws Exception {

		Nidcode aNidcode = new Nidcode();
		// 验证条码的正确性
		String errorMsg = validateIdcode(idcode, tt, productid, aNidcode);
		if (isSuccess(errorMsg)) {
			// 更新条码状态为不可用
			appidcode.updIsUse(idcode, 0);
			// 增加条码
			TakeTicketIdcode pi = new TakeTicketIdcode();
//			pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("take_ticket_idcode", 0, "")));
			pi.setTtid(billid);
			pi.setProductid(aNidcode.productid);
			pi.setIsidcode(1);
			// pi.setWarehousebit(aNidcode.warehousebit);
			pi.setBatch(aNidcode.batch);
			pi.setProducedate(aNidcode.productDate);
			pi.setVad("");
			pi.setUnitid(aNidcode.unitid);
			pi.setQuantity(1d);
			pi.setPackquantity(aNidcode.packQuantity);
			pi.setIdcode(idcode);
			pi.setLcode(lcode);
			pi.setStartno(aNidcode.startno);
			pi.setEndno(aNidcode.endno);
			pi.setIssplit(aNidcode.issplit);
			pi.setMakedate(DateUtil.getCurrentDate());
			pi.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			app.addTakeTicketIdcode(pi);
		}
		return errorMsg;
	}

	private String validateIdcode(String ic, TakeTicket tt, String productid, Nidcode aNidcode)
			throws Exception {
		
		
		// 记录特殊的错误信息(只记录,不报错)
		String errorMsg = null;
		
		
		// 判断条码数量是否超过产品数量
		//if (att.isIdcodeQtyGreatOrEqualThanProductQty(tt.getId(), productid)) {
		//	return UploadErrorMsg.E00016;
		//}
		// 判断条码是否存在
		Idcode idcode = appidcode.getIdcodeByCode(ic);
		if (idcode == null) {
			return UploadErrorMsg.getError(UploadErrorMsg.E00003, ic);
		}
		// 判断条码的产品是否符合条件
		//if (!idcode.getProductid().equals(productid)) {
		//	return UploadErrorMsg.getError(UploadErrorMsg.E00108, ic);
		//}
		productid=idcode.getProductid(); //先直接复制
		// 判断条码是否可用
		//if (idcode.getIsuse() != null && idcode.getIsuse() == 0
		//		&& idcode.getWarehouseid().equals(tt.getWarehouseid())) {
		//	return UploadErrorMsg.getError(UploadErrorMsg.E00004, ic);
		//}
		//判断是否已在单据中。
		TakeTicketIdcode tti = app.getTakeTicketIdcodeByidcode(productid, tt.getId(), ic);
		if (tti != null) {
			return UploadErrorMsg.getError(UploadErrorMsg.E00006, ic);
		}
		/*
		// 判断条码是否在当前仓库(只记录错误信息,不报错)
		if (!idcode.getWarehouseid().equals(tt.getWarehouseid())) {
			errorMsg = UploadErrorMsg.getError(UploadErrorMsg.E00005, ic);
			//手工条码上传与采集器条码上传的单据类型不一致，添加错误日志时使用统一的类型
			Integer bSort = 0;
			if(tt.getBsort() == 1) {
				bSort = 4;
			} else if(tt.getBsort() == 2) {
				bSort = 5;
			} else {
				if(tt.getBillno().startsWith("OW")) {
					bSort = 8;
				} else {
					bSort = 20;
				}
			}
			// 保存条码的特殊信息日志
			appIdcodeUploadLog.addIdcodeUploadLogs(errorMsg, tt.getBillno(), userid.toString(), bSort,users.getMakeorganid(), tt.getWarehouseid(), idcode.getIdcode());
		}
		*/
		aNidcode.batch = idcode.getBatch();
		aNidcode.productid = idcode.getProductid();
		aNidcode.productDate = idcode.getProducedate();
		aNidcode.warehousebit = idcode.getWarehousebit();
		aNidcode.unitid = idcode.getUnitid();
		aNidcode.packQuantity = idcode.getPackquantity();
		aNidcode.startno = idcode.getStartno();
		aNidcode.endno = idcode.getEndno();
		aNidcode.warehousebit = idcode.getWarehousebit();

		return "条码更新成功";

	}

	public class Nidcode {
		public String batch;
		public String productid;
		public String productDate;
		public String warehousebit = "000";
		public int issplit = 0;
		public Integer unitid;
		public String scanFlag;
		public Double packQuantity = 1d;
		public String startno;
		public String endno;
		public String warehouseid;
		public Product product;
	}
	
	public  boolean isSuccess(String errorMsg) {
		if ("条码更新成功".equals(errorMsg)) {
			return true;
		}
		return false;
	}
}