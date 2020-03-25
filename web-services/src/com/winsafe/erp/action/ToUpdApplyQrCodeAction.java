package com.winsafe.erp.action;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.erp.dao.AppApplyQrCode;

public class ToUpdApplyQrCodeAction extends BaseAction {

	private AppApplyQrCode appQrCode = new AppApplyQrCode();
	private static Logger logger = Logger.getLogger(ToUpdApplyQrCodeAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		try {
			String id = request.getParameter("ID");

			List<Map<String,String>> aqcs = appQrCode.getApplyQrCodeById(Integer.valueOf(id));
			
			request.setAttribute("aqc", aqcs.get(0));
		} catch (Exception e) {
			logger.error("", e);
		}
		return mapping.findForward("success");
	}

}
