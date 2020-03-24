package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintCostProductDetailAction extends BaseAction {
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
			String Condition = " so.id=sod.ttid and (so.isblankout = 0 "
					+ visitorgan + " ) ";

			String[] tablename = { "TakeTicket", "TakeTicketDetail" };
			String whereSql = getWhereSql(tablename);
			String blur = getKeyWordCondition("OID", "OName", "Tel", "BillNo",
					"ProductID", "ProductName", "SpecMode", "Batch");

			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppTakeTicket aso = new AppTakeTicket();
			List list = aso.getCostProductDetail(whereSql);
			
			request.setAttribute("list", list);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印营业成本按产品汇总");
			return mapping.findForward("toprint");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
