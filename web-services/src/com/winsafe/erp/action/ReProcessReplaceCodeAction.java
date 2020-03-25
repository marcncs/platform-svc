package com.winsafe.erp.action;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.sap.dao.AppUploadProduceLog;

public class ReProcessReplaceCodeAction extends BaseAction {

	private static Logger logger = Logger
			.getLogger(ReProcessReplaceCodeAction.class);

	private AppUploadProduceLog appUploadProduceLog = new AppUploadProduceLog();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		Integer uploadId = Integer.valueOf(request.getParameter("uploadId"));
		try {
			appUploadProduceLog.reProcessFile(uploadId);

			DBUserLog.addUserLog(request, "编号：" + uploadId);
			request.setAttribute("result", "更新成功");
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
		
		return mapping.findForward("success");
	}
}
