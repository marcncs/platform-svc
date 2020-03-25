package com.winsafe.sap.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.winsafe.sap.util.SapConfig;

public class ToGenerateCommonCode extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String commonCode = SapConfig.getSapConfig().getProperty("commonCode");
		request.setAttribute("commonCode", commonCode);
		request.setAttribute("mCode", request.getParameter("mCode"));
		
		
		return mapping.findForward("gencomcode");
	}
}
