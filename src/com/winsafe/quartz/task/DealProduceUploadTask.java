package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.ProduceUploadTask;

public class DealProduceUploadTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ProduceUploadTask task = new ProduceUploadTask();
		task.run(); 
	}
	
}
