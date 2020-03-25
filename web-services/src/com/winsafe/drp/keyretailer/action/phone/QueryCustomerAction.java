package com.winsafe.drp.keyretailer.action.phone;

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
import com.winsafe.drp.util.ResponseUtil;

/**
 * Project:dupontdgm->Class:DownloadOrganAction.java
 * 下载出入库机构信息
 * Create Time 2012/10/30
 * @author WeiLi
 * @version 0.1
 */
public class QueryCustomerAction extends Action{
	
	private AppSTransferRelation appSTransferRelation = new AppSTransferRelation();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppUsers au = new AppUsers();
		String username = request.getParameter("Username");
		// 采集器IMEI号
		String scannerNo = request.getParameter("IMEI_number");
		//根据用户名称查询用户信息
		UsersBean loginUsers = au.getUsersBeanByLoginname(username);
		
		List<Map<String, String>> list = getChildOrgans(loginUsers);
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list
				,loginUsers.getUserid(),"采集器","IMEI:[" + scannerNo + "],机构下载",true);
		return null;
	}

	private List<Map<String, String>> getChildOrgans(UsersBean loginUsers) throws NumberFormatException, Exception {
		List<Map<String,String>> data  = appSTransferRelation.getOrgansByParentId(loginUsers.getMakeorganid());
		for(Map<String,String> map : data) {
			map.put("organId", map.get("organid"));
			map.put("organName", map.get("organname"));
			map.put("organOaddr", map.get("oaddr"));
			map.remove("organid");
			map.remove("organname");
			map.remove("oaddr");
			/*map.put("organMobile", map.get("omobile"));
			map.put("organProvince", map.get("province"));
			map.put("organCity", map.get("city"));
			map.put("organAreas", map.get("areas"));
			map.put("organOaddr", map.get("oaddr"));
			Olinkman oman = appol.getMainLinkmanByCid(map.get("organid"));
			String organOlinkman = oman==null?"": oman.getName();
			map.put("organOlinkman", organOlinkman);
			
			
			map.remove("omobile");
			map.remove("province");
			map.remove("city");
			map.remove("areas");
			map.remove("oaddr");*/
		}
		return data;
	}

}

