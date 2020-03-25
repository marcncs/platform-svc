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

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.ResponseUtil;

public class QueryAreasAction extends Action {
	
	private AppCountryArea appCountryArea = new AppCountryArea();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		Users users = au.getUsers(username);
		
		List<Map<String,String>> caData = appCountryArea.getUserAreas(users.getUserid());

		List<AreaJson> list = getAreaJson(caData);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,users.getUserid(),"APP","省市区下载",false);
		return null;
	}
	
	private List<AreaJson> getAreaJson(List<Map<String,String>> caData) throws Exception {
		List<AreaJson> ajList = new ArrayList<AreaJson>();
		for(Map<String,String> ca : caData) {
			AreaJson aj = new AreaJson();
			MapUtil.mapToObject(ca, aj); 
			ajList.add(aj);
		}
		return ajList;
	}

	/**
	 * 用于封装json数据
	 */
	
	public class AreaJson{
		private String areaId;
		private String areaName;
		public String getAreaId() {
			return areaId;
		}
		public void setAreaId(String areaId) {
			this.areaId = areaId;
		}
		public String getAreaName() {
			return areaName;
		}
		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}
	}
}
