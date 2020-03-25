package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganAwake;
import com.winsafe.drp.dao.OrganAwake;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelOrganAwakeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int id = Integer.valueOf(request.getParameter("ID"));

		try {
			AppOrganAwake aws = new AppOrganAwake();
			OrganAwake oa = aws.getOrganAwakeByID(id);
			aws.delOrganAwakeByID(id);
			
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      DBUserLog.addUserLog(userid, 11,"机构设置>>删除报警提醒人,编号："+id, oa);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

}
