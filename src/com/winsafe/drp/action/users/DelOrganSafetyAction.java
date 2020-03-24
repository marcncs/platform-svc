package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganSafetyIntercalate;
import com.winsafe.drp.dao.OrganSafetyIntercalate;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelOrganSafetyAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int id = Integer.valueOf(request.getParameter("ID"));

		try {
			AppOrganSafetyIntercalate aws = new AppOrganSafetyIntercalate();
			OrganSafetyIntercalate osi = aws.getOrganSafetyIntercalateByID(id);
			aws.delOrganSafetyIntercalate(id);
			
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      DBUserLog.addUserLog(userid,11,"机构设置>>删除仓库报警,编号："+id, osi);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

}
