package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUserGroup;
import com.winsafe.drp.dao.UserGroup;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;

/**
 * @author alex
 * 
 */
public class UpdUserGroupAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String groupid = request.getParameter("groupId");

		try {

			AppUserGroup aug = new AppUserGroup();
			UserGroup group = aug.getUserGroupById(Integer.valueOf(groupid));
			group.setGroupName(request.getParameter("groupName"));
			group.setDescription(request.getParameter("description"));
			group.setRoleId(Integer.valueOf(request.getParameter("roleId")));
			group.setMakeDate(Dateutil.getCurrentDate());

			aug.updUserGroup(group);

			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(request, "编号：" + group.getId());

			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		} 
	}

}
