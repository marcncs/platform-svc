package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.keyretailer.task.BonusRebateTask;

public class AutoBonusRebateTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		BonusRebateTask task = new BonusRebateTask();
		task.run();
	}
	
}
