package com.winsafe.drp.task;

import org.apache.log4j.Logger; 

import com.winsafe.erp.services.CheckDuplicateDeliveryIdcodeService;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

/**
 * 自动处理导入文件的Sap文件
 * 
 * @author Ryan.Xi
 * 
 */
public class CheckDuplicateDeliveryIdcodeTask {

	private static Logger logger = Logger
			.getLogger(CheckDuplicateDeliveryIdcodeTask.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.info(DateUtil.getCurrentDate() + " 发货重码检查任务---开始---");
					isRunning = true;
					startCheckDuplicateDeliveryIdcode();
				} catch (Exception e) {
					logger.info(DateUtil.getCurrentDate() + " 发货重码检查任务发生异常:"
							+ e.getMessage());
					logger.error("",e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate() + " 发货重码检查任务---结束---");
				}
			}
		}
	}
	
	private void startCheckDuplicateDeliveryIdcode() throws Exception {
		try {
			CheckDuplicateDeliveryIdcodeService service = new CheckDuplicateDeliveryIdcodeService();
			service.deal();
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw e;
		}
		
	}
	
	
}
