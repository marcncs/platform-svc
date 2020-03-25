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

public class SaleTradesDetailAction extends BaseAction {
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
			String Condition = " so.id=sod.stid and (so.isendcase=1 and so.isblankout=0 "
					+ visitorgan
					+ " )  ";

			String[] tablename = { "SaleTrades", "SaleTradesDetail" };
			String whereSql = getWhereSql(tablename);
			String brur = getKeyWordCondition("CName", "CMobile", "ProductID",
					"ProductName", "SpecMode");

			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + brur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSaleTradesDetail asod = new AppSaleTradesDetail();
			List pils = asod.getSaleTradesDetail(request, pagesize, whereSql);
			double totalsum=asod.getDetailTotalSubum(whereSql);
			request.setAttribute("totalsum", totalsum);
			request.setAttribute("list", pils);

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ProductID", request.getParameter("ProductID"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			request.setAttribute("CID", request.getParameter("CID"));
			request.setAttribute("CName", request.getParameter("CName"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>零售>>列表零售换货明细");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
