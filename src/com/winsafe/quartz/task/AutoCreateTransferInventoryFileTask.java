package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext; 
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.CreateTransferInventoryFileTask;

public class AutoCreateTransferInventoryFileTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		CreateTransferInventoryFileTask task = new CreateTransferInventoryFileTask();
		task.run();
	}
	
}
