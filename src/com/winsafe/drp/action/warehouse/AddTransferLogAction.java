package com.winsafe.drp.action.warehouse;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm; 
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.erp.metadata.FileType;
import com.winsafe.erp.services.FileTransferService;

public class AddTransferLogAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddTransferLogAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			Integer type = Integer.valueOf(request.getParameter("fileType"));
			FileType fileType = FileType.parseByValue(type);
			FileTransferService fts = FileTransferService.getTransferService(fileType);
			fts.createTransferFile();
			
			request.setAttribute("result", "添加成功");
			return mapping.findForward("result");
		} catch (Exception e) {
			logger.error("添加传输文件发生异常",e);
			throw e;
		}
	}
}
