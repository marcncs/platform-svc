package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.sap.task.TenDigitsPrimaryCodeGenerateTask;

public class AutoTenDigitsPrimaryCodeGenerateTask  implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		TenDigitsPrimaryCodeGenerateTask task = new TenDigitsPrimaryCodeGenerateTask();
		task.run();
	}
}
