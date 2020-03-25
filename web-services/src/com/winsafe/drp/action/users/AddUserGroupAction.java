package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUserGroup;
import com.winsafe.drp.dao.UserGroup;
import com.winsafe.drp.util.DBUserLog; 
import com.winsafe.drp.util.Dateutil;

public class AddUserGroupAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			initdata(request);
			AppUserGroup ar = new AppUserGroup();
			UserGroup r = new UserGroup();
			r.setGroupName(request.getParameter("groupName"));
			r.setDescription(request.getParameter("description"));
			r.setRoleId(Integer.valueOf(request.getParameter("roleId")));
			r.setMakeDate(Dateutil.getCurrentDate());
			ar.addUserGroup(r); 

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, "系统管理", "用户组>>新增,编号:" + r.getId());
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
