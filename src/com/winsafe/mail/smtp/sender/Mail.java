package com.winsafe.mail.smtp.sender;

public class Mail {
    private String from;
    private String[] to=null;
    private String[] cc=null;
    private String[] bc=null;
    private String user;
    private String password;
    private String body;
    private String subject;
    private String attachment ;
    private String messageId;
    private String mailSender;
	
	 
	public String getMailSender() {
		return mailSender;
	}
	public void setMailSender(String mailSender) {
		this.mailSender = mailSender;
	}
	public Mail(){
		
	} 
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public String[] getBc() {
		return bc;
	}
	public void setBc(String[] bc) {
		this.bc = bc;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	} 
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
 
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String  getAttachment() {
		return attachment;
	}
	public void setAttachment(String  attachment) {
		this.attachment = attachment;
	}
}
