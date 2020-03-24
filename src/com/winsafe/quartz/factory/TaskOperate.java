package com.winsafe.quartz.factory;

import javax.servlet.ServletContext;

import org.quartz.Scheduler;

import com.winsafe.quartz.task.pojo.TaskQuartz;

public class TaskOperate {
	
	public static void start(Integer taskId) throws Exception{
		Scheduler scheduler = InitTask.factoryMap.get(taskId);
		if(null != scheduler){
			scheduler.start();
		}
	}
	
	public static void create(TaskQuartz task,ServletContext application) throws Exception{
		//清除任务
		TaskOperate.remove(task.getId());
		
		//注册新任务
		InitTask initTask = (InitTask)application.getAttribute("initTask");
		initTask.setApplication(application);
		
		if(InitTask.doesAlreadyRegister(task.getPerformClass())){
			InitTask.registTask(task);
		}
		
	}
	public static void stop(Integer taskId) throws Exception{
		Scheduler scheduler = InitTask.factoryMap.get(taskId);
		if(null != scheduler){
    	  scheduler.standby();
		}
	}
	
	public static void remove(Integer taskId)throws Exception{
		Scheduler scheduler = InitTask.factoryMap.get(taskId);
		if(null != scheduler){
	    	scheduler.shutdown();
	    	InitTask.factoryMap.remove(taskId);
		}
    	
	}

}
