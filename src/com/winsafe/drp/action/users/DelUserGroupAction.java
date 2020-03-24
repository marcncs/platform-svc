package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUserGroup; 
import com.winsafe.drp.util.DBUserLog;

public class DelUserGroupAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer roleid = Integer.valueOf(request.getParameter("groupId"));
		try {
			initdata(request);
			AppUserGroup ar = new AppUserGroup();
			
			ar.delUserGroup(roleid); 

			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(userid, "系统管理", "用户组>>删除角色,编号：" + roleid);

			return mapping.findForward("delgroup");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
