package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.action.phone.QueryCustomerDetailAction.OrganJson;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.metadata.DealerType;
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
public class QueryCustomerDetailAction extends Action{
	
	private AppSTransferRelation appSTransferRelation = new AppSTransferRelation();
	private AppOlinkMan appol = new AppOlinkMan();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		String organId = request.getParameter("organId");
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		UsersBean loginUsers = au.getUsersBeanByLoginname(username);
		
		OrganJson organJson = getChildOrgans(loginUsers, organId);
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, organJson
				,loginUsers.getUserid(),"采集器","IMEI:[" + scannerNo + "],机构下载",true);
		return null;
	}

	private OrganJson getChildOrgans(UsersBean loginUsers, String organId) throws NumberFormatException, Exception {
		List<Map<String,String>> organ  = appSTransferRelation.getOrganByIdAndParentId(organId, loginUsers.getMakeorganid());
		OrganJson organJson = new OrganJson();
		if(organ != null && organ.size() > 0) {
			Map<String, String> map = organ.get(0);
			MapUtil.mapToObject(map, organJson);
			Olinkman oman = appol.getMainLinkmanByCid(organJson.getOrganId());
			String organOlinkman = oman==null?"": oman.getName();
			String idCard = oman==null?"": oman.getIdcard();
			organJson.setIdCard(idCard);
			organJson.setName(organOlinkman);
			organJson.setTypeName(DealerType.parseByValue(Integer.valueOf(organJson.getTypeId())).getName());
			if(!StringUtil.isEmpty(organJson.getMobile())) {
				organJson.setMobile(Encrypt.getSecret(organJson.getMobile(), 2));
			}
		}
		return organJson;
	}
	
	public class OrganJson {
		private String organId;
		private String organName;
		private String mobile;
		private String province;
		private String city;
		private String areas;
		private String provinceName;
		private String cityName;
		private String areasName;
		private String address;
		private String name;
		private String idCard;
		private String typeId;
		private String typeName;
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
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getAreas() {
			return areas;
		}
		public void setAreas(String areas) {
			this.areas = areas;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getIdCard() {
			return idCard;
		}
		public void setIdCard(String idCard) {
			this.idCard = idCard;
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
		
	}

}

