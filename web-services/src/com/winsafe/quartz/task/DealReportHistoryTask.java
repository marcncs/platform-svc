package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.sap.task.ReportHistoryTask;

public class DealReportHistoryTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ReportHistoryTask task = new ReportHistoryTask();
		task.run();
	}
	
}
