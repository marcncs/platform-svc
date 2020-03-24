package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.NotificationSftpTask;
import com.winsafe.drp.task.NotificationTask;



public class AutoNotificationTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		NotificationSftpTask reportTask = new NotificationSftpTask();
		reportTask.run();
	}
	
}
