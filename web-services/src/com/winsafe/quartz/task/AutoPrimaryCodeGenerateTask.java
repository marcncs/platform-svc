package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.sap.task.PrePrimaryCodeGenerateTask;

public class AutoPrimaryCodeGenerateTask implements Job {

	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		PrePrimaryCodeGenerateTask task = new PrePrimaryCodeGenerateTask();
		task.run();
	}
	
}
