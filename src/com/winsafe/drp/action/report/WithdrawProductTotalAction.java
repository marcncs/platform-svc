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

public class WithdrawProductTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " so.id=sod.wid and (so.isaudit = 1 and so.isblankout = 0 "
					+ visitorgan + " )  ";

			String[] tablename = { "Withdraw", "WithdrawDetail" };
			String whereSql = getWhereSql(tablename);

			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppWithdraw aso = new AppWithdraw();
			List pils = aso.getWithdrawProductTotal(request, pagesize, whereSql);
			List sumobj = aso.getWithdrawProductTotalSum(whereSql);

			request.setAttribute("list", pils);
			request.setAttribute("sumobj", sumobj);

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ProductID", request.getParameter("ProductID"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			DBUserLog.addUserLog(userid, 10, "报表分析>>零售>>列表零售退货按产品汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
