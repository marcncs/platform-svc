package com.winsafe.drp.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger; 

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.metadata.StockMoveConfirmStatus;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.mail.dao.AppBatchCompleteMail;
import com.winsafe.mail.metadata.MailType;
import com.winsafe.mail.pojo.BatchCompleteMail;
import com.winsafe.mail.smtp.base.SMTPMailFactory;

public class AuditMailUtil {
	private static Logger logger = Logger.getLogger(AuditMailUtil.class);
	private static AppBatchCompleteMail abcm = new AppBatchCompleteMail();
	
	public static void addNotificationMail(MoveApply ma) throws Exception {
		String mailBodyName = "";
		AppUsers appUser = new AppUsers();
		List<Users> auditUsers = new ArrayList<Users>();
		Users makeUser = appUser.getUsersByid(ma.getMakeid());
		StockMoveConfirmStatus status = StockMoveConfirmStatus.parseByValue(ma.getIsratify());
		if(StockMoveConfirmStatus.OUT_ASM_APPROVED == status) {
			mailBodyName = "smtoaudit_mail_body";
			auditUsers = appUser.getUsersByRoleName("转仓审批一");
		} else if(StockMoveConfirmStatus.CHANNEL_MANAGER_APPROVED == status) {
			mailBodyName = "smtoauditasm_mail_body";
			auditUsers = appUser.getUsersByCustomerId(ma.getInorganid());
		} else if(StockMoveConfirmStatus.IN_ASM_APPROVED == status) {
			mailBodyName = "smtoaudit_mail_body";
			auditUsers = appUser.getUsersByRoleName("转仓审批二");
		} else if(StockMoveConfirmStatus.NOT_AUDITED == status) {
			mailBodyName = "smtoauditasm_mail_body";
			auditUsers = appUser.getUsersByCustomerId(ma.getOutorganid());
		}
		for(Users user : auditUsers) {
			if(!StringUtil.isEmpty(user.getEmail())) {
				addToAuditNotificationMail(user, ma.getId(),mailBodyName);
			}
		}
		if(StockMoveConfirmStatus.NOT_AUDITED != status) {
			if(makeUser != null && !StringUtil.isEmpty(makeUser.getEmail())) {
				addAuditNotificationMail(makeUser, status, ma);
			}
		}
	}
	private static void addAuditNotificationMail(Users makeUser,
			StockMoveConfirmStatus status, MoveApply ma) {
		BatchCompleteMail mail = getMail(makeUser);
		Properties mailPro = SMTPMailFactory.getSMTPMailFactory();
		String mailBody = "";
		if(status.getValue() < 0) {
			mailBody = MessageFormat.format(mailPro.getProperty("smdisapprove_mail_body"),ma.getId(),status.getName(), ma.getBlankoutreason());
		} else {
			mailBody = MessageFormat.format(mailPro.getProperty("smapprove_mail_body"),ma.getId(),status.getName());
		}
		mail.setMailBody(mailBody);//内容
		abcm.add(mail);
	}
	
	private static BatchCompleteMail getMail(Users user) {
		BatchCompleteMail mail = new BatchCompleteMail();
		Properties mailPro = SMTPMailFactory.getSMTPMailFactory();
		mail.setMailSender(mailPro.getProperty("Bayer_smtp_from"));
		mail.setMailFrom(mailPro.getProperty("Bayer_smtp_from"));
		mail.setMailTo(user.getEmail());//发送给谁
		mail.setMailSubject(mailPro.getProperty("sm_mail_subject"));//邮件标题
		mail.setCreateDate(DateUtil.getCurrentDate());
		mail.setMailType(MailType.STOCK_MOVE_AUDIT.getDbValue());
		return mail;
	}
	
	private static void addToAuditNotificationMail(Users user, String billNo, String mailBodyName) {
		BatchCompleteMail mail = getMail(user);
		Properties mailPro = SMTPMailFactory.getSMTPMailFactory();
		String mailBody = MessageFormat.format(mailPro.getProperty(mailBodyName),billNo);
		mail.setMailBody(mailBody);//内容
		abcm.add(mail);
	}
	
	
	/**
	 * 通知管理员完善IAM新用户信息通知邮件
	 * @param userNames
	 */
	public static void addIAMNewUserMail(List<String> userNames) {
		try {
			BatchCompleteMail mail = new BatchCompleteMail();
			Properties mailPro = SMTPMailFactory.getSMTPMailFactory();
			mail.setMailSender(mailPro.getProperty("Bayer_smtp_from"));
			mail.setMailFrom(mailPro.getProperty("Bayer_smtp_from"));
			mail.setMailTo(mailPro.getProperty("iam_mailTo"));
			mail.setMailSubject(mailPro.getProperty("iam_mailSubject"));
			StringBuffer unames = new StringBuffer();
			for(String userName : userNames) {
				unames.append(","+userName);
			}
			String mailBody = MessageFormat.format(mailPro.getProperty("iam_mail_body"),"\r\n"+unames.substring(1));
			mail.setMailBody(mailBody);
			mail.setCreateDate(DateUtil.getCurrentDate());
			mail.setMailType(MailType.DELIVERY_ERROR.getDbValue());
			abcm.add(mail);
		} catch (Exception e) {
			logger.error("添加更新邮件时异常：",e);
		}
	}
}
