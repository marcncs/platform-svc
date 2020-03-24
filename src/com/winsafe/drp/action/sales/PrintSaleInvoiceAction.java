package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleInvoice;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintSaleInvoiceAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String Condition = " (si.makeid="+userid+" "+getOrVisitOrgan("si.makeorganid")+") ";
			String[] tablename = { "SaleInvoice" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("MakeDate");
			String blur =getKeyWordCondition("CID","CNAME","InvoiceCode");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppSaleInvoice asl = new AppSaleInvoice();
			List pils = asl.getSaleInvoiceAll(whereSql);

			request.setAttribute("alsi", pils);

			DBUserLog.addUserLog(userid, 6,"零售管理>>打印零售发票");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
