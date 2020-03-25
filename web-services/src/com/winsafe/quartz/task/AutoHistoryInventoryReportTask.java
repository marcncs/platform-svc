package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.HistoryInventoryReportTask;

public class AutoHistoryInventoryReportTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		HistoryInventoryReportTask task = new HistoryInventoryReportTask();
		task.run();
	}
	
}
