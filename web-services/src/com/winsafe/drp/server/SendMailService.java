package com.winsafe.drp.server;

import com.winsafe.drp.util.FileConstant;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MailSender;

/** 
 * @author: jelli 
 * @version:2009-9-19 下午05:46:07 
 * @copyright:www.winsafe.cn
 */
public class SendMailService {
	private MailSender ms;
	private String title;
	private String[] tomail;
	private String content;
	
	public SendMailService(String title, String[] tomail, String content){
		this.title = title;
		this.tomail = tomail;
		this.content = content;
		ms = new MailSender(FileConstant.mail_host, FileConstant.mail_username, FileConstant.mail_password);
	}
	
	public void sendmail(){
		try{
			ms.setSubject(title);
			ms.setSendDate(DateUtil.getCurrentDate());
			ms.setMailFrom(FileConstant.mail_address);
			ms.setMailTo(tomail, "to");
			ms.setMailContent(content);
			ms.sendMail();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
