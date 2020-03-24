package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PurchaseWithdrawProviderTotalAction extends BaseAction {
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
			String Condition = " ( isendcase=1 and isblankout=0 " + visitorgan
					+ " )   ";

			String[] tablename = { "PurchaseWithdraw" };
			String whereSql = getWhereSql(tablename);
			String brur = getKeyWordCondition("PID", "PLinkman",
					"Tel");
			String timeCondition = getTimeCondition(
					"MakeDate");
			whereSql = whereSql + brur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppPurchaseWithdraw aso = new AppPurchaseWithdraw();
			List list = aso.getPurchaseProviderTotal(request, pagesize,
					whereSql);
			double allsum = aso.getTotalSum(whereSql);
			
			request.setAttribute("allsum", allsum);
			request.setAttribute("list", list);



			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("PID", request.getParameter("PID"));
			request.setAttribute("PName", request.getParameter("PName"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>采购>>列表采购退货按供应商汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
