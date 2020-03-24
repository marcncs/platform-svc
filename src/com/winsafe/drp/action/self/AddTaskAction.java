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
import com.winsafe.drp.dao.TaskExecute;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddTaskAction extends Action {

	@SuppressWarnings("static-access")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			Task task = new Task();
			BeanCopy bc = new BeanCopy();
			
			bc.copy(task, request);

			Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"task", 0, ""));
			task.setId(id);
			task.setOverstatus(1);
			task.setMakeid(userid);
			task.setMakedeptid(users.getMakedeptid());
			task.setMakeorganid(users.getMakeorganid());
			task.setMakedate(DateUtil.getCurrentDate());
			
			task.setIsallot(1);
			AppTask appTask = new AppTask();
			
			appTask.addTask(task);
			request.getSession().setAttribute("ID", task.getId());
			request.getSession().setAttribute("ReferType", "Task");
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>新增任务  ,编号:" + task.getId());
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("addresult");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

	public void saveTaskExecute(Integer tid, Integer userid) throws Exception {
		AppTaskExecute appTaskExecute = new AppTaskExecute();
		TaskExecute taskExecute = new TaskExecute();
		taskExecute.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"task_execute", 0, "")));
		taskExecute.setTpid(tid);
		taskExecute.setIsaffirm(1);
		taskExecute.setUserid(userid);
		taskExecute.setIsover(0);
		taskExecute.setOverdate(null);
		appTaskExecute.addExecute(taskExecute);
	}
}
