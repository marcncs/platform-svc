package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppTask;
import com.winsafe.drp.dao.AppTaskExecute;
import com.winsafe.drp.dao.Task;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class TaskDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			String strid = request.getParameter("ID");
			Integer id = Integer.valueOf(strid);
			AppTask appTask = new AppTask();
			Task task = appTask.getTaskByID(id);			
			AppTaskExecute appTaskExecute = new AppTaskExecute();
			appTaskExecute.updIsAffirmTaskExecute(id, userid);
			request.setAttribute("content", task.getTpcontent().replace("\n", "<br/>"));
			request.setAttribute("isover", appTaskExecute.getTaskExecute(id, userid).getIsover());
			request.setAttribute("tp", task);
			return mapping.findForward("listdetail");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return new ActionForward(mapping.getInput());
	}
}
