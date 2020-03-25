package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.sap.task.SapBillUploadTask;

public class DealSapBillUploadTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SapBillUploadTask task = new SapBillUploadTask();
		task.run(true);
	}
	
}
