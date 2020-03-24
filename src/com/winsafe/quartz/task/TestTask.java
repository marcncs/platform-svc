package com.winsafe.quartz.task;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.drp.util.Dateutil;

public class TestTask implements Job{
	private Logger logger = Logger.getLogger(TestTask.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.debug("------任务执行----" + Dateutil.getCurrentDateTime() + "------");
	}

}
