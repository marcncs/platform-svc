package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleTradesDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintSaleTradesDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " so.id=sod.stid and (so.isendcase=1 and so.isblankout=0 "
					+ visitorgan
					+ " )  ";
			String[] tablename = { "SaleTrades", "SaleTradesDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String brur = getKeyWordCondition("CID", "CName",
					"ProductID", "ProductName", "SpecMode");
			whereSql = whereSql + brur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSaleTradesDetail asod = new AppSaleTradesDetail();

			List list = asod.getSaleTradesDetail(whereSql);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印零售换货明细");
			request.setAttribute("list", list);
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
