package com.winsafe.mail.smtp.base;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.winsafe.drp.util.JProperties;

public class SMTPMailFactory { 
	private static Logger logger = Logger.getLogger(SMTPMailFactory.class);
	private static Properties mailPro ; 
	public static Properties getSMTPMailFactory() {
		try {
			mailPro = JProperties.loadProperties("mail.conf",
					JProperties.BY_CLASSLOADER);
		} catch (Exception e) {
			logger.error("failed to load mail.conf file", e);
		}
		return mailPro;
	}
}
