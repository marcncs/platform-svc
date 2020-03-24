package com.winsafe.drp.util;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.metadata.SmsSendStatus;
import com.winsafe.drp.metadata.SmsType;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.sms.dao.AppSms;
import com.winsafe.sms.pojo.Sms;


public class SmsUtil {
	
	private static Logger logger = Logger.getLogger(SmsUtil.class);
	private static String urlprefix; 
	private static String organNo;
	private static String password;
	private static ResourceBundle smsConfig;
	
	
	static {
		logger.debug("init sms config...");
		smsConfig = PropertyResourceBundle.getBundle("smsConfig");
		//取得连接IP、账号、密码
		urlprefix = smsConfig.getString("sms_urlprefix");
		organNo = smsConfig.getString("sms_organNo");
		password = smsConfig.getString("sms_password");
		//解码 
//		password = Encrypt.getSecret(password, 2);
//		organNo = Encrypt.getSecret(organNo, 2);
		logger.debug("get sms organNo:"+organNo);
		logger.debug("get sms password:"+password);
	}
	/**
	 * 依托短信平台发送短信
	 * @param mobiles
	 * @param content
	 * @return INT
	 * @author WEILI
	 * @throws Exception 
	 * @date 2013/04/25
	 */
	public static int sendSmsBySmsTerrace(String mobiles, String smsContent){
		try {
			logger.debug("start sendSmsBySmsTerrace...");
			//短信内容进行编码
			String content = URLEncoder.encode(URLEncoder.encode(smsContent, "UTF-8"), "UTF-8");
			//校验字符串
			String strMd5 = organNo + DigestUtils.md5Hex(password) + mobiles + smsContent;
			strMd5 = DigestUtils.md5Hex(strMd5.getBytes("UTF-8"));
			//设置请求参数
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put("organNo", organNo);
			params.put("password", DigestUtils.md5Hex(password));
			params.put("mobiles", mobiles);
			params.put("content", content);
			params.put("md5Str", strMd5);
			String returnString = HttpUtils.URLGetForSMS(urlprefix, params);// 执行查询,得到返回值
			return Integer.valueOf(returnString);
		} catch (Exception e) {
			logger.error("failed to send sms message", e);
			return -1;
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(SmsUtil.sendSmsBySmsTerrace("13671548010", "尊敬的客户,您的发货单号为805438696已由上海北芳储运集团有限公司发出。预计2019-06-08前到达大通路609号。验证码：OC1906050172。点击链接https://rtci.bayer.cn/RTCI/cm/de/OC1906050172/805438696查看货物信息，请收货后登录RTCI确认收货，感谢您对拜耳作物的支持。 "));
//		System.out.println(DigestUtils.md5Hex("123"));
		System.out.println(SmsUtil.testSendSmsBySmsTerrace("13671548010", "尊敬的客户,您的发货单号为805438696已由上海北芳储运集团有限公司发出。预计2019-06-08前到达大通路609号。验证码：OC1906050172。点击链接https://rtci.bayer.cn/RTCI/cm/de/OC1906050172/805438696查看货物信息，请收货后登录RTCI确认收货，感谢您对拜耳作物的支持。 ", "1000008","e10adc3949ba59abbe56e057f20f883e"));
	}
	
	public static int testSendSmsBySmsTerrace(String mobiles, String smsContent, String organNo, String password){
		try {
			logger.debug("start sendSmsBySmsTerrace...");
			//短信内容进行编码
			String content = URLEncoder.encode(URLEncoder.encode(smsContent, "UTF-8"), "UTF-8");
			//校验字符串
			String strMd5 = organNo + password + mobiles + smsContent;
			strMd5 = DigestUtils.md5Hex(strMd5.getBytes("UTF-8"));
			//设置请求参数
			Map<String, String> params = new LinkedHashMap<String, String>();
			params.put("organNo", organNo);
			params.put("password", password);
			params.put("mobiles", mobiles);
			params.put("content", content);
			params.put("md5Str", strMd5);
			String returnString = HttpUtils.URLGetForSMS(urlprefix, params);// 执行查询,得到返回值
			return Integer.valueOf(returnString);
		} catch (Exception e) {
			logger.error("failed to send sms message", e);
			return -1;
		}
	}
	
	public static ResourceBundle getSmsConfig() {
		return smsConfig;
	}
	
	/**
	 * 把模板转换为最终的信息
	 * Create Time Dec 30, 2011 2:45:56 PM 
	 * @param template 模板
	 * @param paramsMap 模板所需参数
	 * @return 最终的信息
	 * @author huangxy
	 */
	public static String converTemplate(String template,Map<String, String> paramsMap){
		Iterator<Map.Entry<String,String>> iterator=paramsMap.entrySet().iterator();
		Map.Entry<String,String> entry;
		String value;
		String key;
		while(iterator.hasNext()){
			entry=iterator.next();
			key = entry.getKey();
			value = entry.getValue();
			if(!StringUtil.isEmpty(value)){
				template = template.replaceAll(key, value);
			}
		}
		template = template.replaceAll("\\{", "").replaceAll("\\}", "");
		return template;
	}
	
	public static Long createSmsMessage(String mobile, String content) throws Exception {
		AppSms appSms = new AppSms();
		logger.debug("create sms message for ic");
		Sms sms = new Sms(); 
		sms.setType(SmsType.INDENTIFY_CODE.getDbValue());
		sms.setCreateDate(DateUtil.getCurrentDate());
		sms.setMobileNo(mobile);
		sms.setSendStatus(SmsSendStatus.NOT_SEND.getDbValue());
		sms.setTryCount(Integer.parseInt(SmsUtil.getSmsConfig().getString("tryCount")));
		sms.setContent(content);
		appSms.addSms(sms);
		return sms.getId();
	}
	
	public static Long createSmsMessage(String mobile, String content, SmsType smsType) throws Exception {
		AppSms appSms = new AppSms();
		logger.debug("create sms message for ic");
		Sms sms = new Sms(); 
		sms.setType(smsType.getDbValue());
		sms.setCreateDate(DateUtil.getCurrentDate());
		sms.setMobileNo(mobile);
		sms.setSendStatus(SmsSendStatus.NOT_SEND.getDbValue());
		sms.setTryCount(Integer.parseInt(SmsUtil.getSmsConfig().getString("tryCount")));
		sms.setContent(content);
		appSms.addSms(sms);
		return sms.getId(); 
	}
	
	/**
    * 生成验证码
    * @return
    */
	public static String getTmpCheckNumString(int length){
		Random tmpRandom = new Random();
		StringBuffer tmpCheckNum = new StringBuffer();
		for (int i=0;i<length;i++){
            String rand=String.valueOf(tmpRandom.nextInt(10));
            tmpCheckNum.append(rand);
        }
		return tmpCheckNum.toString();
	}
	
	/**
	 * 发送手机验证码
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static String sendUpdPasswordSms(String mobile) throws Exception{
		String tmpCheckNum = getTmpCheckNumString(4);
		String smsContent = DateUtil.getCurrentDateTime() 
			+ ", 您正在尝试修改RTCI系统账号密码。验证码是：" 
			+ tmpCheckNum 
			+ ", 请及时填写, "
			+ "如非本人操作,请忽略该短信";
		SmsUtil.createSmsMessage(mobile, smsContent);
		return tmpCheckNum;
	}
}
