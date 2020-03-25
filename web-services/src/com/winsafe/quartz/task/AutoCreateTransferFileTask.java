package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext; 
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.CreateTransferFileTask;

public class AutoCreateTransferFileTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		CreateTransferFileTask task = new CreateTransferFileTask();
		task.run();
	}
	
}
