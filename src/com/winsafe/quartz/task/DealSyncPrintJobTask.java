package com.winsafe.quartz.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.winsafe.sap.task.SyncApplyQrCodeHzTask;
import com.winsafe.sap.task.SyncPrintJobTask;

public class DealSyncPrintJobTask implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//同步打印任务相关信息到杭州工厂
		SyncPrintJobTask task = new SyncPrintJobTask();
		task.run();  
		
		SyncApplyQrCodeHzTask saqTask = new SyncApplyQrCodeHzTask();
		saqTask.run();
	}

}
