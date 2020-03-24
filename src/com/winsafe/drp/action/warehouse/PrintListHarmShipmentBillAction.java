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

public class PrintListHarmShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	      
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
			List pils = aosb.getHarmShipmentBill(whereSql);
			request.setAttribute("alsb", pils);

			DBUserLog.addUserLog(userid,7,"仓库管理>>打印报损出库单列表"); 
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
