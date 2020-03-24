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
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSBonusAppraise;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.util.DBUserLog;

public class ListSBonusAppraiseAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListSBonusAppraiseAction.class);
	
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		String whereSql = " where " + SBonusService.getWhereCondition(users);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
//		String printJobId = (String)map.get("printJobId");
//		String[] tablename = { "SBonusAppraise" };
//		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
//		String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," transOrderDate");
//		whereSql = whereSql + timeCondition;
//		whereSql = DbUtil.getWhereSql(whereSql);
//		whereSql = whereSql + condition;
//		List<SBonusAppraise> sBonusAppraises = appSBonusAppraise.getSBonusAppraise(request, pagesize, whereSql);
		List<Map<String,String>> sBonusAppraises = appSBonusAppraise.getSBonusAppraise(request, pagesize, whereSql);
		
		
		request.setAttribute("sBonusAppraises", sBonusAppraises);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
		
}
