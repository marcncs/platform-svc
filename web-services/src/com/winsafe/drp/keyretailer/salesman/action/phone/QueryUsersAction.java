package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppUserCustomer;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

public class QueryUsersAction extends Action{
	
	private AppUserCustomer appUserCustomer = new AppUserCustomer();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		// 采集器IMEI号
		String organId = request.getParameter("organId");
		//根据用户名称查询用户信息
		UsersBean loginUsers = au.getUsersBeanByLoginname(username);
		
		List<UserJson> list = new ArrayList<UserJson>();
		
		Properties pro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
		if("1".equals(pro.getProperty("setSSSRForBKD"))) {
			list = getUsers(loginUsers, organId);
		}
//		List<UserJson> list = getUsers(loginUsers, organId);
		
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,loginUsers.getUserid(),"BCS_RI","机构下载",false);
		return null;
	}
	
	private List<UserJson> getUsers(UsersBean loginUsers, String organId) throws Exception {
		List<Map<String,String>> data = appUserCustomer.getSSAndSR(loginUsers.getUserid(), organId);
		List<UserJson> userJsonList = new ArrayList<UserJson>();
		for(Map<String,String> map : data) {
			UserJson userJson = new UserJson();
			MapUtil.mapToObject(map, userJson);
			userJson.setTypeName(UserType.parseByValue(Integer.parseInt(userJson.getTypeId())).getName());
			userJsonList.add(userJson);
			if(!StringUtil.isEmpty(userJson.getMobile())) {
				userJson.setMobile(Encrypt.getSecret(userJson.getMobile(), 2));
			}
		}
		return userJsonList;
	}

	public class UserJson {
		private String userId;
		private String userName;
		private String realName;
		private String typeId;
		private String typeName;
		private String mobile;
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
		}
		
		public String getTypeId() {
			return typeId;
		}
		public void setTypeId(String typeId) {
			this.typeId = typeId;
		}
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
	}

}

