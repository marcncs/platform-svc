package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;


public class ToAddLargessAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			Integer objsort  = Integer.valueOf(request.getParameter("objsort"));
			request.setAttribute("objsort", objsort);
			
			return mapping.findForward("toadd");

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mapping.getInputForward();
	}

}
