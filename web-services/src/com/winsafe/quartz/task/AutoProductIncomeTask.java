package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.task.ProductInComeTask;

public class AutoProductIncomeTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ProductInComeTask produceIncomeTask = new ProductInComeTask();
		produceIncomeTask.run();
	}
	
}
