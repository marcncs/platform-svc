package com.winsafe.drp.action.phone;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMember;
import com.winsafe.drp.dao.Member;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.SmsUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

/**
 * @author ryan.xi
 */
public class CheckMessageAction extends Action {
	private static Logger logger = Logger.getLogger(CheckMessageAction.class);
	private AppMember appMember = new AppMember();
	/** ajax返回  flg 0:成功,1:失败*/
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("send sms message..");
		try {
			MobileCodeJson mobileCodeJson = null;
			String mobile = request.getParameter("Mobile");
			String imeiNumber = request.getParameter("IMEI_number");
			if(StringUtil.isEmpty(mobile)) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "获取密码失败,手机号码为空");
				return null;
			}
			
			if(!isValidMobile(mobile)) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR,
						"获取密码失败,手机号码格式不正确");
				return null;
			}
			
			Member member = appMember.getMemberByMobile(mobile);
			String randomStr = getTmpCheckNumString();
			String password = Encrypt.getSecret(randomStr, 1);
			//若无账号,先注册
			if(member == null) {
				addNewMember(mobile, password, imeiNumber);
			} else {//若有账号,更新密码
				member.setPassword(password);
				appMember.updMember(member);
			}
			
			mobileCodeJson = sendMobileVodeBySms(mobile, randomStr);
			if(mobileCodeJson != null) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, "登录密码已通过短信发送到你的手机,请注意查收",mobileCodeJson);
			} else {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "获取密码失败,请联系客服");
			}
		} catch (Exception e) {
			logger.error("获取密码时发生异常", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "获取密码失败,系统异常");
		} 
		return null;
	}
	
	private void addNewMember(String mobile, String password, String imeiNumber) throws Exception {
		Member us = new Member();
		us.setLoginname(mobile);
		us.setPassword(password);
		us.setMobile(mobile);
		
		us.setCreatedate(DateUtil.getCurrentDate());
		us.setLastlogin(DateUtil.getCurrentDate());
		us.setLogintimes(0);
		us.setStatus(1);
		us.setIsonline(0);
		us.setImeiNumber(imeiNumber);
		us.setUserType(6);
		appMember.InsertMember(us);
	}

	/**
	 * 发送手机验证码
	 * @param mobile 
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public MobileCodeJson sendMobileVodeBySms(String mobile, String password) throws Exception{
		MobileCodeJson mobileCodeJson = null;
//		String tmpCheckNum = getTmpCheckNumString();
		String smsContent = DateUtil.getCurrentDateTime() 
			+ ", 您正在使用拜验证会员信息。登录密码是：" 
			+ password 
			+ ", 请及时填写"
			+ " [工作人员不会向您索取, 请勿泄露]。";
		SmsUtil.createSmsMessage(mobile, smsContent);
		mobileCodeJson = new MobileCodeJson();
		mobileCodeJson.setMobile(mobile);
		mobileCodeJson.setCode(password);
		return mobileCodeJson;
	}
   /**
    * 生成验证码
    * @return
    */
	public String getTmpCheckNumString(){
		Random tmpRandom = new Random();
		StringBuffer tmpCheckNum = new StringBuffer();
		for (int i=0;i<4;i++){
            String rand=String.valueOf(tmpRandom.nextInt(10));
            tmpCheckNum.append(rand);
        }
		return tmpCheckNum.toString();
	}
	/*
	public String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 } */
	
	public class MobileCodeJson {
		private String mobile;
		private String code;
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
	}
	
	private boolean isValidMobile(String  mobile) {
		if(mobile.length() != 11 || !mobile.matches("[0-9]*")) {
			return false;
		}
		return true;
	}

}
