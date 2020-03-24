package com.winsafe.quartz.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.quartz.factory.InitTask;
import com.winsafe.quartz.factory.TaskOperate;
import com.winsafe.quartz.task.dao.TaskService;
import com.winsafe.quartz.task.pojo.TaskQuartz;

public class RetryIntialTask implements Job{
	
	private static Logger logger = Logger.getLogger(RetryIntialTask.class);
	
	private static boolean isAllIntialized = false;
	/** 任务服务对象 */
	private TaskService taskService = new TaskService();
 
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.debug("任务初始化重试任务开始");
		if(isAllIntialized) {
			stopTask();	
		}
		List<TaskQuartz> taskList = null;
		try {
			taskList = taskService.queryNotStartAndBindTask();
		} catch (Exception e) {
			logger.error("获取定时任务失败",e);
			return;
		}
		if(taskList == null || taskList.size() == 0) {
			return;
		}
		//循环任务添加任务
		for(TaskQuartz task : taskList){
			try{
				//过滤出已注册的任务task.getPerformClass()
				if(InitTask.doesAlreadyRegister(task.getPerformClass())){
					InitTask.registTask(task);
				}
			}catch(Exception e){
				logger.error("任务初始化失败![" + task.getId() + "(" + task.getPerformClass() + ")]", e);
			}
		}
		isAllIntialized = true;
		stopTask();				
		
	}

	private void stopTask() {
		try {
			TaskOperate.remove(9999);
		} catch (Exception e) {
			logger.error("获取挂起失败",e);
		}
	}

}
