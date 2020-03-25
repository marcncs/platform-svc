package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.keyretailer.task.SMSValidateCodeCheckTask;


public class ClearSMSValidateCodeTask implements Job {
	@Override 
	public void execute(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		SMSValidateCodeCheckTask task = new SMSValidateCodeCheckTask();
		task.run();
	}
}
