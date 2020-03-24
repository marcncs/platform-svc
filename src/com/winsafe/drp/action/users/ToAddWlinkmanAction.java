package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ToAddWlinkmanAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			request.setAttribute("warehouseid", request.getParameter("warehouseid"));
			return mapping.findForward("toadd");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.getInputForward();
	}

}
