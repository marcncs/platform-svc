package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMoveCanuseOrgan;

public class DelMoveCanuseOrganAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		String uid = (String) request.getSession().getAttribute("uid");

	try {
		String[] opid = request.getParameterValues("opid");
		AppMoveCanuseOrgan aop = new AppMoveCanuseOrgan();
		for (int i = 0; i < opid.length; i++) {
			aop.delMoveCanuseOrgan(Integer.valueOf(opid[i]));
		}
		
		request.setAttribute("result", "databases.del.success");
		request.setAttribute("forward", "../users/listMoveCanuseOrganAction.do?UID="+uid);
		return mapping.findForward("del");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
