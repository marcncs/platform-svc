package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelRoleAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer roleid = Integer.valueOf(request.getParameter("roleid"));
		try {
			
			if ( roleid.intValue() == 1 ){
				request.setAttribute("result", "admin.del.fail");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AppRole ar = new AppRole();
			if (ar.isDispatchUser(roleid)) {
				request.setAttribute("result", "databases.role.dispatchuser");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			Role role = ar.getRoleById(roleid);
			
			
			ar.DelRoleMenu(roleid);
			
			ar.DelRoleLeftMenu(roleid);
			
			ar.DelUserRole(roleid);
			
			ar.DelRole(roleid);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, "系统管理", "角色管理>>删除角色,编号：" + roleid, role);

			return mapping.findForward("delrole");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("delrole");
	}
}
