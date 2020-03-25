package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.PSIReportTask;

public class AutoPSIReportTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		PSIReportTask psiReportTask = new PSIReportTask();
		psiReportTask.run();
	}
	
}
