package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToUpdOLinkManAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("id"));
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		AppOlinkMan appLinkman = new AppOlinkMan();

		try {
			Olinkman ol = appLinkman.getOlinkmanByID(id);

			request.setAttribute("ol", ol);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
