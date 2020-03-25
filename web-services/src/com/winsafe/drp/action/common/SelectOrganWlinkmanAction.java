package com.winsafe.drp.action.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWlinkMan;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectOrganWlinkmanAction extends BaseAction {
	private AppWlinkMan al = new AppWlinkMan(); 
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String Condition = " warehouseid in (select wv.warehouseId from  RuleUserWh as wv where  wv.userId="+userid+") "; 
		try {
			String[] tablename = { "Wlinkman" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate"); 
			String blur = DbUtil.getOrBlur(map, tmpMap, "name", "mobile"); 
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			
			
			List usList = al.searchWlinkman(request, whereSql);
			
			request.setAttribute("usList", usList);
			request.setAttribute("warehouseid", request.getParameter("warehouseid"));

//			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("success");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());}
}