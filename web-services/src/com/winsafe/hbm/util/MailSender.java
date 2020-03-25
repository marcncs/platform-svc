package com.winsafe.hbm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.winsafe.hbm.util.MailSender;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MailSender extends Authenticator {
	private Logger logger = Logger.getLogger(MailSender.class);
	private String username = null; 

	private String userpasswd = null; 

	protected BodyPart messageBodyPart = null;

	protected Multipart multipart = new MimeMultipart("related");

	protected MimeMessage mailMessage = null;

	protected Session mailSession = null;

	protected Properties mailProperties = System.getProperties();

	protected InternetAddress mailFromAddress = null;

	protected InternetAddress mailToAddress = null;

	protected Authenticator authenticator = null;

	protected String mailSubject = "";

	protected Date mailSendDate = null;

	protected String mailContent = null;
	
	private String defaultFromMailAddress="";


	public MailSender(String smtpHost, String username, String password) {
		this.username = username;
		this.userpasswd = password;
		mailProperties.put("mail.smtp.host", smtpHost);
		mailProperties.put("mail.smtp.auth", "true");
		mailSession = Session.getDefaultInstance(mailProperties, this);
		mailMessage = new MimeMessage(mailSession);
		messageBodyPart = new MimeBodyPart();

	}


	public void setMailContent(String content) throws MessagingException {
		mailContent = content;
		messageBodyPart
				.setContent(mailContent, "text/html; charset=UTF-8");
		multipart.addBodyPart(messageBodyPart);
		logger.info("正在发邮件，请稍侯......");
	}
	

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, userpasswd);
	}


	public void setSubject(String mailSubject) throws MessagingException {
		this.mailSubject = mailSubject;
		mailMessage.setSubject(mailSubject);
	}


	public void setSendDate(Date sendDate) throws MessagingException {
		this.mailSendDate = sendDate;
		mailMessage.setSentDate(sendDate);
	}

	public void setAttachments(String attachmentName) throws MessagingException {
		messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(attachmentName);
		messageBodyPart.setDataHandler(new DataHandler(source));
		int index = attachmentName.lastIndexOf(File.separator);
		String attachmentRealName = attachmentName.substring(index + 1);
		messageBodyPart.setFileName(attachmentRealName);
		multipart.addBodyPart(messageBodyPart);
	}


	public void setMailFrom(String mailFrom) throws MessagingException {
		mailFromAddress = new InternetAddress(mailFrom);
		mailMessage.setFrom(mailFromAddress);
	}


	public void setMailTo(String[] mailTo, String mailType) throws Exception {
		for (int i = 0; i < mailTo.length; i++) {
			mailToAddress = new InternetAddress(mailTo[i]);
			if (mailType.equalsIgnoreCase("to")) {
				mailMessage.addRecipient(Message.RecipientType.TO,
						mailToAddress);
			} else if (mailType.equalsIgnoreCase("cc")) {
				mailMessage.addRecipient(Message.RecipientType.CC,
						mailToAddress);
			} else if (mailType.equalsIgnoreCase("bcc")) {
				mailMessage.addRecipient(Message.RecipientType.BCC,
						mailToAddress);
			} else {
				throw new Exception("Unknown mailType" + mailType + "!");
			}
		}
	}

	public void sendMail() throws MessagingException, SendFailedException {
//		logger.error("smtp:"+mailProperties.get("mail.smtp.host"));
//		logger.error("username:"+username);
//		logger.error("password:"+userpasswd);
//		logger.error("from:"+mailFromAddress.getAddress());

		if (mailToAddress == null)
			throw new MessagingException("请你必须你填写收件人地址!");
		mailMessage.setContent(multipart);
		try {
			Transport.send(mailMessage);
		} catch (SendFailedException se) {
			logger.info("mail exception:"+se.toString());
			se.printStackTrace();
			throw se;
		}

		logger.info("恭喜你，邮件已经成功发送!");

	}
	
	
}
