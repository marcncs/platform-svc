package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppUserGroup;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UserGroup;

public class ToUpdUserGroupAction  extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		initdata(request);
		String groupid=request.getParameter("groupId");
		AppUserGroup aug=new AppUserGroup();
		UserGroup group=aug.getUserGroupById(Integer.valueOf(groupid));
		AppRole ar=new AppRole();
		Role role=ar.getRoleById(group.getRoleId());
		group.setRoleName(role.getRolename());
		request.setAttribute("currentGroup",group);
		return mapping.findForward("updGroup");	
	}
}
