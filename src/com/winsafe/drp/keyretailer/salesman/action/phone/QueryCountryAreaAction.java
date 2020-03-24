package com.winsafe.drp.keyretailer.salesman.action.phone;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class QueryCountryAreaAction extends Action {
	
	private AppCountryArea appCountryArea = new AppCountryArea();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		Users users = au.getUsers(username);
		
		List<CountryArea> caData = appCountryArea.getAllCountryArea();
		//下载所有的可用产品
		Map<String,List<AreaJson>> list = getAreaJson(caData);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,users.getUserid(),"APP","省市区下载",false);
		return null;
	}
	
	private Map<String,List<AreaJson>> getAreaJson(List<CountryArea> caData) {
		Map<String,List<AreaJson>> map = new HashMap<String, List<AreaJson>>();
		List<AreaJson> province = new ArrayList<AreaJson>();
		List<AreaJson> city = new ArrayList<AreaJson>();
		List<AreaJson> area = new ArrayList<AreaJson>();
		for(CountryArea ca : caData) {
			AreaJson aj = new AreaJson();
			aj.setAreaId(ca.getId().toString());
			aj.setAreaName(ca.getAreaname());
			aj.setParentId(ca.getParentid().toString());
			if(ca.getRank() == 0) {
				province.add(aj);
			} else if(ca.getRank() == 1) {
				city.add(aj);
			} else {
				area.add(aj);
			}
		}
		map.put("province", province);
		map.put("city", city);
		map.put("area", area);
		return map;
	}

	/**
	 * 用于封装json数据
	 */
	
	public class AreaJson{
		private String areaId;
		private String areaName;
		private String parentId;
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
		public String getParentId() {
			return parentId;
		}
		public void setParentId(String parentId) {
			this.parentId = parentId;
		}
		
	}
}
