package com.winsafe.quartz.task.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.quartz.factory.InitTask;
import com.winsafe.quartz.factory.TaskOperate;
import com.winsafe.quartz.task.dao.TaskService;
import com.winsafe.quartz.task.pojo.TaskQuartz;

public class OperateTaskAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	    UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid(); 
		String tid = request.getParameter("ID");
		String operateId = request.getParameter("operateId");
		ServletContext application = request.getSession().getServletContext();
		// flag   true:设置成功 false:设置失败
		boolean flag = operateTask(Integer.valueOf(tid), Integer.valueOf(operateId),userid,application);
		if(!flag){
			request.setAttribute("result", "修改失败,服务未注册,请注册服务!");
			return new ActionForward("/sys/operatorclose3.jsp");
		}
		request.setAttribute("result", "databases.upd.success");
		return new ActionForward("/sys/operatorclose.jsp");
	  }
	
	/**
	 * 操作任务
	 */
	public boolean operateTask(Integer id,Integer operateId,Integer userid,ServletContext application)throws Exception{
		 
		try {
			TaskService appTask = new TaskService();
			TaskQuartz task = appTask.queryTaskByID(id+"");
			
//			InitTask initTask = (InitTask)application.getAttribute("initTask");
			//只有注册服务才有权设置
			if(InitTask.doesAlreadyRegister(task.getPerformClass())){
				task.setStatus(operateId);
				
				//启动
				if(operateId.intValue() == 0){
//	        	TaskOperate.start(id); 
					TaskOperate.create(task, application);
					DBUserLog.addUserLog(userid, "系统管理", "任务>>启动任务,编号:" + id);
				}
				//挂起
				else if(operateId.intValue() == 1){
//	        	TaskOperate.stop(id);
					TaskOperate.remove(id);
					DBUserLog.addUserLog(userid, "系统管理", "任务>>挂起任务,编号:" + id);
				} 
				
				appTask.updTask(task);
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
