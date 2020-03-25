package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceiveIncome;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * 经销商签收入库列表
 * 
 * @Title: ListReceiveIncomeAction.java
 * @author: wenping
 * @CreateTime: Jan 9, 2013 1:58:41 PM
 * @version:
 */
public class ListReceiveIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {

			// String Condition = " (pi.makeid=" + userid + " "+
			// getOrVisitOrgan("pi.makeorganid") +
			// ") and pi.warehouseid=wv.wid and wv.userid=" + userid;
			String Condition = "warehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = " + userid
					+ " and activeFlag = 1)";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ReceiveIncome" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " IncomeDate");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppReceiveIncome api = new AppReceiveIncome();
			List pils = api.getReceiveIncome(request, pagesize, whereSql);

			// 赋值给页面logic标签
			request.setAttribute("alpi", pils);
			// 赋值给页面输入框（保持查询条件？）
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("WarehouseID", request.getParameter("WarehouseID"));
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));

			DBUserLog.addUserLog(userid, 7, "入库>>列表经销商签收入库");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
