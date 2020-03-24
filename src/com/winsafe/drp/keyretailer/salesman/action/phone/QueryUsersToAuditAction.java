package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.metadata.ApplyUserType;
import com.winsafe.drp.server.UserApplyServices;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

public class QueryUsersToAuditAction extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers appUsers = new AppUsers();
		String username = request.getParameter("Username");
		String areaId = request.getParameter("areaId");
		// 判断用户是否存在 
		UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
		String condition = UserApplyServices.getWhereConditionForAuditUser(loginUsers);
		
		List<Map<String, String>> list = appUsers.getUserApplyToAudit(condition, areaId);
		
		List<UserJson> ciList = getUserJsonList(list);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, ciList
				,loginUsers.getUserid(),"APP_RI","客户信息确认",true);
		return null;
	}
	
	private List<UserJson> getUserJsonList(
			List<Map<String, String>> list) throws Exception { 
		List<UserJson> ciList = new ArrayList<UserJson>();
		for(Map<String, String> data : list) {
			UserJson ci = new UserJson();
			MapUtil.mapToObject(data, ci);
			ci.setUserTypeName(ApplyUserType.parseByValue(Integer.valueOf(ci.getUserType())).getName()); 
			if(!StringUtil.isEmpty(ci.getMobile())) {
				ci.setMobile(Encrypt.getSecret(ci.getMobile(), 2));
			}
			ciList.add(ci);
		}
		return ciList;
	}

	public class UserJson {
		private String userId;
		private String mobile;
		private String organName;
		private String name;
		private String cityName;
		private String areasName;
		private String provinceName;
		private String userType;
		private String userTypeName;
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getOrganName() {
			return organName;
		}
		public void setOrganName(String organName) {
			this.organName = organName;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCityName() {
			return cityName;
		}
		public void setCityName(String cityName) {
			this.cityName = cityName;
		}
		public String getAreasName() {
			return areasName;
		}
		public void setAreasName(String areasName) {
			this.areasName = areasName;
		}
		public String getUserType() {
			return userType;
		}
		public void setUserType(String userType) {
			this.userType = userType;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getProvinceName() {
			return provinceName;
		}
		public void setProvinceName(String provinceName) {
			this.provinceName = provinceName;
		}
		public String getUserTypeName() {
			return userTypeName;
		}
		public void setUserTypeName(String userTypeName) {
			this.userTypeName = userTypeName;
		}
	}

}

