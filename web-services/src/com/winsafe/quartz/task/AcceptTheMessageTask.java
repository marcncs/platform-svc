package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.util.DealMessageAcceptListener;

public class AcceptTheMessageTask implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		DealMessageAcceptListener task = new DealMessageAcceptListener();
		task.run();
	}

}
