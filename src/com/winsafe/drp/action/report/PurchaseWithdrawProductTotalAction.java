package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PurchaseWithdrawProductTotalAction extends BaseAction {
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
			String Condition = " pw.id=pwd.pwid and  pw.isendcase=1 and pw.isblankout=0 "
					+ visitorgan + " ";

			String[] tablename = { "PurchaseWithdraw", "PurchaseWithdrawDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition(" MakeDate");
			String blur = getKeyWordCondition("PName", "ProductName",
					"ProductID", "PID", "MakeDate", "MakeOrganID");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseWithdrawDetail asod = new AppPurchaseWithdrawDetail();
			List sumobj = asod.getTotalSubum(whereSql);

			List list = asod.getPurchaseProductTotal(request, pagesize,
					whereSql);

			request.setAttribute("list", list);
			request.setAttribute("sumobj", sumobj);
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>采购>>列表采购退货按产品汇总");
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
