package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelProviderAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();

			String pid = request.getParameter("PID");
			 
			AppProvider apv = new AppProvider();
			Provider p = apv.getProviderByID(pid);
			apv.updProviderToDel(pid);

			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(userid, 2, "供应商资料>>删除供应商,编号:" + pid, p);

			return mapping.findForward("del");
		} catch (Exception e) {
			request.setAttribute("result", "databases.del.fail");
			e.printStackTrace();
		} finally {

		}
		return null;
	}

}
