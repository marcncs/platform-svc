package com.winsafe.drp.action.self;

import java.util.List;

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

public class ToUpdTaskAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			UsersBean users = UserManager.getUser(request);
			String strid = request.getParameter("ID");
			Integer id = Integer.valueOf(strid);
			Integer userid = users.getUserid();
			AppTask atp = new AppTask();
			Task task = atp.getTaskFromByID(id);
			if(!task.getMakeid().equals(userid)){
				request.setAttribute("result", "databases.del.nosuccess");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			AppTaskExecute appTaskExecute = new AppTaskExecute();
			List list = appTaskExecute.getIsUsers(users.getMakeorganid(),id);

			request.setAttribute("Users", list);
			request.setAttribute("tpf", task);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
