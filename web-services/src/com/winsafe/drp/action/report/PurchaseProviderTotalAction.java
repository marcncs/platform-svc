package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;

public class PurchaseProviderTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pb.makeorganid");
			}
			String Condition = " pb.isratify=1  " + visitorgan
					+ "   ";

		
			String[] tablename = { "PurchaseBill" };
			String whereSql = getWhereSql(tablename);
	
			String timeCondition = getTimeCondition(
					"MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppPurchaseBill aso = new AppPurchaseBill();
			List list = aso.getPurchaseProviderTotal(request, pagesize,
					whereSql);
			double allsum = aso.getTotalPayment(whereSql);
			
			request.setAttribute("allsum", DataFormat.currencyFormat(allsum));
			request.setAttribute("list", list);



			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("PurchaseDept", request.getParameter("PurchaseDept"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("PID", request.getParameter("PID"));
			request.setAttribute("PName", request.getParameter("PName"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>采购>>列表采购订单按供应商汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
