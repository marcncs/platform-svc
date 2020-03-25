package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.InventoryUploadTask;

public class AutoInventoryUploadTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		InventoryUploadTask task = new InventoryUploadTask();
		task.run();
	}
	
}
