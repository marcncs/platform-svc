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
import com.winsafe.drp.dao.AppAmountInventory;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListProductStockingAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 10; 
		super.initdata(request);

		try {
			Map<String, Object> param = new LinkedHashMap<String, Object>();

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "AmountInventory" };
			String whereSql = EntityManager.getTmpWhereSqlForSql(map, tablename, param);

			String timeCondition = DbUtil.getTimeConditionForSql(map, tmpMap, " ai.RequireDate", param);
			String blur = DbUtil.getOrBlurForSql(map, tmpMap, param, "ai.ID", "ai.WarehouseID", "ai.ShipmentSort",
					"ai.MakeID", "ai.MakeOrganID", "ai.MakeDeptID",
					"ai.isaccount");
			//权限条件
			String Condition ="";
			if(DbUtil.isDealer(users)) {
				Condition += " ai.warehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id=?) ";
				param.put("wv.user_Id", userid);
			} else { 
				Condition += DbUtil.getWhereCondition(users, "o");
			}
			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppAmountInventory aai = new AppAmountInventory();
			AppOrgan ao = new AppOrgan();
			Organ organ = new Organ();
			whereSql = whereSql.replace("isaudit", "ai.isaudit");
			List pils = aai.getAmountInventoryList(request, pagesize, whereSql, param);

			request.setAttribute("alsb", pils);
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("WarehouseID", request.getParameter("WarehouseID"));
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request.setAttribute("isaccount", map.get("isaccount"));

			DBUserLog.addUserLog(request, "[列表]");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
