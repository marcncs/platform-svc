package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.erp.task.TollerPrimaryCodeGenTask;

public class AutoTollerPrimaryCodeGenTask implements Job {

	
	@Override 
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		TollerPrimaryCodeGenTask task = new TollerPrimaryCodeGenTask();
		task.run();
	}
	
}
