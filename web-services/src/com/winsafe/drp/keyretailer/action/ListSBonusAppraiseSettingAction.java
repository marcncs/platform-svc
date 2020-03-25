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
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListSBonusAppraiseSettingAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListSBonusAppraiseSettingAction.class);
	
	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		String whereSql = "";
		if(users.getOrganType() != null && OrganType.Dealer.getValue().equals(users.getOrganType())) {
			whereSql = " where o.id = '"+users.getMakeorganid()+"' and sbt.toorganid in (select organizationid from S_TRANSFER_RELATION where opporganid = '"+users.getMakeorganid()+"') and ";
		} else {
			whereSql = " where " + SBonusService.getWhereCondition(users) +" and ";
		}
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
//		String printJobId = (String)map.get("printJobId");
//		String[] tablename = { "SBonusAppraise" };
//		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
//		String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," transOrderDate");
		String blur = DbUtil.getOrBlur(map, tmpMap, "o.organname", "toorgan.organname");
		whereSql = whereSql + blur;
		whereSql = DbUtil.getWhereSql(whereSql);
//		whereSql = whereSql + condition;
//		List<SBonusAppraise> sBonusAppraises = appSBonusAppraise.getSBonusAppraise(request, pagesize, whereSql);
		List<Map<String,String>> sBonusAppraises = appSBonusAppraise.getSBonusAppraiseSetting(request, pagesize, whereSql);
		for(Map<String,String> setting : sBonusAppraises) {
			if(StringUtil.isEmpty(setting.get("issupported"))) {
				setting.put("issupported", "1");
			}
			if(StringUtil.isEmpty(setting.get("curbonus"))) {
				setting.put("curbonus", "0");
			}
		}
		
		request.setAttribute("sBonusAppraises", sBonusAppraises);
		DBUserLog.addUserLog(request, "列表");
		if("1".equals(request.getParameter("type"))) {
			return mapping.findForward("confirm");
		}
		return mapping.findForward("list");
	}
		
}
