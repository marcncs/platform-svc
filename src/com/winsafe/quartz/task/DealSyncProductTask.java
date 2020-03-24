package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException; 

import com.winsafe.sap.task.SyncProductTask;

public class DealSyncProductTask implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SyncProductTask task = new SyncProductTask();
		task.run();  
	}

}
