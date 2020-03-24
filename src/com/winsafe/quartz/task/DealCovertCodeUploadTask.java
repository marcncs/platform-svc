package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.CovertCodeUploadTask;

public class DealCovertCodeUploadTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		CovertCodeUploadTask task = new CovertCodeUploadTask();
		task.run();
	}
	
}
