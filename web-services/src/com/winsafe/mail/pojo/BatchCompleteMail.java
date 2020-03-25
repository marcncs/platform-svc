package com.winsafe.mail.pojo;

import java.util.Date;

public class BatchCompleteMail {

	private long mailId;
	private String mailSender;
	private String mailFrom;
	private String mailTo;
	private String mailCc;
	private String mailBcc;
	private String mailSubject;
	private String mailBody;
	private String mailAttachment;
	private Date createDate;
	private Date sentDate;
	private int isSent = 0;
	private String moblieApprovalId;
	private int wrongTimes = 0;
    private String missMailAddress;
    private int mailType;
    private String mailDateType;
    
    
	public String getMissMailAddress() {
		return missMailAddress;
	}

	public void setMissMailAddress(String missMailAddress) {
		this.missMailAddress = missMailAddress;
	}

	public long getMailId() {
		return mailId;
	}

	public void setMailId(long mailId) {
		this.mailId = mailId;
	}

	public String getMailSender() {
		return mailSender;
	}

	public void setMailSender(String mailSender) {
		this.mailSender = mailSender;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getMailCc() {
		return mailCc;
	}

	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}

	public String getMailBcc() {
		return mailBcc;
	}

	public void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public String getMailAttachment() {
		return mailAttachment;
	}

	public void setMailAttachment(String mailAttachment) {
		this.mailAttachment = mailAttachment;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public int getIsSent() {
		return isSent;
	}

	public void setIsSent(int isSent) {
		this.isSent = isSent;
	}

	public int getWrongTimes() {
		return wrongTimes;
	}

	public void setWrongTimes(int wrongTimes) {
		this.wrongTimes = wrongTimes;
	}

	public String getMoblieApprovalId() {
		return moblieApprovalId;
	}

	public void setMoblieApprovalId(String moblieApprovalId) {
		this.moblieApprovalId = moblieApprovalId;
	}

	public int getMailType() {
		return mailType;
	}

	public void setMailType(int mailType) {
		this.mailType = mailType;
	}

	public String getMailDateType() {
		return mailDateType;
	}

	public void setMailDateType(String mailDateType) {
		this.mailDateType = mailDateType;
	}

}
