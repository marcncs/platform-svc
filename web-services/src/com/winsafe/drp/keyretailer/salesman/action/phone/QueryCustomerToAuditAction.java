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
import com.winsafe.drp.keyretailer.salesman.dao.AppSalesMan;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

public class QueryCustomerToAuditAction extends Action{
	
	private AppSalesMan appSalesMan = new AppSalesMan();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers appUsers = new AppUsers();
		String username = request.getParameter("Username");
		String organId = request.getParameter("organId");
		String areaId = request.getParameter("areaId");
		// 判断用户是否存在 
		UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
		String condition = SBonusService.getWhereConditionForAuditCustomer(loginUsers);
		
		List<Map<String, String>> list = appSalesMan.getCustomerToAudit(organId,condition, areaId);
		
		List<OrganJson> ciList = getOrganJsonList(list);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, ciList
				,loginUsers.getUserid(),"APP_RI","客户信息确认",true);
		return null;
	}
	
	private List<OrganJson> getOrganJsonList(
			List<Map<String, String>> list) throws Exception { 
		List<OrganJson> ciList = new ArrayList<OrganJson>();
		for(Map<String, String> data : list) {
			OrganJson ci = new OrganJson();
			MapUtil.mapToObject(data, ci);
			if(!StringUtil.isEmpty(ci.getMobile())) {
				ci.setMobile(Encrypt.getSecret(ci.getMobile(), 2));
			}
			ci.setTypeName(DealerType.parseByValue(Integer.valueOf(ci.getTypeId())).getName()); 
			ciList.add(ci);
		}
		return ciList;
	}

	public class OrganJson {
		private String organId;
		private String organName;
		private String provinceId;
		private String provinceName;
		private String cityId;
		private String cityName;
		private String areasId;
		private String areasName;
		private String oaddr;
		private String mobile;
		private String name;
		private String typeId;
		private String typeName;
		private String oppOrganId;
		private String oppOrganName;
		private String oppOaddr;
		private String userId;
		private String userName;
		private String realName;
		public String getOrganName() {
			return organName;
		}
		public void setOrganName(String organName) {
			this.organName = organName;
		}
		public String getOrganId() {
			return organId;
		}
		public void setOrganId(String organId) {
			this.organId = organId;
		}
		public String getProvinceId() {
			return provinceId;
		}
		public void setProvinceId(String provinceId) {
			this.provinceId = provinceId;
		}
		public String getProvinceName() {
			return provinceName;
		}
		public void setProvinceName(String provinceName) {
			this.provinceName = provinceName;
		}
		public String getCityId() {
			return cityId;
		}
		public void setCityId(String cityId) {
			this.cityId = cityId;
		}
		public String getCityName() {
			return cityName;
		}
		public void setCityName(String cityName) {
			this.cityName = cityName;
		}
		public String getAreasId() {
			return areasId;
		}
		public void setAreasId(String areasId) {
			this.areasId = areasId;
		}
		public String getAreasName() {
			return areasName;
		}
		public void setAreasName(String areasName) {
			this.areasName = areasName;
		}
		public String getOaddr() {
			return oaddr;
		}
		public void setOaddr(String oaddr) {
			this.oaddr = oaddr;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
		public String getOppOrganId() {
			return oppOrganId;
		}
		public void setOppOrganId(String oppOrganId) {
			this.oppOrganId = oppOrganId;
		}
		public String getOppOrganName() {
			return oppOrganName;
		}
		public void setOppOrganName(String oppOrganName) {
			this.oppOrganName = oppOrganName;
		}
		public String getOppOaddr() {
			return oppOaddr;
		}
		public void setOppOaddr(String oppOaddr) {
			this.oppOaddr = oppOaddr;
		}
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
		
	}

}

