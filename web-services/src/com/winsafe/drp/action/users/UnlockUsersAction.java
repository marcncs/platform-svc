package com.winsafe.drp.action.users;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;

public class UnlockUsersAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			
			UsersService au = new UsersService();
			
			au.unlockUsers(id);
			
			DBUserLog.addUserLog(id, "系统管理", "用户管理>>解锁用户,编号：" + id);

			return mapping.findForward("success");
		} catch (Exception e) {
			throw e;
		}
	}
}

