package com.winsafe.drp.action.scanner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppScanner;
import com.winsafe.drp.dao.AppScannerDownload;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Scanner;
import com.winsafe.drp.dao.ScannerDownload;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.service.UserService;
import com.winsafe.drp.metadata.UserCategary;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;

public class LoginAction extends Action {
	private Logger logger = Logger.getLogger(LoginAction.class);
	private AppScannerDownload appDownload = new AppScannerDownload();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppUsers appUsers = new AppUsers();
		AppScanner appScanner = new AppScanner();
		
		
		// 登录名
		String username = request.getParameter("Username");
		// 密码
		String password = request.getParameter("Password");
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		// 得到加密后的密码
		String securityPassword = Encrypt.getSecret(password, 1);
		// 用户名密码验证
		UsersBean usersBean = new UsersBean();
		if(Constants.SCANNNER_VALIDATE_USER){
			// 得到用户名和密码符合的用户
			usersBean = appUsers.CheckUsersNamePassword(username,	securityPassword);
			if(usersBean == null ){
				ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, Constants.CODE_LOGIN_FAIL_MSG, "");
				return null;
			}
			if(usersBean.getStatus() != 1){
				ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, "账户不可用!", "");
				return null;
			}
			//获取用户分类
			Set<Integer> ucSet = appUsers.getUserCategarySet(usersBean.getUserid());
			if(!ucSet.contains(UserCategary.RTCI.getValue())) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, "用户类型不正确!", "");
				return null;
			}
			// 设置用户在线
			appUsers.setOnline(usersBean.getUserid());
			appUsers.updateLastLogin(username);
		}
		//IMEI验证
		if(Constants.SCANNNER_VALIDATE_IMEI){
			//判断采集器是否注册
			Scanner scanner = appScanner.findScannerByIMEI(scannerNo);
			if(scanner == null){
				ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, Constants.CODE_SCANNER_FAIL_MSG, "");
				return null;
			}
		}
		
		Map<String, String> data = new HashMap<String, String>();
		
		//非CWID用户检查密码有效期
		if(usersBean.getIsCwid() == null || usersBean.getIsCwid() != 1) { 
			//判断密码有效期
			UserService us = new UserService();
			int day = us.differentDaysByMillisecond(Dateutil.getCurrentDate(), usersBean.getVad());
			//七天之内即将过期,提示更新
			if(day > 0 && day <=6) {
				data.put("isPwdExpired", "1");
			} else if(day <= 0) {
				data.put("isPwdExpired", "2");
			} else {
				data.put("isPwdExpired", "0");
			}
		} else {
			data.put("isPwdExpired", "0");
		}
		//判断采集器主数据是否有更新
//		String returnDate = "";
		Users users = appUsers.getUsers(username);
		if(!checkBaseData(users, scannerNo)){
//			returnDate = Constants.CODE_LOGIN_UPDATE;
			data.put(Constants.CODE_LOGIN_UPDATE, Constants.CODE_LOGIN_UPDATE);
		} else {
			data.put(Constants.CODE_LOGIN_UPDATE, "");
		}
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG,data
				,usersBean.getUserid(), "采集器", "IMEI:[" + scannerNo + "],登陆",true);
		return null;
	}
	
	private Boolean checkBaseData(Users users,String scannerNo) throws Exception{
		// 新md5
		String baseDataMd5 = appDownload.getBaseDataMd5(users, scannerNo);
		// 旧md5
		String oldBaseDateMd5 = "";
		ScannerDownload scannerDownload = appDownload.getByIMEI(scannerNo);
		if(scannerDownload == null){
			return false;
		}else { 
			oldBaseDateMd5 = scannerDownload.getDownloadMd5();
			return baseDataMd5.equals(oldBaseDateMd5);
		}
	}
	
	
	
	
	
	
}
