package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.LinkedHashMap;  
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.StringUtil;

public class ListTakeBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		int pagesize = 10;
		initdata(request);
		AppTakeTicket appTT = new AppTakeTicket();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		try {
			//查询条件
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "TakeTicket" };
			String whereSql = EntityManager.getTmpWhereSqlForSql(map, tablename, param);
			String blur = DbUtil.getOrBlurForSql(map, tmpMap, param, "tt.ID", "tt.OName", "tt.Tel","tt.BillNo"); 
			String timeCondition = DbUtil.getTimeConditionForSql(map, tmpMap," MakeDate", param);
			whereSql = whereSql + blur + timeCondition;
			if(!StringUtil.isEmpty((String)map.get("withdrawType"))) {
				whereSql = whereSql + " tt.billno like ? and ";
				param.put("tt.billno", (String)map.get("withdrawType")+"%");
			}
			if(!StringUtil.isEmpty((String)map.get("isNoBill"))) {
				if("1".equals(map.get("isNoBill"))) {
					whereSql = whereSql + " tt.isNoBill = 1 and ";
				} else {
					whereSql = whereSql + " tt.isNoBill is null and ";
				} 
			} 
			
			whereSql = DbUtil.getWhereSql(whereSql);
			whereSql = whereSql.replace("where", " and ");
			if(!DbUtil.isDealer(users)) {
				whereSql += " and ("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") ";
			} 
			whereSql = whereSql.replace("isaudit", "tt.isaudit");
			List<TakeTicket> ttList = appTT.getTakeTicketByRule(request, pagesize, whereSql,users, param);
			
			request.setAttribute("also", ttList);
			String objectsortselect = Internation.getSelectTagByKeyAll(
					"ObjectSort", request, "ObjectSort", true, null);
			request.setAttribute("objectsortselect", objectsortselect);

			//DBUserLog.addUserLog(request,"列表");
			return mapping.findForward("list");
		} catch (Exception e) {
			WfLogger.error("", e); 
		}
		return new ActionForward(mapping.getInput());
	}
}
