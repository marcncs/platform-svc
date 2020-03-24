package com.winsafe.drp.action.assistant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWlmIdcodeLog;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListWlmIdcodeLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 20;
		super.initdata(request);
		AppWlmIdcodeLog asb = new AppWlmIdcodeLog();

		String Condition = " ";
		// Condition +=
		// " and warehouseid in (select wv.warehouseId from  RuleUserWh as wv where  wv.userId="+userid+")  ";
		// 权限条件
		Condition += "  makeorganid in (select uv.visitorgan from  UserVisit as uv where  uv.userid="
				+ userid + ")  ";
		// 判断MakeDate是否有值
		Map map = new HashMap(request.getParameterMap());
		String timeBeginDate = "";
		if (map.containsKey("BeginDate")) {
			timeBeginDate = (String) map.get("BeginDate");
			if (timeBeginDate == null || "".equals(timeBeginDate)) {
				timeBeginDate = DateUtil.getCurrentDateString();
				map.put("BeginDate", timeBeginDate);
				request.setAttribute("BeginDate", timeBeginDate);
			}
		} else {
			timeBeginDate = DateUtil.getCurrentDateString();
			map.put("BeginDate", timeBeginDate);
			request.setAttribute("BeginDate", timeBeginDate);
		}
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "WlmIdcodeLog" };
		String whereSql = EntityManager.getTmpWhereSql(map, tablename);

		String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MakeDate");
		String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "WlmIdcode", "WarehouseID", "ProductID",
				"ProductName", "SpecMode", "MakeID", "MakeDate");

		whereSql = whereSql + timeCondition + blur + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);

		List pils = asb.getWlmIdcodeLog(request, pagesize, whereSql);

		request.setAttribute("alsb", pils);

		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}

}
