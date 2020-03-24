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
import com.winsafe.drp.util.DBUserLog;

public class DelTaskAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			AppTask at = new AppTask();
			AppTaskExecute ate = new AppTaskExecute();
			Task task = at.getTaskByID(id);
			if(!task.getMakeid().equals(userid) && userid.intValue() !=1){
				request.setAttribute("result", "databases.del.nosuccess");
				return mapping.findForward("del");
			}			
			
			ate.delTaskPlanExecuteByTPID(id);
			at.delTask(id);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>删除任务 ,编号：" + id, task);
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
