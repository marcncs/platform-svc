/**
 * 
 */
package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;


public class UpdUserRoleAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] arid = request.getParameterValues("role");
		Integer userid = Integer.valueOf(request.getParameter("userid"));
		
		UsersBean users = UserManager.getUser(request);

		try {

			AppRole ar = new AppRole();
			
			Integer userroleid;
			ar.resetARByUserid(userid);
			
			if ( arid != null ){
				for (int i = 0; i < arid.length; i++) {
					userroleid = Integer.valueOf(arid[i]);
					ar.updateAR(userroleid);
				}
			}

			DBUserLog.addUserLog(users.getUserid(), 11, "用户管理>>修改用户角色,用户编号:"+userid);

			request.setAttribute("result", "databases.upd.success");
			request.setAttribute("forward", "../users/listUsersAction.do");
			return mapping.findForward("roleResult");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return mapping.findForward("roleResult");
	}

}
