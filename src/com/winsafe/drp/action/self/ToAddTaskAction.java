package com.winsafe.drp.action.self;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppTaskExecute;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToAddTaskAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			
			UsersBean users = UserManager.getUser(request);
			AppTaskExecute appTaskExecute = new AppTaskExecute();
			List list = appTaskExecute.getIsUsers(users.getMakeorganid(), Integer.valueOf(0));
			request.setAttribute("Users", list);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());

	}
}
