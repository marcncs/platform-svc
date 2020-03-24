package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPlinkman;
import com.winsafe.drp.dao.Plinkman;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelPlinkmanAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			Integer lid = Integer.valueOf(request.getParameter("LID"));
			AppPlinkman al = new AppPlinkman();
			Plinkman pl = al.getProvideLinkmanByID(lid);
			al.delPlinkman(lid);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 2, "供应商资料>>删除供应商联系人,编号:" + lid, pl);

			return mapping.findForward("del");
		} catch (Exception e) {
			request.setAttribute("result", "databases.del.fail");
			e.printStackTrace();
		}
		return null;
	}

}
