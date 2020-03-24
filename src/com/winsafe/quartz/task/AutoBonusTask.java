package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.keyretailer.task.BonusTask;

public class AutoBonusTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		BonusTask task = new BonusTask();
		task.run();
	}
	
}
