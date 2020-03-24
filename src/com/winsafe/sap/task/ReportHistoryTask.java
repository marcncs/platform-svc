package com.winsafe.sap.task;

import org.apache.log4j.Logger;

import com.winsafe.drp.server.ReportHistoryService;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

/*******************************************************************************************  
 * 定时任务类,生成销售，消耗和库存明细历史报表数据
 * @author: ryan.xi	  
 * @date：2017-06-01  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2017-06-01   ryan.xi                
 *******************************************************************************************  
 */  
public class ReportHistoryTask {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(ReportHistoryTask.class);
	/**
	 * 定时任务入口方法
	 */
	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					isRunning = true;
					logger.info(DateUtil.getCurrentDate()
							+ " 生成历史报表数据任务---开始---");
					execute();
				} catch (Exception e) {
					logger.error(e);
					logger.info(DateUtil.getCurrentDate()
							+ " 生成历史报表数据发生异常" + e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate()
							+ " 生成历史报表数据任务---结束---");
				}
			}
		}
	}
	
	/**
	 * 生成历史数据
	 * @throws Exception
	 */
	public void execute() throws Exception {
		ReportHistoryService rhs = new ReportHistoryService();
		//生成销售明细历史数据
		rhs.genSalesDetailReportHistory();
		//生成消耗明细历史数据
		rhs.genConsumeDetailReportHistory();
		//生成库存明细历史数据
		rhs.genInventoryDetailReportHistory();
	}
	
}
