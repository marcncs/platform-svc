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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListSBonusTargetAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListSBonusTargetAction.class);
	
	private AppSBonusTarget app = new AppSBonusTarget();
	private AppBaseResource appBr = new AppBaseResource();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		String condition = SBonusService.getWhereCondition(users);
		if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
			condition += " and fromo.id = "+users.getMakeorganid();
		}
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = {"SBonusTarget"};
		//where=
		String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
		//时间
		String timeCondition = DbUtil.getTimeCondition(map, tmpMap,"modifiedDate");
		whereSql = whereSql + timeCondition + condition; 
		whereSql = DbUtil.getWhereSql(whereSql); 
		List<Map<String,String>> SBonusTargets = app.getSBonusTarget(request, pagesize, whereSql);
		Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
		for(Map<String,String> sbs : SBonusTargets) {
			sbs.put("countunitname", countUnitMap.get(Integer.parseInt(sbs.get("countunit"))));
		}
		
		request.setAttribute("datas", SBonusTargets);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
}
