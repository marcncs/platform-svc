package com.winsafe.drp.action.machin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOtherIncomeAll;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OtherIncomeAll;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListBlueIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {
			
			String Condition = " incomesort = 4 and warehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
					+ userid + " and activeFlag = 1)";
			// String
			// Condition=" (makeid="+userid+" "+getOrVisitOrgan("makeorganid")+") ";
			Map map = new HashMap(request.getParameterMap());
			String id = (String)map.remove("ID");
			if(!StringUtil.isEmpty(id)) {
				Condition = Condition + " and id = '" + id.trim() +"'";
			}
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "OtherIncomeAll" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppOtherIncomeAll aoi = new AppOtherIncomeAll();
			AppOrgan ao = new AppOrgan();
			Organ organ = new Organ();
			List<OtherIncomeAll> pils = aoi.getOtherIncomeAll(request, pagesize, whereSql);

			/*for (OtherIncomeAll oia : pils) {
				if (StringUtil.isEmpty(oia.getOrganid()) && !StringUtil.isEmpty(oia.getWarehouseid())) {
					organ = ao.getOrganByWarehouseid(oia.getWarehouseid());
					if (organ != null) {
						oia.setOrganid(organ.getId());
					}
				}
			}*/
			
			request.setAttribute("alpi", pils);
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("WarehouseID", request.getParameter("WarehouseID"));
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request.setAttribute("isaccount", map.get("isaccount"));

			DBUserLog.addUserLog(request,"[列表]");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
