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
import com.winsafe.hbm.util.DbUtil;

public class PrintPurchaseProductTotalAction extends BaseAction {
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
			String Condition = " p.id=pd.pbid and p.isratify=1 " + visitorgan
					+ " ";

			String[] tablename = { "PurchaseBill","PurchaseBillDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition(" MakeDate");
			String blur = getKeyWordCondition("PID", "PName","PLinkman", "Tel","ProductName","SpecMode");
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseBill aso = new AppPurchaseBill();
			List list = aso.getPurchaseProductTotal(whereSql);
			request.setAttribute("list", list);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印采购订单按产品汇总");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
