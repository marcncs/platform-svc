package com.winsafe.drp.task;


import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppInventoryReport;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

public class HistoryInventoryReportTask {

	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(HistoryInventoryReportTask.class);

	private AppInventoryReport appInventoryReport = new AppInventoryReport();

	/**
	 * 初始化要处理的任务
	 */
	public void init() throws Exception {
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start processing sap upload file.");
					this.init();
					isRunning = true;
					logger.info(DateUtil.getCurrentDate()
							+ " 历史库存任务---开始---");
					execute();
				} catch (Exception e) {
					logger.error(e);
					logger.info(DateUtil.getCurrentDate() + " 历史库存任务发生异常"
							+ e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate()
							+ " 历史库存任务---结束---");
				}
			}
		}

	}

	/**
	 * @throws Exception
	 */
	public void execute() throws Exception {
		appInventoryReport.addHistoryReport();
	}
	
}
