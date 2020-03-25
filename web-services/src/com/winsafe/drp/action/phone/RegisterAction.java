package com.winsafe.drp.action.phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.users.AddUsersAction;
import com.winsafe.drp.dao.AppMember;
import com.winsafe.drp.dao.Member;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

public class RegisterAction extends Action {
	private static Logger logger = Logger.getLogger(AddUsersAction.class);
	
	private AppMember appMember = new AppMember();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean hasError = doValidate(request, response);
			if(hasError) {
				return null;
			}
			String loginName = request.getParameter("LoginName");
			String password = Encrypt.getSecret(
					request.getParameter("Password"), 1);
			String userType = request.getParameter("UserType");
			String mobile = request.getParameter("Mobile");
			String imeiNumber = request.getParameter("IMEI_number");
			
			Member existsMember = null;
			existsMember = appMember.getMember(loginName);
			if(existsMember != null) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR,
						"该手机号已注册过");
				return null;
			}
			
			Member us = new Member();
			us.setLoginname(loginName);
			us.setPassword(password);
			us.setMobile(mobile);
			
			us.setCreatedate(DateUtil.getCurrentDate());
			us.setLastlogin(DateUtil.getCurrentDate());
			us.setLogintimes(0);
			us.setStatus(1);
			us.setIsonline(0);
			us.setImeiNumber(imeiNumber);
			
			//添加用户性质
			if(userType!=null && userType.length() > 0){
				us.setUserType(Integer.valueOf(userType));
			}
			
			appMember.InsertMember(us);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, "注册成功");
		} catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
				logger.error("注册用户时发生异常：", e);
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR,
						"注册失败,系统异常", "");
			} catch (Exception e1) {
				logger.error(e1);
			}
		} 
		return null;
	}
	
	
	private boolean doValidate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String loginName = request.getParameter("LoginName");
		String password = request.getParameter("Password");
		String userType = request.getParameter("UserType");
		String mobile = request.getParameter("Mobile");
		if(StringUtil.isEmpty(loginName)) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS,
					"注册失败,用户名为空", "");
			return true;
		}
		if(StringUtil.isEmpty(password)) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS,
					"注册失败,密码为空", "");
			return true;
		}
		if(StringUtil.isEmpty(userType)) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS,
					"注册失败,用户类型为空", "");
			return true;
		}
		if(StringUtil.isEmpty(mobile)) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS,
					"注册失败,手机号码为空", "");
			return true;
		}
		return false;
	}
	
}
