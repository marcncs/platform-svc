package com.winsafe.drp.keyretailer.task;


import org.apache.log4j.Logger;

import com.winsafe.drp.keyretailer.util.SmsValidateCodesUtil;
import com.winsafe.hbm.util.DateUtil; 

public class SMSValidateCodeCheckTask  {
	//日志
	private static Logger logger = Logger.getLogger(SMSValidateCodeCheckTask.class);
	//同步锁
	private static Object lock = new Object();
	private static boolean isRunning=false;
	
	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug(DateUtil.getCurrentDate() +":短信验证码任务任务开始.");
					isRunning = true;
					execute();
				} catch (Exception e) {
					logger.error(e);
					logger.info(DateUtil.getCurrentDate()
							+ " 短信验证码任务发生异常" + e.getMessage());
				} finally {
					isRunning = false;
					logger.info(DateUtil.getCurrentDate()
							+ " 短信验证码任务---结束---");
				}
			}
		}
	}
	
	private void execute() {
		SmsValidateCodesUtil.checkAndRemove();
	}

}
