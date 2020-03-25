package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.UploadproduceReportTask;



public class AutoUploadProduceReportTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		UploadproduceReportTask reportTask = new UploadproduceReportTask();
		reportTask.run();
	}
	
}
