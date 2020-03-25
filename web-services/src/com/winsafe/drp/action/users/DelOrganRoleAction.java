package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganRole;

public class DelOrganRoleAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		String roleid = (String) request.getSession().getAttribute("roleid");

	try {
		String[] opid = request.getParameterValues("opid");
		AppOrganRole aor = new AppOrganRole();
//		AppMoveCanuseOrgan aop = new AppMoveCanuseOrgan();
		for (int i = 0; i < opid.length; i++) {
			aor.delOrganRole(Integer.valueOf(opid[i]));
		}
		
		request.setAttribute("result", "databases.del.success");
		request.setAttribute("forward", "../users/listOrganRoleAction.do?RoleID="+roleid);
		return mapping.findForward("del");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
