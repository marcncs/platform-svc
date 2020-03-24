package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException; 

import com.winsafe.drp.task.SyncIAMUsersTask;

public class DealSyncIAMUsersTask implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SyncIAMUsersTask task = new SyncIAMUsersTask();
		task.run(); 
	}

}
