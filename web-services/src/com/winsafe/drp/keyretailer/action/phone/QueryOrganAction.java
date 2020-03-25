package com.winsafe.drp.keyretailer.action.phone;

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
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

/**
 * Project:dupontdgm->Class:DownloadOrganAction.java
 * 下载出入库机构信息
 * Create Time 2012/10/30
 * @author WeiLi
 * @version 0.1
 */
public class QueryOrganAction extends Action{
	
	private AppSTransferRelation appSTransferRelation = new AppSTransferRelation();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers au = new AppUsers();
		String type = request.getParameter("type");
		String username = request.getParameter("Username");
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		Users users = au.getUsers(username);
		
		List<OrganJson> list = null;
		if ("1".equals(type)) {
			//出货客户
			list = getParentOrgans(users);
		} else if ("2".equals(type)) {
			//收货客户
			list=getChildOrgans(users);
		} else if("3".equals(type)){
			//银河会员收货客户
			list=getKeyChildOrgans(users);
		} else {
			list=getAllOrgans(users);
		}
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,users.getUserid(),"采集器","IMEI:[" + scannerNo + "],机构下载",true);
		return null;
	}
	
	private List<com.winsafe.drp.keyretailer.action.phone.QueryOrganAction.OrganJson> getKeyChildOrgans(
			Users users) throws Exception { 
		List<Map<String,String>> data  = appSTransferRelation.getOrgansByOppOrganId(users.getMakeorganid(), users.getUserid(), 1);
		List<OrganJson> organJsonList = new ArrayList<OrganJson>();
		for(Map<String,String> map : data) {
			OrganJson oj = new OrganJson();
			MapUtil.mapToObject(map, oj);
			if(!StringUtil.isEmpty(oj.getOrganMobile())) {
				oj.setOrganMobile(Encrypt.getSecret(oj.getOrganMobile(), 2));
			}
			organJsonList.add(oj);
		}
		return organJsonList;
	}

	private List<OrganJson> getAllOrgans(Users users) throws NumberFormatException, Exception {
		List<Map<String,String>> data = appSTransferRelation.getAllOrgans(users.getMakeorganid(), users.getUserid());
		List<OrganJson> organJsonList = new ArrayList<OrganJson>();
		for(Map<String,String> map : data) {
			OrganJson oj = new OrganJson();
			MapUtil.mapToObject(map, oj);
			if(!StringUtil.isEmpty(oj.getOrganMobile())) {
				oj.setOrganMobile(Encrypt.getSecret(oj.getOrganMobile(), 2));
			}
			organJsonList.add(oj);
		}
		return organJsonList;
	}

	private List<OrganJson> getChildOrgans(Users users) throws NumberFormatException, Exception {
		List<Map<String,String>> data  = appSTransferRelation.getOrgansByOppOrganId(users.getMakeorganid(), users.getUserid(), null);
		List<OrganJson> organJsonList = new ArrayList<OrganJson>();
		for(Map<String,String> map : data) {
			OrganJson oj = new OrganJson();
			MapUtil.mapToObject(map, oj);
			if(!StringUtil.isEmpty(oj.getOrganMobile())) {
				oj.setOrganMobile(Encrypt.getSecret(oj.getOrganMobile(), 2));
			}
			organJsonList.add(oj);
		}
		return organJsonList;
	}

	private List<OrganJson> getParentOrgans(Users users) throws NumberFormatException, Exception { 
		List<Map<String,String>> data = appSTransferRelation.getOrgansByOrganId(users.getMakeorganid());
		List<OrganJson> organJsonList = new ArrayList<OrganJson>();
		for(Map<String,String> map : data) {
			OrganJson oj = new OrganJson();
			MapUtil.mapToObject(map, oj);
			if(!StringUtil.isEmpty(oj.getOrganMobile())) {
				oj.setOrganMobile(Encrypt.getSecret(oj.getOrganMobile(), 2));
			}
			organJsonList.add(oj);
		}
		return organJsonList;
	}
	
	public class OrganJson {
		private String organId;
		private String organType;
		private String organName;
		private String organMobile;
		private String organProvince;
		private String organCity;
		private String organAreas;
		private String organOaddr;
		private String organOlinkman;
		private String isKeyRetailer;
		public String getOrganId() {
			return organId;
		}
		public void setOrganId(String organId) {
			this.organId = organId;
		}
		public String getOrganName() {
			return organName;
		}
		public void setOrganName(String organName) {
			this.organName = organName;
		}
		public String getOrganMobile() {
			return organMobile;
		}
		public void setOrganMobile(String organMobile) {
			this.organMobile = organMobile;
		}
		public String getOrganProvince() {
			return organProvince;
		}
		public void setOrganProvince(String organProvince) {
			this.organProvince = organProvince;
		}
		public String getOrganCity() {
			return organCity;
		}
		public void setOrganCity(String organCity) {
			this.organCity = organCity;
		}
		public String getOrganAreas() {
			return organAreas;
		}
		public void setOrganAreas(String organAreas) {
			this.organAreas = organAreas;
		}
		public String getOrganOaddr() {
			return organOaddr;
		}
		public void setOrganOaddr(String organOaddr) {
			this.organOaddr = organOaddr;
		}
		public String getOrganOlinkman() {
			return organOlinkman;
		}
		public void setOrganOlinkman(String organOlinkman) {
			this.organOlinkman = organOlinkman;
		}
		public String getOrganType() {
			return organType;
		}
		public void setOrganType(String organType) {
			this.organType = organType;
		}
		public String getIsKeyRetailer() {
			return isKeyRetailer;
		}
		public void setIsKeyRetailer(String isKeyRetailer) {
			this.isKeyRetailer = isKeyRetailer;
		}
	}

}

