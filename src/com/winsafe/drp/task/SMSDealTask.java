package com.winsafe.drp.task;

import java.util.List;

import org.apache.log4j.Logger;

import com.winsafe.drp.metadata.SmsSendStatus;
import com.winsafe.drp.util.SmsUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sms.dao.AppSms;
import com.winsafe.sms.pojo.Sms;

public class SMSDealTask {
	private static Logger logger = Logger.getLogger(SMSDealTask.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private AppSms appSms = new AppSms();
	private List<Sms> smses;

	/**
	 * 初始化路径
	 */
	public void init() throws Exception{
		String whereHql = " where sendStatus = "+SmsSendStatus.NOT_SEND.getDbValue()+ " and tryCount > 0";
		smses = appSms.getSmsByWhere(whereHql);
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					this.init();
					isRunning = true;
//					DBUserLog.addUserLog1(1,"自动处理物流信息通知---开始---");
					for(Sms sms : smses) {
						execute(sms);
					}
				} catch (Exception e) {
					logger.error("自动发送SMS信息发生异常", e);
					try {
//						DBUserLog.addUserLog1(1, "自动处理物流信息通知发生异常");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					try {
//						DBUserLog.addUserLog1(1, "自动处理物流信息通知---结束---");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	/**
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void execute(Sms sms){
		logger.debug("send sms message to:" + sms.getMobileNo());
		try {
			if(isValidMobile(sms.getMobileNo())) {
				int result = SmsUtil.sendSmsBySmsTerrace(sms.getMobileNo(), sms.getContent());
				sms.setTryCount(sms.getTryCount() - 1);
				if(result == 1 || result == 49) {
					sms.setSendStatus(SmsSendStatus.SUCCESS.getDbValue());
				} else if(sms.getTryCount() == 0) {
					sms.setSendStatus(SmsSendStatus.FAILED.getDbValue());
				}
			} else {
				sms.setSendStatus(SmsSendStatus.FAILED.getDbValue());
			} 			
			sms.setSendTime(DateUtil.getCurrentDate());
			appSms.updSms(sms);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("error ocurred when sending sms message", e);
		}
	}
	
	private boolean isValidMobile(String  mobile) {
		if(!StringUtil.isEmpty(mobile)) {
			for(String mb : mobile.split(",")) {
				if(mb.length() != 11 || !mb.matches("[0-9]*")) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
