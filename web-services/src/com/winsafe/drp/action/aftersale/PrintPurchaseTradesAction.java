package com.winsafe.drp.action.aftersale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseTrades;
import com.winsafe.drp.dao.PurchaseTrades;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintPurchaseTradesAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String Condition=" (dp.makeid="+userid+" "+getOrVisitOrgan("dp.makeorganid")+") ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PurchaseTrades" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");

			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + blur +timeCondition+Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppPurchaseTrades asl = new AppPurchaseTrades();
			List<PurchaseTrades> pils = asl.getPurchaseTrades(whereSql);

			request.setAttribute("also", pils);

			DBUserLog.addUserLog(userid, 2,"产品采购>>打印采购换货");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
