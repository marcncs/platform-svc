package com.winsafe.sap.interfaces;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.action.form.SapDataForm;
import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.service.SapFileUploadService;
import com.winsafe.sap.util.FileUploadUtil;

public class SapDataAction extends Action {
	private static Logger logger = Logger.getLogger(SapDataAction.class);
	
	AppUsers appUsers = new AppUsers();
	SapFileUploadService sapFileUploadService = new SapFileUploadService();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter writer = response.getWriter();
		String resultMsg;
		try {
			SapDataForm sapFileForm = (SapDataForm) form;
			//身份验证
			UsersBean usersBean = appUsers.getUsersBeanByLoginname(sapFileForm.getUsername());
			if(usersBean == null) {
				logger.debug("CheckUsersNamePassword failed");
				resultMsg = "{returncode:-1}";
				outputToSap(resultMsg, writer);
				return null;
			}
			//文件类型验证
			SapFileType fileType = SapFileType.parseBySapName(sapFileForm.getType());
			if(fileType == null) {
				logger.debug("check file type failed");
				resultMsg = "{returncode:-1}";
				outputToSap(resultMsg, writer);
				return null;
			}
			//文件内容与格式验证
			FormFile sapDataFile = (FormFile) sapFileForm.getFilestream();
			boolean bool = false;
			if (sapDataFile != null && !sapDataFile.equals("")) {

				if (sapDataFile.getContentType() != null) {
					if (sapDataFile.getFileName().toLowerCase().indexOf("xml") >= 0) {
						bool = true;
					}
				}
			}
			if(bool) {
				String savePath = FileUploadUtil.getSapSaveFilePath() + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/" + fileType.getSapName() + "/";
		    	String saveName = DateUtil.getCurrentDateTimeString() + "_" + sapDataFile.getFileName();
				sapFileUploadService.processSapFile(sapDataFile.getInputStream(), sapDataFile.getFileName(), fileType, savePath, saveName,  usersBean, "");
				resultMsg = "{returncode:1}";
			} else {
				logger.debug("file not found or format is not valid.");
				resultMsg = "{returncode:-1}";
			}
			outputToSap(resultMsg, writer);
			return null;
		} catch (Exception e) {
			logger.error("SAP上传文件失败", e);
			resultMsg = "{returncode:-1}";
			outputToSap(resultMsg, writer);
			return null;
		}
	}
	/**
	 * 返回处理结果到SAP 
	 * Create Time: Oct 17, 2014 5:54:28 PM
	 * @param msg
	 * @param writer
	 * @return
	 * @author ryanxi
	 */
	private void outputToSap(String msg, PrintWriter writer)
	{
		writer.write(msg);
		writer.flush();
	}
	
}
