package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintListProductIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String Condition = " (r.activeFlag = 1 "+ getOrVisitOrgan("p.makeorganid")	+ ") and p.warehouseid=r.warehouseId and r.userId=" + userid;
			String[] tablename = { "ProductIncome"};
			String whereSql = getWhereSql2(tablename);
			String blur = getKeyWordCondition("KeysContent");

			String timeCondition = getTimeCondition("IncomeDate");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppProductIncome api = new AppProductIncome();
			List<ProductIncome> pils = api.getProductIncome(whereSql);
	
			request.setAttribute("alpi", pils);

			DBUserLog.addUserLog(userid, 7, "入库>>打印产成品入库");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
