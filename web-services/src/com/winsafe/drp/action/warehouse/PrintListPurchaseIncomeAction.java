package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintListPurchaseIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String Condition = " (pi.makeid=" + userid + " "
					+ getOrVisitOrgan("pi.makeorganid")
					+ ") and pi.warehouseid=wv.wid and wv.userid=" + userid;
			String[] tablename = { "PurchaseIncome", "WarehouseVisit" };
			String whereSql =getWhereSql2(tablename);
			String blur = getKeyWordCondition("KeysContent");
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseIncome api = new AppPurchaseIncome();
			List<PurchaseIncome> pils = api.searchPurchaseIncome(whereSql);


			request.setAttribute("alpi", pils);
			
			DBUserLog.addUserLog(userid, 7,"仓库管理>>入库>>打印采购入库");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
