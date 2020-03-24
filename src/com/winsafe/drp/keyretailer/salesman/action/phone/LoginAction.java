package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppSUserArea;
import com.winsafe.drp.keyretailer.service.UserService;
import com.winsafe.drp.metadata.UserCategary;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;

public class LoginAction extends Action {
	private Logger logger = Logger.getLogger(LoginAction.class);
	private AppOrgan appOrgan = new AppOrgan();
	private AppSUserArea appSUserArea = new AppSUserArea();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers appUsers = new AppUsers();
		// 登录名
		String username = request.getParameter("Username");
		// 密码
		String password = request.getParameter("Password");
		// 采集器IMEI号
		String phoneNo = request.getParameter("IMEI_number");
		try {
			// 用户名密码验证 
			UsersBean usersBean = new UsersBean();
			Map<String, Object> data = new HashMap<String, Object>();
	 		if (Constants.PHONE_VALIDATE_USER) {
	 			UsersService usServices = new UsersService();
				usersBean = usServices.CheckUsersNamePassword(username, password); 
				if (usersBean == null) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL,
							Constants.CODE_LOGIN_FAIL_MSG, "");
					return null;
				}
				if (usersBean.getStatus() != 1) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, "账户不可用!", "");
					return null;
				}
				
				//获取用户分类
				Set<Integer> ucSet = appUsers.getUserCategarySet(usersBean.getUserid());
				if(!ucSet.contains(UserCategary.RI.getValue())) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, "用户类型不正确!", "");
					return null;
				}
				
				// 判断用户所属机构是否撤销
				Organ organ = appOrgan.getOrganByID_Isrepeal(usersBean.getMakeorganid());
				if (organ == null) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, "账户不可用!", "");
					return null;
				}
				if(usersBean.getUserType() == null || !UserType.getAllValueString().contains(usersBean.getUserType().toString())) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, "账户类型不正确!", "");
					return null;
				}
				//判断机构类型是否正确
				/*if(organ.getOrganType() != 2 || (organ.getOrganType() ==2 && organ.getOrganModel() == 1)) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, "账户不可用!", "");
					return null;
				}*/
				//检查机构是否已审核
				/*if(organ.getValidatestatus() != 1) {
					ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, "账户不可用!", "");
					return null;
				}*/
				
				// 设置用户在线
				appUsers.setOnline(usersBean.getUserid());
				appUsers.updateLastLogin(username);
				
				data.put("loginName", usersBean.getLoginname());
				data.put("userType", usersBean.getUserType());
				data.put("userName", usersBean.getRealname());
				data.put("mobile", usersBean.getMobile());
				List<RegionItemInfo> riiList = appSUserArea.getAreaByUserId(usersBean.getUserid());
				List<Map<String,String>> areaList = new ArrayList<Map<String,String>>();
				for(RegionItemInfo rii : riiList) {
					Map<String,String> areaMap = new HashMap<String, String>();
					areaMap.put("areaId", rii.getAreaId().toString());
					areaMap.put("areaName", rii.getName());
					areaList.add(areaMap);
				}
				data.put("areas", areaList);
				
				String fullurl = request.getRequestURL().toString();
				String pojectname = request.getContextPath();
				String realpathname = usersBean.getImgurl();
				String realpathnamexx = fullurl.substring(0, fullurl.indexOf(pojectname))+ pojectname +realpathname;
				if(StringUtil.isEmpty(realpathname)||"null".equals(realpathname)) {
					data.put("imgurl", "");
				} else {
					data.put("imgurl", realpathnamexx);
				}
				//登录名
				data.put("loginname", usersBean.getLoginname());
//				if("1".equals(usersBean.getValkey())) {
//					data.put("needUpdPwd", "1");
//				} else {
					data.put("needUpdPwd", "0");
//				}
				
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
				
			}
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG,
					data, usersBean.getUserid(), "手机", "IMEI:[" + phoneNo + "],登陆", true); 
		} catch (Exception e) {
			logger.error("",e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "登陆失败,网络异常",
					"", null, "手机", "IMEI:[" + phoneNo + "],登陆", false);
		}
		
		return null;
	}
	
	private boolean isValidMobile(String  mobile) {
		if(mobile.length() != 11 || !mobile.matches("[0-9]*")) {
			return false;
		}
		return true;
	}
}
