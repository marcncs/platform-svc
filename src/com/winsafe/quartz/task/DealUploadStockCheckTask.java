package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.util.UploadStockCheckListener;

public class DealUploadStockCheckTask implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		UploadStockCheckListener task = new UploadStockCheckListener();
		task.run();
	}

}
