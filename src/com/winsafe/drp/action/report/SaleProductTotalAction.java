package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class SaleProductTotalAction extends BaseAction {
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
			String Condition = " so.id=sod.soid and so.isendcase = 1 and so.isblankout = 0 "
					+ visitorgan + "  ";

			String[] tablename = { "SaleOrder", "SaleOrderDetail" };
			String whereSql = getWhereSql(tablename);

			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppSaleOrder aso = new AppSaleOrder();
			List pils = aso.getSaleTotal(request, pagesize, whereSql);
			List sumobj = aso.getTotalSum(whereSql);

			request.setAttribute("str", pils);
			request.setAttribute("sumobj", sumobj);

			request.setAttribute("MakeOrganID", request
					.getParameter("MakeOrganID"));
			request.setAttribute("EquipOrganID", request
					.getParameter("EquipOrganID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ProductID", request
							.getParameter("ProductID"));
			request.setAttribute("ProductName", request
					.getParameter("ProductName"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>零售>>列表零售按产品汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
