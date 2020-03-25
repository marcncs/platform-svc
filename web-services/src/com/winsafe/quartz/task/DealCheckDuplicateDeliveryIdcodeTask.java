package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.CheckDuplicateDeliveryIdcodeTask;

public class DealCheckDuplicateDeliveryIdcodeTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		CheckDuplicateDeliveryIdcodeTask task = new CheckDuplicateDeliveryIdcodeTask();
		task.run();
	}
	
	
}
