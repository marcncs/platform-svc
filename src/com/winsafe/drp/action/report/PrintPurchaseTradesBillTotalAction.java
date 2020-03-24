package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseTrades;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintPurchaseTradesBillTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pt.makeorganid");
			}
			String Condition = " pt.id=ptd.ptid and pt.isreceive=1 and pt.isblankout=0 "
					+ visitorgan + "    ";

			String[] tablename = { "PurchaseTrades", "PurchaseTradesDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String blur = getKeyWordCondition("pt.id");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseTrades asod = new AppPurchaseTrades();

			List list = asod.getPurchaseTradesBill(whereSql);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印采购换货按单据汇总");
			request.setAttribute("list", list);
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
