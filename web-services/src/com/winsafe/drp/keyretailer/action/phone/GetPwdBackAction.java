package com.winsafe.drp.keyretailer.action.phone;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.SmsUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

public class GetPwdBackAction extends Action {
	private Logger logger = Logger.getLogger(GetPwdBackAction.class);
	private AppUsers appUser = new AppUsers();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// 登录名
		String mobile = request.getParameter("mobile");
		String userName = request.getParameter("Username");
		
		try {
			
			if(StringUtil.isEmpty(userName)) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR,
						"获取密码失败,用户名为空");
				return null;
			}
			
			if(StringUtil.isEmpty(mobile)) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR,
						"获取密码失败,手机号为空");
				return null;
			}
			
			if(!isValidMobile(mobile)) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR,
						"获取密码失败,手机号码格式不正确");
				return null;
			}
			
//			Users users = appUser.getUsersByMobile(mobile);;
			Users users = appUser.getUsersByloginNameAndMobile(userName, mobile);
			if(users == null) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR,
						"获取密码失败,未找到用户信息");
				return null;
			}
			
			String randomPwd = getRandomString(6);
			
			String securityPassword = Encrypt.getSecret(randomPwd, 1);
			
			boolean isSent = sendMobileVodeBySms(mobile, randomPwd, users);
			
			if(isSent) {
				users.setPassword(securityPassword);
				users.setValkey("1");
				appUser.updUsers(users);
				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS,
						"获取密码成功,密码已发送到你的手机中,请注意查收");
			} else {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR,
						"获取密码失败,短信发送失败");
			}
		} catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
				logger.error("获取密码时发生异常：", e);
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR,
						"获取密码失败,系统异常");
			} catch (Exception e1) {
				logger.error(e1);
			}
		}
		
		return null;
	}
	
	public String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 } 
	
	public boolean sendMobileVodeBySms(String mobile, String password, Users users) throws Exception{
		String smsContent = DateUtil.getCurrentDateTime() 
			+ ", 您的账号密码已被重置为：" 
			+ password 
			+ ", 用户名：" + users.getLoginname()
			+ " [工作人员不会向您索取, 请勿泄露]。";
		SmsUtil.createSmsMessage(mobile, smsContent);
		return true;
	}
	
	private boolean isValidMobile(String  mobile) {
		if(mobile.length() != 11 || !mobile.matches("[0-9]*")) {
			return false;
		}
		return true;
	}

}
