package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.DealCodeReplaceTask;

public class AutoDealTollingCodeUploadTask implements Job {

	@Override 
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		DealCodeReplaceTask task = new DealCodeReplaceTask();
		task.run(); 
	}
	
}
