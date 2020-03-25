package com.winsafe.mail.task;

import java.util.Date;
import java.util.List;

import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MailService;
import com.winsafe.mail.dao.AppBatchCompleteMail;
import com.winsafe.mail.pojo.BatchCompleteMail;

public class MailSendTask {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private AppBatchCompleteMail abcm = new AppBatchCompleteMail();
	private List<BatchCompleteMail> mails;

	/**
	 * 初始化路径
	 */
	public void init() throws Exception{
		mails = abcm.getMailList();
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					this.init();
					isRunning = true;
					WfLogger.error(DateUtil.getCurrentDate() + "自动处理邮件发送任务---开始---");
					if(mails != null && mails.size() > 0) {
						execute(mails);
					}
				} catch (Exception e) {
					WfLogger.error("自动处理邮件发送任务---发生异常---", e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					WfLogger.error(DateUtil.getCurrentDate() + "自动处理邮件发送任务---结束---");
				}
			}
		}

	}
	
	/**
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void execute(List<BatchCompleteMail> mailList){
		WfLogger.debug("start send mail id");
		WfLogger.debug(">>>>>>mail count:"+mailList.size()+"<<<<<<");
		try {
			for (int i = 0; i < mailList.size(); i++) {
				BatchCompleteMail mailBatch = (BatchCompleteMail) mailList.get(i);
				try {
					MailService mailService = new MailService();
					mailService.setMailFrom(mailBatch.getMailFrom());
					mailService.addMailTo(mailBatch.getMailTo(),",","TO");
					mailService.addMailTo(mailBatch.getMailCc(),",","CC");
					mailService.setSubject(mailBatch.getMailSubject());
					mailService.setContent(mailBatch.getMailBody());
					mailService.sendMail();
				} catch (Exception e) {
					WfLogger.error("Error occurs while sending mail:"+mailBatch.getMailId(), e);
					mailBatch.setWrongTimes(mailBatch.getWrongTimes() + 1);
					abcm.update(mailBatch);
					HibernateUtil.commitTransaction();
					continue;
				}
				mailBatch.setSentDate(new Date());
				mailBatch.setIsSent(1);
				abcm.update(mailBatch);
				WfLogger.debug(">>>>>>One mail has been sent successfully!<<<<<<");
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			WfLogger.error("Error occurs while sending mails",e);
		}
	}
	
}
