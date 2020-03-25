package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class SaleTradesCustomerTotalAction extends BaseAction {
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

			String[] tablename = { "SaleTrades" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String Condition = " (isendcase =1 and isblankout = 0 "
					+ visitorgan + ")";
			whereSql = whereSql + timeCondition + Condition; 

			whereSql = DbUtil.getWhereSql(whereSql); 
			AppSaleTrades appst = new AppSaleTrades();
			List list = appst.getSaleTradesCustomerTotal(request, pagesize, whereSql);
			double allsum = appst.getSaleTradesCustomerTotalSum(whereSql);

			request.setAttribute("allsum", allsum);
			request.setAttribute("list", list);

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("EquipOrganID", request.getParameter("EquipOrganID"));
			request.setAttribute("CName", request.getParameter("CName"));
			request.setAttribute("CID", request.getParameter("CID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("Tel", request.getParameter("Tel"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>零售>>列表零售换货按会员汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
