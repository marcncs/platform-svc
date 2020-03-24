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

public class PrintWithdrawProductTotalAction extends BaseAction {
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
			String Condition = " so.id=sod.wid and (so.isaudit = 1 and so.isblankout = 0 "
					+ visitorgan + " )  ";

			String[] tablename = { "Withdraw", "WithdrawDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppWithdraw aso = new AppWithdraw();
			List list = aso.getWithdrawProductTotal(whereSql);
			
			request.setAttribute("list", list);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印零售退货按产品汇总");	
			return mapping.findForward("toprint");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
