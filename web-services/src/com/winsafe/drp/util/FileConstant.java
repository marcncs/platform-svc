package com.winsafe.drp.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author jelli
 * 2009-5-23
 */
public class FileConstant {
	//短信自动处理用户
	public static String msguserid="333";
	//短信自动处理密码
	public static String msgpassword="cell333";
	
	//短信群发处理用户
	public static String group_msguserid="666";
	//短信群发处理密码
	public static String group_msgpassword="cell666";
	
	//邮件服务器
	public static String mail_host = "SMTP.163.com";
	//邮件用户名
	public static String mail_username = "";
	//邮件密码
	public static String mail_password = "";
	//邮件地址
	public static String mail_address = "";
	
	//物流码前缀位数
	public static int icode_length = 3;
	//仓位位数
	public static int warehousebit_length=3;
	static{
		try{
			String path = FileConstant.class.getResource("").getPath();
			path=path.substring(1, path.indexOf("classes"));
			String name = path+"windrp.properties";
			InputStream in = new BufferedInputStream(new FileInputStream(name));
			Properties p = new Properties();
			p.load(in);
			msguserid = p.getProperty("msguserid").trim();
			msgpassword = p.getProperty("msgpassword").trim();
			group_msguserid = p.getProperty("group_msguserid").trim();
			group_msgpassword = p.getProperty("group_msgpassword").trim();
			
			mail_host = p.getProperty("mail_host").trim();
			mail_username = p.getProperty("mail_username").trim();
			mail_password = p.getProperty("mail_password").trim();
			mail_address = p.getProperty("mail_address").trim();
			icode_length = Integer.valueOf(p.getProperty("icode_length").trim());
			warehousebit_length = Integer.valueOf(p.getProperty("warehousebit_length").trim());
		}catch ( Exception e ){
			e.printStackTrace();
		}
	}
	
	
}
