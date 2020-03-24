package com.winsafe.drp.action.sales;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPact;
import com.winsafe.drp.dao.Pact;


public class ToUpdPactAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception, ServletException {
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			AppPact ap = new AppPact();
			Pact pt = ap.getPactByID(id);
			request.setAttribute("pt", pt);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("toupd");
	}
}
