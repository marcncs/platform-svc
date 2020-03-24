package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext; 
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.CreateCSSITransferFileTask;

public class AutoCreateCSSITransferFileTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		CreateCSSITransferFileTask task = new CreateCSSITransferFileTask();
		task.run();
	}
	
}
