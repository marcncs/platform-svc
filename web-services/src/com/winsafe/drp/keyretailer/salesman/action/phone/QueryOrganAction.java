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
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

public class QueryOrganAction extends Action{
	
	private AppSTransferRelation appSTransferRelation = new AppSTransferRelation();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		String organType = request.getParameter("organType");
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		UsersBean loginUsers = au.getUsersBeanByLoginname(username);
		String whereSql = " where (o.VALIDATE_STATUS="+ValidateStatus.PASSED.getValue()+"or o.organModel ="+DealerType.PD.getValue()+") and "+ SBonusService.getWhereCondition(loginUsers);
		if(!StringUtil.isEmpty(organType)) {
			whereSql += " and o.organModel in ("+organType+") ";
		}
		List<OrganJson> list = getAllOrgans(loginUsers, whereSql);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,loginUsers.getUserid(),"BCS_RI","机构下载",false);
		return null;
	}
	
	private List<OrganJson> getAllOrgans(UsersBean loginUsers,
			String whereSql) throws Exception {
		List<Map<String,String>> data = appSTransferRelation.getAllOrgans(whereSql);
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
		
	}

}

