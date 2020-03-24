package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.mail.task.MailSendTask;

public class AutoMailSendTask implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		MailSendTask task = new MailSendTask();
		task.run();
	}

}
