package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.Role;

public class ToCopyRoleAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		String roleid=request.getParameter("roleid");
		AppRole ar=new AppRole();
		Role role=ar.getRoleById(Integer.valueOf(roleid));
		request.setAttribute("currentRole",role);
		return mapping.findForward("updRole");	
	}
}
