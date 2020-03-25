package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.sap.task.CommonCodeGenerateTask;

public class DealCommonCodeGenerateTask implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		CommonCodeGenerateTask task = new CommonCodeGenerateTask();
		task.run();
	}
}
