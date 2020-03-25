package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganRole;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelOrganRoleForOrganAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
		String organid = (String) request.getSession().getAttribute("organid");

	try {
		if ( organid.equals(users.getMakeorganid()) ){
			request.setAttribute("result", "对不起，不能删除自己机构的角色！");
			return new ActionForward("/sys/lockrecordclose2.jsp");
		}
		String[] opid = request.getParameterValues("opid");
		AppOrganRole aor = new AppOrganRole();
//		AppMoveCanuseOrgan aop = new AppMoveCanuseOrgan();
		for (int i = 0; i < opid.length; i++) {
			aor.delOrganRole(Integer.valueOf(opid[i]));
		}
		
		request.setAttribute("result", "databases.del.success");
		request.setAttribute("forward", "../users/listOrganRoleForOrganAction.do?OrganID="+organid);
		return mapping.findForward("del");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
