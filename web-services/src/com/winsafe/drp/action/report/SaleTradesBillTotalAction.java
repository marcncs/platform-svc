package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class SaleTradesBillTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " (isendcase =1 and isblankout = 0 "
					+ visitorgan + " )  ";

			String[] tablename = { "SaleTrades" };
			String whereSql = getWhereSql(tablename);

			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppWithdraw aso = new AppWithdraw();
			List pils = aso.getWithdrawBillTotal(request, pagesize, whereSql);
			double allsum = aso.getWithdrawCustomerTotalSum(whereSql);

			request.setAttribute("allsum", allsum);
			request.setAttribute("list", pils);


			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("CName", request.getParameter("CName"));
			request.setAttribute("CID", request.getParameter("CID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ID", request.getParameter("ID"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>零售>>列表零售换货按单据汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
