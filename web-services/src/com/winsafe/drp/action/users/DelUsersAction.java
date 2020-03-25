package com.winsafe.drp.action.users;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;

public class DelUsersAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			
			if ( id.intValue() == 1 ){
				request.setAttribute("result", "admin.del.fail");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			UsersService au = new UsersService();
			Users u = au.getUsersByid(id);
			au.delUsersById(id);
			AppUserVisit appuv = new AppUserVisit();
			appuv.delUserVisitByUserID(id);
			//无区域信息
			//au.deleteUserAreas(id);
			AppRole appr = new AppRole();
			appr.delWareHouseByUserid(id);
			appr.delUserRoleByUserid(id);
			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, "系统管理", "用户管理>>删除用户,编号：" + id, u);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "databases.del.fail");
		}
		return mapping.findForward("success");
	}
}

