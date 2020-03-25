package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.sap.task.HzPrimaryCodeAllocateTask;
import com.winsafe.sap.task.PrimaryCodeAllocateTask;

public class AutoPrimaryCodeAllocateTask implements Job {

	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//打印任务小包装码
		PrimaryCodeAllocateTask task = new PrimaryCodeAllocateTask();
		task.run(); 
		//码申请
		HzPrimaryCodeAllocateTask hzTask = new HzPrimaryCodeAllocateTask();
		hzTask.run();
	}
	
}
