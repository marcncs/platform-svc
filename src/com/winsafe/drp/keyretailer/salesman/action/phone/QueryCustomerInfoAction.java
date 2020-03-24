package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

public class QueryCustomerInfoAction extends Action{
	
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
		String condition = SBonusService.getWhereConditionForCustomerInfo(loginUsers);
		
		List<Map<String, String>> list = appSalesMan.getCustomerInfo(organId, condition, areaId);
		
		Collection<CustomerInfo> ciList = getCustomerInfoList(list);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, ciList
				,loginUsers.getUserid(),"APP_RI","客户信息查看",true);
		return null;
	}
	
	private Collection<CustomerInfo> getCustomerInfoList(
			List<Map<String, String>> list) throws Exception { 
		Map<String, CustomerInfo> ciMap = new TreeMap<String, CustomerInfo>(Collections.reverseOrder());
		for(Map<String, String> data : list) {
			if(ciMap.containsKey(data.get("organid"))) {
				if(!StringUtil.isEmpty(data.get("opporganid"))) {
					CustomerInfoDetail cid = new CustomerInfoDetail();
					MapUtil.mapToObject(data, cid);
					ciMap.get(data.get("organid")).getDetails().add(cid);
				}
			} else {
				CustomerInfo ci = new CustomerInfo();
				MapUtil.mapToObject(data, ci);
				if("1".equals(data.get("iskeyretailer"))) {
					ci.setIsKeyRetailer("是");
				} else {
					ci.setIsKeyRetailer("否");
				}
				if(!StringUtil.isEmpty(ci.getMobile())) {
					ci.setMobile(Encrypt.getSecret(ci.getMobile(), 2));
				}
				ci.setTypeName(DealerType.parseByValue(Integer.parseInt(ci.getTypeId())).getName()); 
				
				List<CustomerInfoDetail> details = new ArrayList<CustomerInfoDetail>();
				if(!StringUtil.isEmpty(data.get("opporganid"))) {
					CustomerInfoDetail cid = new CustomerInfoDetail();
					MapUtil.mapToObject(data, cid);
					details.add(cid);
				}
				ci.setDetails(details);
				ciMap.put(data.get("organid"), ci);
			}
		}
		return ciMap.values();
	}

	public class CustomerInfo {
		private String organId;
		private String organName;
		private String provinceName;
		private String cityName;
		private String areasName;
		private String oaddr;
		private String mobile;
		private String name;
		private String isKeyRetailer;
		private String typeId;
		private String typeName;
		
		private List<CustomerInfoDetail> details;
		
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
		public String getProvinceName() {
			return provinceName;
		}
		public void setProvinceName(String provinceName) {
			this.provinceName = provinceName;
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
		public String getIsKeyRetailer() {
			return isKeyRetailer;
		}
		public void setIsKeyRetailer(String isKeyRetailer) {
			this.isKeyRetailer = isKeyRetailer;
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
		public List<CustomerInfoDetail> getDetails() {
			return details;
		}
		public void setDetails(List<CustomerInfoDetail> details) {
			this.details = details;
		}
	}
	
	public class CustomerInfoDetail {
		private String oppOrganId;
		private String oppOrganName;
		private String oppOaddr;
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
	}

}

