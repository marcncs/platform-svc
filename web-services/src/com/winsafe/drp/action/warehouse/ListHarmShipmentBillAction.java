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
import com.winsafe.drp.dao.AppHarmShipmentBill;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListHarmShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

	      
		super.initdata(request);
		try {
			String Condition=" (hsb.makeid='"+userid+"' " +getOrVisitOrgan("hsb.makeorganid") +") and hsb.warehouseid=wv.wid and wv.userid="+userid;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"HarmShipmentBill","WarehouseVisit"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" HarmDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + timeCondition + blur +Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppHarmShipmentBill aosb = new AppHarmShipmentBill();
			List pils = aosb.getHarmShipmentBill(request,pagesize, whereSql);
			request.setAttribute("alsb", pils);
			request.setAttribute("WarehouseID", request.getParameter("WarehouseID"));
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("IsEndcase", map.get("IsEndcase"));
			request.setAttribute("IsBlankOut", map.get("IsBlankOut"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid,7,"仓库管理>>列表报损出库单"); 
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
