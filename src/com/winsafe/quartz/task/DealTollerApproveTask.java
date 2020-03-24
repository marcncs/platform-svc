package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.util.TollerApproveListener;

public class DealTollerApproveTask implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		TollerApproveListener tollerTask = new TollerApproveListener();
		tollerTask.run();
	}
}
