package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.QueryAreaTask;

public class AutoQueryAreaTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		QueryAreaTask task = new QueryAreaTask();
		task.run();
	}
	
}