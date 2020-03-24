package com.winsafe.quartz.task;

import org.quartz.Job; 
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.OrganUploadTask;

public class AutoOrganUploadTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		OrganUploadTask task = new OrganUploadTask();
		task.run();
	}
	
}
