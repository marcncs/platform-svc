package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.SalesConsumHistoryTask;

public class DealSalesConsumHistoryTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SalesConsumHistoryTask task = new SalesConsumHistoryTask();
		task.run();
	}
	
}
