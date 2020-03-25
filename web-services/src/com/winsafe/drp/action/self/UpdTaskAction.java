package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppTask;
import com.winsafe.drp.dao.Task;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;


public class UpdTaskAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
				
			Task task = new Task();
			BeanCopy bc = new BeanCopy();
			
			bc.copy(task, request);

			AppTask appTask = new AppTask();
			
			Task taskupd = appTask.getTaskByID(task.getId());
			Task oldtask = (Task) BeanUtils.cloneBean(taskupd);
			taskupd.setTptitle(task.getTptitle());
			taskupd.setTpcontent(task.getTpcontent());
			taskupd.setCid(task.getCid());
			taskupd.setCname(task.getCname());
			taskupd.setConclusiondate(task.getConclusiondate());
			taskupd.setEndtime(task.getEndtime());
			taskupd.setObjsort(task.getObjsort());
			taskupd.setPriority(task.getPriority());
			taskupd.setTasksort(task.getTasksort());
			taskupd.setOverstatus(task.getOverstatus());
			taskupd.setIsallot(1);

		
			
			appTask.updTask(taskupd);
			request.getSession().setAttribute("ID", task.getId());
			request.getSession().setAttribute("ReferType", "Task");	
			DBUserLog.addUserLog(userid,"系统管理", "任务>>修改任务,编号："+task.getId(),oldtask,taskupd);
			
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
}
