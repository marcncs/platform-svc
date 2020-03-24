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

public class QueryUserOrganAction extends Action{
	
	private AppSTransferRelation appSTransferRelation = new AppSTransferRelation();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		String userType = request.getParameter("userType");
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		UsersBean loginUsers = au.getUsersBeanByLoginname(username);
		String whereSql = " where o.VALIDATE_STATUS="+ValidateStatus.PASSED.getValue()+" and "+ SBonusService.getWhereConditionForAuditUser(loginUsers);
		if(!StringUtil.isEmpty(userType)) {
			if("3".equals(userType)) {
				whereSql += " and o.organModel in ("+DealerType.BKD.getValue()+") ";
			} else if("4".equals(userType)) {
				whereSql += " and o.organModel not in ("+DealerType.BKD.getValue()+","+DealerType.PD.getValue()+") ";
			}
			
		}
		List<OrganJson> list = getAllOrgans(loginUsers, whereSql);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,loginUsers.getUserid(),"BCS_RI","机构下载",false);
		return null;
	}
	
	private List<OrganJson> getAllOrgans(UsersBean loginUsers,
			String whereSql) throws Exception {
		List<Map<String,String>> data = appSTransferRelation.getAllUserOrgans(whereSql);
		List<OrganJson> organJsonList = new ArrayList<OrganJson>();
		for(Map<String,String> map : data) {
			OrganJson oj = new OrganJson();
			MapUtil.mapToObject(map, oj);
			if("2".equals(oj.getOrganType())) {
				oj.setUserType("3");
			} else {
				oj.setUserType("4");
			}
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
		private String organOaddr;
		private String userType;
		public String getOrganId() {
			return organId;
		}
		public void setOrganId(String organId) {
			this.organId = organId;
		}
		public String getOrganType() {
			return organType;
		}
		public void setOrganType(String organType) {
			this.organType = organType;
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
		public String getOrganOaddr() {
			return organOaddr;
		}
		public void setOrganOaddr(String organOaddr) {
			this.organOaddr = organOaddr;
		}
		public String getUserType() {
			return userType;
		}
		public void setUserType(String userType) {
			this.userType = userType;
		}
		
		
	}

}

