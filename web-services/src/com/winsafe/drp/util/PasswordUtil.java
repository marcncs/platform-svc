package com.winsafe.drp.util;

import java.util.Set;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;


/**  
 * 检查密码强度,密码必须符合以下规则 
 * 至少8位
 * 大写英文字母，小写英文字母，数字和特殊字符，4种类型中至少包含3种
 * 不能与用户名相同
 * 不能与最后10次的密码相同
 */

public class PasswordUtil {

	public static String checkPwdForUpdate(String password, String userName, int userId) throws Exception {
		String msg = "";
		msg = isStrongEnough(password, userName, userId);
		if(!StringUtil.isEmpty(msg)) {
			return msg;
		}
		String syspassword = Encrypt.getSecret(password, 1);
		msg = updPwdHistory(syspassword, userId);
		return msg;
	}
	
	public static String checkPwdForAdd(String password, String userName, int userId) throws Exception {
		String msg = "";
		msg = isStrongEnough(password, userName, userId);
		if(!StringUtil.isEmpty(msg)) {
			return msg;
		}
		String syspassword = Encrypt.getSecret(password, 1);
		addPwdHistory(syspassword, userId);
		return msg;
	}
	
	public static void addPwdHistory(String password, int userId) throws Exception {
		AppUsers appUsers = new AppUsers();
		appUsers.addPwdHistory(userId,password);
	}

	public static String isStrongEnough(String password, String userName, int userId) throws Exception {
		if(userName.equals(password)) {
			return "密码不能与用户名相同";
		}
		if(password.length()<8) { 
			return "密码至少8位";
		}
		int lowerCase = 0, upperCase=0,number = 0,specialChar = 0;
		char[] pwdArray = password.toCharArray();
		for(char pwd : pwdArray) {
			int iN = Integer.valueOf(pwd);
			if (iN >= 48 && iN <= 57)//数字
				number = 1;
	        if (iN >= 65 && iN <= 90) //大写字母
	        	upperCase = 1;
	        if ((iN >= 97 && iN <= 122)) //小写字母
	        	lowerCase  = 1;
	        else
	            specialChar = 1; //特殊字符
		}
		if((lowerCase+upperCase+number+specialChar)<3) {
			return "密码必须至少包含大写英文字母,小写英文字母,数字和特殊字符4种类型中的3种";
		}
		return "";
	}
	
	public static String updPwdHistory(String password, int userId) throws Exception {
		AppUsers appUsers = new AppUsers();
		Set<String> pwdSet = appUsers.getPwdHsitorySet(userId);
		if(pwdSet.contains(password)) {
			return "不能与最后10次的密码相同";
		}
		//更新历史密码
		if(pwdSet.size()>=10) {
			appUsers.removeOldestPwdHistory(userId);
		}
		appUsers.addPwdHistory(userId,password);
		return "";
	}
	
};
