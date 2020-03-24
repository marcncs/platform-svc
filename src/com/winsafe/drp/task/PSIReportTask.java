package com.winsafe.drp.task;


import com.winsafe.drp.task.psi.PSIReportService;
import com.winsafe.drp.util.DBUserLog;

public class PSIReportTask extends Thread{
	
	private PSIReportService psiReportService ;
	
	
	//同步锁
	private static Object lock = new Object();
	private static boolean isRunning=false;


	
	/** 初始化数据*/
	public PSIReportTask(){
	}
	
	
	@Override
	public void run() {
		if (!isRunning) {
			synchronized(lock){
				try {
					isRunning = true;
					DBUserLog.addUserLog1(1, "PSI报表数据生成任务---开始---");
					//执行历史库存报表数据生成任务
					psiReportService = new PSIReportService();
					psiReportService.producePSIReportData();
					
				} catch (Exception e) {
					try {
						DBUserLog.addUserLog1(1, "PSI报表数据生成任务---异常---");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}finally{
					isRunning = false;
					try {
						DBUserLog.addUserLog1(1, "PSI报表数据生成任务---结束---");
					} catch (Exception e) {
					}
				}
				
				
			}
		}
	}

}
