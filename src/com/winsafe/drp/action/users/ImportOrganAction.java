package com.winsafe.drp.action.users;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.ExcelUtil;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppOrganUploadLog;
import com.winsafe.sap.pojo.OrganUploadLog;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.MD5BigFileUtil;

/**
 * Project:is->Class:ImportOrganAction.java
 * <p style="font-size:16px;">Description：导入机构信息</p>
 * @author <a href='fuming.zhang@winsafe.com'>zhangfuming</a>
 * @version 0.8
 */
public class ImportOrganAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ImportOrganAction.class);
	
	AppOrganUploadLog app = new AppOrganUploadLog();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		
		//保存报错信息
		StringBuffer errMsg = new StringBuffer();
		Workbook wb = null;
		try {
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			boolean bool = false;
			if (idcodefile != null && !idcodefile.equals("")) {

				if (idcodefile.getContentType() != null) {
					if (idcodefile.getFileName().indexOf("xls") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				boolean hasError = false; 
				
				
				wb = WorkbookFactory.create(idcodefile.getInputStream());
				Sheet sheet = wb.getSheetAt(0);
				Row row = null;
				System.out.println(sheet.getLastRowNum());
				
//				wb = Workbook.getWorkbook(idcodefile.getInputStream());
//				Sheet sheet = wb.getSheet("Sheet1");
//				int row = sheet.getRows();
				
				if(sheet.getLastRowNum() <= 0) {
					request.setAttribute("result", "上传文件处理失败，未从文件中读取到数据");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				
				row = sheet.getRow(0);
				System.out.println(row.getLastCellNum());
				
				String header = ExcelUtil.getValue(row.getCell(0));
				if("1".equals(mf.getType())) {
					if(StringUtil.isEmpty(header) || !header.trim().equals("机构类型") || row.getLastCellNum() < 19) {
						request.setAttribute("result", "上传文件处理失败,请确认是否选择了正确的类型和模板");
						return new ActionForward("/sys/lockrecord2.jsp");
					}
				} else {
					if(StringUtil.isEmpty(header) || !header.trim().equals("机构编号*") || row.getLastCellNum() < 16) {
						request.setAttribute("result", "上传文件处理失败,请确认是否选择了正确的类型和模板");
						return new ActionForward("/sys/lockrecord2.jsp");
					}
				}
				
				
				// 保存报错信息
				
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					if("1".equals(mf.getType())) {
						String organName = ExcelUtil.getValue(row.getCell(3));
						if(StringUtil.isEmpty(organName)){
							errMsg.append("第[" + i + "]行机构名称不能为空<br />");
							hasError = true;
						} 
					} else {
						String organId = ExcelUtil.getValue(row.getCell(0));
						if(StringUtil.isEmpty(organId)){
							errMsg.append("第[" + i + "]行机构编号不能为空<br />");
							hasError = true;
						} 
					}
					
				}
				
				if(!hasError) {
					//保存文件
					String uploadPath = (String)JProperties.loadProperties("upr.properties",JProperties.BY_CLASSLOADER).get("organFilePath"); 
//					String uploadPath = "/upload/organ/";
					String saveFileName = DateUtil.getCurrentDateTimeString() + "_" + idcodefile.getFileName();
					FileUploadUtil.saveUplodedFile(idcodefile.getInputStream(), uploadPath, saveFileName);
					
					//检查是否已上传过
					String md5 = MD5BigFileUtil.md5(idcodefile.getInputStream());

					OrganUploadLog iu = new OrganUploadLog();
					iu.setFileHaseCode(md5);
					iu.setFileName(idcodefile.getFileName());
					iu.setFilePath(uploadPath+saveFileName);
					iu.setIsDeal(0);
					iu.setMakeDate(DateUtil.getCurrentDate());
					iu.setMakeId(users.getUserid());
					iu.setMakeOrganId(users.getMakeorganid());
					iu.setType(Integer.valueOf(mf.getType()));
					
					app.addOrganUploadLog(iu);
					
				} else {
					request.setAttribute("result", "文件上传失败,原因如下:<br /> "+errMsg.toString());
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				DBUserLog.addUserLog(request, "");
			} 
			
		} catch (Exception e) {
			logger.error("error occurred when processing xls file", e);
			request.setAttribute("result", "处理文件时发生异常");
			throw e;
		} finally {
			
		}
		request.setAttribute("result", "文件上传成功,请通过日志查看处理结果");
		return new ActionForward("/sys/lockrecord2.jsp");
	}
	
	
}




