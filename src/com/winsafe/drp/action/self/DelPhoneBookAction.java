package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPhoneBook;
import com.winsafe.drp.dao.PhoneBook;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelPhoneBookAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			AppPhoneBook at = new AppPhoneBook();
			PhoneBook ph = at.getPhoneBookByID(id);
			
			at.delPhoneBook(id);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>删除电话本,编号：" + id, ph);
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
