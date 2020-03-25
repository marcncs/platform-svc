package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintListShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
	
			String Condition = " (sb.makeid=" + userid + " " +getOrVisitOrgan("sb.makeorganid")+ ") ";


			String[] tablename = { "ShipmentBill" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition =getTimeCondition("RequireDate");
			String blur = getKeyWordCondition("KeysContent");

			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppShipmentBill asb = new AppShipmentBill();
			List pils = asb.searchShipmentBill(whereSql);
		
			request.setAttribute("alsb", pils);		
			DBUserLog.addUserLog(userid, 8, "打印列表送货清单");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
