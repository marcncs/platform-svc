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

public class UpdRoleUserAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] arid = request.getParameterValues("role");
		Integer roleid = Integer.valueOf(request.getParameter("roleid"));

		try {

			AppRole ar = new AppRole();
			
			Integer userroleid;
			ar.resetARByRoleid(roleid);
			
			if ( arid != null ){
				for (int i = 0; i < arid.length; i++) {
					userroleid = Integer.valueOf(arid[i]);
					ar.updateAR(userroleid);
				}
			}
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(request, "角色编号:"+roleid);
			request.setAttribute("result", "databases.upd.success");
			
			return mapping.findForward("roleResult");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return mapping.findForward("roleResult");
	}

}
