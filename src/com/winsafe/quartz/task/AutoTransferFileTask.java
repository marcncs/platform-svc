package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext; 
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.TransferFileTask;

public class AutoTransferFileTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		TransferFileTask task = new TransferFileTask();
		task.run();
	}
	
}
