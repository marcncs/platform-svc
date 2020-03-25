package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.EncryptUserMobileTask;

public class DealEncryptUserMobileTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		EncryptUserMobileTask task = new EncryptUserMobileTask();
		task.run(); 
	}
	
}
