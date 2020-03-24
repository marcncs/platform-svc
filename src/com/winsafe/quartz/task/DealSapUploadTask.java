package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.sap.task.SapUploadTask;

public class DealSapUploadTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SapUploadTask task = new SapUploadTask();
		task.run(false);
	}
	
}
