package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.FIFOAlertReportTask;

public class DealFIFOAlertReportTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		FIFOAlertReportTask task = new FIFOAlertReportTask();
		task.run();
	}
	
}
