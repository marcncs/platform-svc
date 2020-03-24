package com.winsafe.drp.util.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.winsafe.drp.task.ProductInComeTask;

public class TaskListener implements ServletContextListener  {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//        Thread aIdcodeScanReportThread=new IdcodeScanReportThread();
//        aIdcodeScanReportThread.setDaemon(true);
//        aIdcodeScanReportThread.start();
			Thread produceIncomeTask = new ProductInComeTask();
			produceIncomeTask.start();
	}

}
