package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.SMSDealTask;

public class AutoSMSDealTask implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SMSDealTask smsDealTask = new SMSDealTask();
		smsDealTask.run();
	}
}
