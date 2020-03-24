package com.winsafe.mail.smtp.sender;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 

import com.winsafe.mail.util.StringUtils;


public class MailSender {
	private String mailhost = null;
	private String mailer = "msgsend";
	private String port = null;
	private String user = null; 
	private String password = null;
	private String from = null;
	private String to = null;
	private boolean isNeedValidate;
	private boolean isTest;
	private Mail mail=null;
	private Log logger = LogFactory.getLog(MailSender.class);
	private Properties pro;
	public static final String APPL_PROPS_PATH="appl.props.path";
	public MailSender(Mail mail){
		this.mail=mail; 
	}
	public MailSender(Mail mail,Properties pro){
		this.mail=mail;
		this.pro=pro;
	}
	public MailSender(  String mailhost,   String port ,   String user,   String password ,  String from   ){
		this.mailhost=mailhost;
		this.port=port;
		this.user=user;
		this.password=password;
		this.from=from; 
	}
	public Properties getConnectParam(){
		 
		if(this.mailhost!=null){
			pro.setProperty("Bayer_smtp_Server", this.mailhost);
		}else{
			this.mailhost=pro.getProperty("Bayer_smtp_Server");
		}
		if(this.port!=null){
			pro.setProperty("Bayer_port", this.port);
		}else{
			this.port=pro.getProperty("Bayer_port");
		}
		if(this.user!=null){
			pro.setProperty("Bayer_smtp_username", this.user);
		}else{
			this.user=pro.getProperty(this.mail.getMailSender()+"SMTPID");
			if(StringUtils.isRealEmpty(this.user)){
				this.user = pro.getProperty("Bayer_smtp_username");
			}
		}
		if(this.password!=null){
			pro.setProperty("Bayer_smtp_password", this.password);
		}else{
			this.password=pro.getProperty(this.mail.getMailSender()+"SMTPpassword");
			if(StringUtils.isRealEmpty(this.password)){
				this.password = pro.getProperty("Bayer_smtp_password");
			}
		}

		if("test".equals(pro.getProperty("Bayer_smtp_status"))){
			this.to =pro.getProperty("Bayer_smtp_to");
			this.isTest=true;
		}
		if("true".equals(pro.getProperty("Bayer_smtp_needValidate")))
			isNeedValidate=true;
		else
			isNeedValidate=false;
		if(this.from==null){ 
			this.from=pro.getProperty(this.mail.getMailSender()+"SMTPID");
			if(StringUtils.isRealEmpty(this.from)){
				this.from=pro.getProperty("Bayer_smtp_from");
			}
		}
		return pro;
	}

	public void sendMail() throws NumberFormatException, Exception{
		Session session=getSession();
		// construct the message
		Message msg = new MimeMessage(session);
		if (!isRealEmpty(from) )
			msg.setFrom(new InternetAddress(from));
		else
			msg.setFrom();
		if(this.isTest)
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to, false));
		else{
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toString(this.mail.getTo()), false));
		}
		if(!this.isTest)
			if (this.mail.getCc() != null)
				msg.setRecipients(Message.RecipientType.CC,
						InternetAddress.parse(toString(this.mail.getCc()), false));
		if(!this.isTest)
			if (this.mail.getBc()!= null)
				msg.setRecipients(Message.RecipientType.BCC,
						InternetAddress.parse(toString(this.mail.getBc()), false));

		msg.setSubject(MimeUtility.encodeText(this.mail.getSubject(),"utf8","B"));
//		setBody(msg,this.mail);
		String text = this.mail.getBody();
		String content="";
		if (!isRealEmpty(this.mail.getAttachment()) ) {
			// Attach the specified file.
			// We need a multipart message to hold the attachment.
			MimeMultipart mp = new MimeMultipart();
			MimeBodyPart mbp1 = new MimeBodyPart();
			content=setBody(mbp1,mail);
			MimeBodyPart mbp2 = new MimeBodyPart();
			mbp2.attachFile(new String (this.mail.getAttachment().getBytes(),"utf-8") );
			mp.addBodyPart(mbp2);

			mp.addBodyPart(mbp1);
			msg.setContent(mp);
		} else {
			content=setBody(msg,mail);
			msg.setContent(content,"text/html;charset=utf8");
		}

		msg.setHeader("X-Mailer", mailer);
		msg.setSentDate(new Date());
	 
		Transport t = session.getTransport("smtp");
		try {
			if (isNeedValidate)
				t.connect(mailhost, user, password);
			else
				t.connect();
			msg.saveChanges();
			
			System.out.println("user:------"+user);
			logger.info("mail_address:"+msg.getAllRecipients());
			logger.info("mail_subject:"+msg.getSubject()); 
			logger.info("connected--------------------"+t.isConnected());
			
			
			t.sendMessage(msg,msg.getAllRecipients());  
		}catch(Exception e){
		   e.printStackTrace();
		}finally { 
			t.close();
		}
		mail.setMessageId(((MimeMessage)msg).getMessageID()); 
		logger.info("MessageId:"+mail.getMessageId());
		logger.info("Mail was sent successfully.");
	}

	private   boolean isRealEmpty(String str) {
		return str == null || str.length() == 0 || str.trim().equals("");
	}

	private String setBody(MimeBodyPart msg, Mail mail) throws MessagingException, IOException{
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML>\n");
		sb.append("<HEAD>\n");
		sb.append("<TITLE>\n"); 
		sb.append(mail.getSubject() + "\n");
		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		sb.append("</TITLE>\n");
		sb.append("</HEAD>\n"); 
		sb.append("<BODY style='font-family: Calibri;font-size: 16px;'>\n");
		sb.append(addHref(mail.getBody()).replaceAll("\r", "<br>"));
		sb.append("</BODY>\n");
		sb.append("</HTML>\n");

		msg.setDataHandler(new DataHandler(new ByteArrayDataSource(sb.toString(), "text/html;charset=utf8")));
		return sb.toString();
	}
	private String setBody(Message msg, Mail mail) throws MessagingException, IOException{
		String subject = msg.getSubject();
		StringBuffer sb = new StringBuffer();
		sb.append("<HTML>\n");
		sb.append("<HEAD>\n");
		sb.append("<TITLE>\n");
		sb.append(subject + "\n");
		sb.append("</TITLE>\n");
		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		sb.append("</HEAD>\n");
		sb.append("<BODY style='font-family: Calibri;font-size: 16px;'>\n"); 
		sb.append(addHref(mail.getBody()).replaceAll("\r", "<br>"));
		sb.append("</BODY>\n");
		sb.append("</HTML>\n");

		msg.setDataHandler(new DataHandler(
				new ByteArrayDataSource(sb.toString(), "text/html;charset=utf8")));
		//add wqj by 2012-05-16
		msg.setContent(sb.toString(),"text/html;charset=utf8");
		
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
	private Session getSession() throws NumberFormatException, Exception{
		Properties properties = new Properties();
		Properties proConnectParam=this.getConnectParam();
		final String transprort = proConnectParam.getProperty("Bayer_transport_protocol"); 
		properties.put("mail."+transprort+".host", proConnectParam.getProperty("Bayer_smtp_Server"));  

		final String user = proConnectParam.getProperty("Bayer_smtp_username"); 
		final String password = proConnectParam.getProperty("Bayer_smtp_password");

		if("true".equals(proConnectParam.getProperty("Bayer_smtp_needValidate"))){
			properties.setProperty("mail."+transprort+".auth", "true");
			properties.put("mail."+transprort+".debug", "true");
			properties.put("mail."+transprort+".starttls.enable","true");
			 
			java.security.Security.setProperty("ssl.SocketFactory.provider",
			"com.dne.mail.smtp.base.DNESSLSocketFactory");

			return Session.getInstance(properties,new Authenticator(){
				public PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(user, password);}
			});
		}else
			properties.put("mail.smtp.auth", "false");
		return Session.getInstance(properties);

	}
	
	
	String toString(String[] s){
		StringBuffer str=new StringBuffer();
		if(s!=null){
			for(int i=0;i<s.length;i++){
				if(!StringUtils.isRealEmpty(s[i])){
					if(i==0)
						str.append(s[i]);
					else {
						str.append(","+s[i]);
					}
				}
			}
		}
		return str.toString();
	}

	public String getMailhost() {
		return mailhost;
	}
	public void setMailhost(String mailhost) {
		this.mailhost = mailhost;
	}
	public String getMailer() {
		return mailer;
	}
	public void setMailer(String mailer) {
		this.mailer = mailer;
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
	public boolean isNeedValidate() {
		return isNeedValidate;
	}
	public void setNeedValidate(boolean isNeedValidate) {
		this.isNeedValidate = isNeedValidate;
	}
	public Log getLogger() {
		return logger;
	}
	public void setLogger(Log logger) {
		this.logger = logger;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Properties getPro() {
		return pro;
	}
	public void setPro(Properties pro) {
		this.pro = pro;
	}

}

