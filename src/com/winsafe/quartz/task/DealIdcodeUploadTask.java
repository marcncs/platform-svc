package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.util.IdcodeUploadListener;

public class DealIdcodeUploadTask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		IdcodeUploadListener task = new IdcodeUploadListener();
		task.run();
	}
	
}
