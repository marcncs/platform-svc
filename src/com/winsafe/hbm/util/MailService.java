package com.winsafe.hbm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppSystemResource;
import com.winsafe.drp.dao.SystemResource;
import com.winsafe.drp.util.SysConfig;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.mail.smtp.sender.Mail;

public class MailService {
	private Logger logger = Logger.getLogger(MailService.class);

	private String mailSplit = ",";
	private static final String CC = "CC";
	private static final String BCC = "BCC";
	private static final String TO = "TO";

	private Set<String> toMailSet = new LinkedHashSet<String>();
	private Set<String> ccSet = new LinkedHashSet<String>();
	private Set<String> bccSet = new LinkedHashSet<String>();
	private AppSystemResource appsr = new AppSystemResource();
	private Set<String> attachments = new LinkedHashSet<String>();
	private String subject;
	private String content;
	private String mailFrom;

	public MailService() {
	}

	/**
	 * 邮件分隔符
	 * 
	 * @param mailSplit
	 */
	public MailService(String mailSplit) {
		this.mailSplit = mailSplit;
	}

	/**
	 * @param mails
	 * @param split
	 * @param type
	 *            (to|cc|bcc)
	 */
	public void addMailTo(List<String> mails, String split, String type) {
		for (String s : mails) {
			addMailTo(s, split, type);
		}
	}

	/**
	 * @param mails
	 * @param split
	 * @param type
	 *            (to|cc|bcc)
	 */
	public void addMailTo(String mails, String split, String type) {
		if (StringUtil.isEmpty(split)) {
			addMailTo(mails, type);
			return;
		}
		if (StringUtil.isEmpty(mails)) {
			return;
		}
		String[] values = mails.split(split);
		for (String v : values) {
			addMailTo(v, type);
		}
	}

	/**
	 * @param mail
	 * @param type
	 *            (to|cc|bcc)
	 */
	public void addMailTo(String mail, String type) {
		if (StringUtil.isEmpty(mail)) {
			return;
		}
		if (CC.equalsIgnoreCase(type)) {
			ccSet.add(mail);
		} else if (BCC.equalsIgnoreCase(type)) {
			bccSet.add(mail);
		} else if (TO.equalsIgnoreCase(type)) {
			toMailSet.add(mail);
		}

	}

	public void addAttachment(String filepath) {
		if (StringUtil.isEmpty(filepath)) {
			return;
		}
		attachments.add(filepath);
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void send() {

		try {
			boolean success = false;
			try {
				// 获取配置
				List<String> defaultCC = getValueList("邮件固定抄送人", "固定抄送人");
				addMailTo(defaultCC, mailSplit, CC);

				String tagName = "邮件服务";
				List<String> smtpList = getValueList(tagName, "SMTP");
				List<String> userNameList = getValueList(tagName, "USERNAME");
				List<String> pwdList = getValueList(tagName, "PASSWORD");
				List<String> fromList = getValueList(tagName, "发件人");

				String smtp;
				String username;
				String password;
				String from;
				for (int i = 0; i < smtpList.size(); i++) {
					try {
						smtp = smtpList.get(i);
						username = userNameList.get(i);
						password = pwdList.get(i);
						from = fromList.get(i);
						send(smtp, username, password, from);

						success = true;
						break;
					} catch (Exception e) {

						WfLogger.error("邮件发送失败", e);
						continue;
					}
				}
			} catch (Exception e) {
				WfLogger.error("", e);
			}
			if (!success) {
				defaultSend();
			}
		} catch (Exception e) {
			WfLogger.error("", e);
		}
	}

	public boolean sendMail() {
		boolean success = false;
		try {
			String smtp = SysConfig.getSysConfig().getProperty("mail.smtp.host");
			String username = SysConfig.getSysConfig().getProperty("mail.username");
			String password = SysConfig.getSysConfig().getProperty("mail.password");
			//String from = SysConfig.getSysConfig().getProperty("mail.from");
			try {
				send(smtp, username, password, mailFrom);

				success = true;
			} catch (Exception e) {
				WfLogger.error("邮件发送失败", e);
			}
		} catch (Exception e) {
			WfLogger.error("", e);
		}
		return success;
	}

	private void setMail(MailSender mailSender) throws Exception {
		if (!toMailSet.isEmpty()) {
			mailSender.setMailTo(toMailSet.toArray(new String[0]), "TO");
		}
		if (!ccSet.isEmpty()) {
			mailSender.setMailTo(ccSet.toArray(new String[0]), "cc");
		}
		if (!bccSet.isEmpty()) {
			mailSender.setMailTo(bccSet.toArray(new String[0]), "bcc");
		}
		mailSender.setSubject(subject);
		mailSender.setMailContent(content);

		if (!attachments.isEmpty()) {
			for (String filepath : attachments) {
				mailSender.setAttachments(filepath);
			}
		}
	}
	
	private String setBody(String content) throws MessagingException, IOException{
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML>\n");
		sb.append("<HEAD>\n");
		sb.append("<TITLE>\n");
		sb.append(subject + "\n");
		sb.append("</TITLE>\n");
		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		sb.append("</HEAD>\n");
		sb.append("<BODY style='font-family: Calibri;font-size: 16px;'>\n"); 
		sb.append(addHref(content).replaceAll("\r", "<br>"));
		sb.append("</BODY>\n");
		sb.append("</HTML>\n");

		/*msg.setDataHandler(new DataHandler(
				new ByteArrayDataSource(sb.toString(), "text/html;charset=utf8")));*/
		//add wqj by 2012-05-16
		//msg.setContent(sb.toString(),"text/html;charset=utf8");
		
		return sb.toString();

	}
	
	public    String addHref(String title){
		StringBuffer ret = new StringBuffer();
		String regEx= "(http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)){1,}?";
		Pattern p=Pattern.compile(regEx,Pattern.CASE_INSENSITIVE); 
		Matcher m=p.matcher(title); 
		int j=0; 
		for(int i=0;m.find() ;i++){
			ret.append(title.substring(j,m.start(i)));
			ret.append("<a href='"+m.group(i)+"'>");
			ret.append(title.substring(m.start(i) ,m.end(i) ));
			ret.append("</a>"); 
			j=m.end(i);
		} 
		ret.append(title.substring(j));
 		if(j==0)
			return title;
		return ret.toString();
	}

	private void send(final String smtpHost, final String username, final String password, final String mailFrom)
			throws Exception {

		logger.info("正在发邮件，请稍侯......");
		
		Properties props = new Properties();
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", smtpHost);
	    props.put("mail.smtp.ssl.trust", smtpHost);
	    props.put("mail.smtp.port", SysConfig.getSysConfig().getProperty("mail.smtp.port"));
		
		Session mailSession = Session.getInstance(props, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		MimeMessage mailMessage = new MimeMessage(mailSession);
		InternetAddress mailFromAddress = new InternetAddress(mailFrom);
		mailMessage.setFrom(mailFromAddress);

		if (!toMailSet.isEmpty()) {
			setMailTo(mailMessage, toMailSet.toArray(new String[0]), "TO");
		}
		if (!ccSet.isEmpty()) {
			setMailTo(mailMessage, ccSet.toArray(new String[0]), "cc");
		}
		if (!bccSet.isEmpty()) {
			setMailTo(mailMessage, bccSet.toArray(new String[0]), "bcc");
		}
		mailMessage.setSentDate(new Date());
		mailMessage.setSubject(MimeUtility.encodeText(subject,"utf8","B"));
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		content=setBody(content);
		messageBodyPart.setContent(content,"text/html;charset=utf8");
		Multipart multipart = new MimeMultipart("related");
		multipart.addBodyPart(messageBodyPart);

		if (!attachments.isEmpty()) {
			for (String filepath : attachments) {
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(filepath);
				messageBodyPart.setDataHandler(new DataHandler(source));
				File file = new File(filepath);
				messageBodyPart.setFileName(file.getName());
				multipart.addBodyPart(messageBodyPart);
			}
		}
		mailMessage.setContent(multipart);
		mailMessage.saveChanges();
		Transport.send(mailMessage);

		logger.info("邮件已经成功发送!");
	}

	private void setMailTo(MimeMessage mailMessage, String[] mailTo, String mailType) throws Exception {
		InternetAddress mailToAddress;
		for (int i = 0; i < mailTo.length; i++) {
			mailToAddress = new InternetAddress(mailTo[i]);
			if (mailType.equalsIgnoreCase("to")) {
				mailMessage.addRecipient(Message.RecipientType.TO, mailToAddress);
			} else if (mailType.equalsIgnoreCase("cc")) {
				mailMessage.addRecipient(Message.RecipientType.CC, mailToAddress);
			} else if (mailType.equalsIgnoreCase("bcc")) {
				mailMessage.addRecipient(Message.RecipientType.BCC, mailToAddress);
			} else {
				throw new Exception("Unknown mailType" + mailType + "!");
			}
		}
	}

	private void defaultSend() throws Exception {
		InputStream in = null;
		try {
			String path = MailSender.class.getResource("/mail.properties").getPath();
			in = new BufferedInputStream(new FileInputStream(path));
			Properties p = new Properties();
			p.load(in);

			List<String> smtpList = getPropertyValueList(p, "mailsender_smtp");
			List<String> userNameList = getPropertyValueList(p, "mailsender_username");
			List<String> pwdList = getPropertyValueList(p, "mailsender_password");
			List<String> fromList = getPropertyValueList(p, "mailsender_from_mail");

			String smtp;
			String username;
			String password;
			String from;
			for (int i = 0; i < smtpList.size(); i++) {
				try {
					smtp = smtpList.get(i);
					username = userNameList.get(i);
					password = pwdList.get(i);
					from = fromList.get(i);
					send(smtp, username, password, from);
					break;
				} catch (Exception e) {
					WfLogger.error("邮件发送失败", e);
					continue;
				}
			}

			// MailSender mailSender = new MailSender(smtp,username,password);
			// mailSender.setSendDate(new Date());
			// mailSender.setMailFrom(from);
			//
			// setMail(mailSender);
			//
			// mailSender.sendMail();

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					WfLogger.error("", e);
				}
			}
		}
	}

	private List<String> getValueList(String tagName, String tagkey) throws Exception {
		List<SystemResource> srlist = appsr.querySystemResourceValue(tagName, tagkey);

		List<String> list = new ArrayList<String>();
		for (SystemResource sr : srlist) {
			list.add(sr.getTagvalue());
		}
		return list;
	}

	private List<String> getPropertyValueList(Properties p, String key) {
		List<String> list = new ArrayList<String>();
		String value = p.getProperty(key);
		if (!StringUtil.isEmpty(value)) {
			value = value.trim();
			list.add(value);
		}
		for (int i = 1;; i++) {
			value = p.getProperty(key + i);
			if (!StringUtil.isEmpty(value)) {
				value = value.trim();
				list.add(value);
			} else {
				return list;
			}
		}
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	/*public void sendMail() {
		try {
			defaultSend();
		} catch (Exception e) {
			WfLogger.error("发送邮件是发生异常", e);
		}
	}*/
}
