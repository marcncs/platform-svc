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
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		String begindate = request.getParameter("BeginDate");
		try {
	
			String Condition = " (sb.makeid=" + userid + " " +getOrVisitOrgan("sb.makeorganid")+ ") ";

			Map map = new HashMap(request.getParameterMap());
			if (begindate == null) {
				String currentdate = DateUtil.getCurrentDateString();
				map.put("BeginDate", currentdate);
				request.setAttribute("BeginDate", currentdate);
			}
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ShipmentBill" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" RequireDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");

			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppShipmentBill asb = new AppShipmentBill();
			List pils = asb.searchShipmentBill(request,pagesize, whereSql);
			

		
			request.setAttribute("alsb", pils);		
			
			DBUserLog.addUserLog(userid, 8, "列表送货清单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
