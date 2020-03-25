package com.winsafe.drp.keyretailer.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSUserArea;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListSUserAreasAction extends BaseAction{
//	private static Logger logger = Logger.getLogger(ListSBonusSettingAction.class);
	
	private AppSUserArea appSUserArea = new AppSUserArea();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		
		String whereSql = "";
		String blur = DbUtil.getOrBlur(map, tmpMap, "u.userid","u.LOGINNAME","u.REALNAME","SUA.AREAID","sba.name_loc");
		if(!StringUtil.isEmpty(blur)) {
			whereSql = " where " + blur;
		}
		whereSql = DbUtil.getWhereSql(whereSql);
		
		List<Map<String,String>> sUserAreas = appSUserArea.getSUserArea(request, pagesize, whereSql);
		
		 AppBaseResource appBaseResource = new AppBaseResource();
		 Map<Integer, String> salesUserType = appBaseResource.getBaseResourceMap("SalesUserType");
		 for(Map<String,String> sUserArea : sUserAreas) {
			 if(!StringUtil.isEmpty(sUserArea.get("usertype"))){
				 sUserArea.put("userTypeName", StringUtil.removeNull(salesUserType.get(Integer.parseInt(sUserArea.get("usertype")))));
			 }
		 }
		
		request.setAttribute("sUserAreas", sUserAreas);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
		
}
