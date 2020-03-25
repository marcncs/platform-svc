package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUserGroup;
import com.winsafe.drp.dao.UserGroupApp;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;

public class UpdUserGroupAppAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] arid = request.getParameterValues("appId");
		String groupId = request.getParameter("groupId");

		try {

			AppUserGroup ar = new AppUserGroup();
			
			Integer intGroupId = Integer.valueOf(groupId);
			ar.delUserGroupAppByGroupId(intGroupId);
			
			if ( arid != null ){
				for (int i = 0; i < arid.length; i++) {
					UserGroupApp uga = new UserGroupApp();
					uga.setAppId(Integer.valueOf(arid[i]));
					uga.setMakeDate(Dateutil.getCurrentDate());
					uga.setUserGroupId(intGroupId);
					ar.addUserGroup(uga);
				}
			}
			DBUserLog.addUserLog(request, "用户组编号:"+groupId);
			request.setAttribute("result", "databases.upd.success");
			
			return mapping.findForward("ugaResult");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return mapping.findForward("roleResult");
	}

}
