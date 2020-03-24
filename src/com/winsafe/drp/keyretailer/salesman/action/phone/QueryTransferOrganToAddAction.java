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
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;

public class QueryTransferOrganToAddAction extends Action{
	
	private AppSTransferRelation appSTransferRelation = new AppSTransferRelation();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		// 机构编号
		String organId = request.getParameter("organId");
		//根据用户名称查询用户信息
		UsersBean loginUsers = au.getUsersBeanByLoginname(username);
		
		List<Map<String,String>> data =  appSTransferRelation.getTransferOrgan(organId, loginUsers.getUserid());
		
		List<OrganJson> list = getOrganJsonList(data);
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,loginUsers.getUserid(),"BCS_RI","进货机构下载",false);
		return null;
	}

	private List<OrganJson> getOrganJsonList(List<Map<String, String>> data) throws Exception {
		List<OrganJson> organJsonList = new ArrayList<OrganJson>();
		for(Map<String,String> map : data) {
			OrganJson oj = new OrganJson();
			MapUtil.mapToObject(map, oj);
			if(!StringUtil.isEmpty(map.get("id"))) {
				oj.setIsSelected("1");
			} else {
				oj.setIsSelected("0");
			}
			organJsonList.add(oj);
		}
		return organJsonList;
	}
	
	public class OrganJson {
		private String oppOrganId;
		private String oppOrganName;
		private String oppOaddr;
		private String isSelected;
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
		public String getIsSelected() {
			return isSelected;
		}
		public void setIsSelected(String isSelected) {
			this.isSelected = isSelected;
		}
		
	}

}

