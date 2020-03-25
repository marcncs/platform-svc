package com.winsafe.drp.action.machin;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.erp.dao.AppApplyScratchCode;

public class ToUpdApplyScratchCodeAction extends BaseAction {

	private AppApplyScratchCode appQrCode = new AppApplyScratchCode();
	private static Logger logger = Logger.getLogger(ToUpdApplyScratchCodeAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		try {
			String id = request.getParameter("ID"); 

			List<Map<String,String>> aqcs = appQrCode.getApplyScratchCodeById(Integer.valueOf(id));
			
			request.setAttribute("aqc", aqcs.get(0));
		} catch (Exception e) {
			logger.error("", e);
		}
		return mapping.findForward("success");
	}

}
