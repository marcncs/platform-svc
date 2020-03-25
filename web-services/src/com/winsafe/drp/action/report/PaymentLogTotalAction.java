package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.CashBank;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.dao.PaymentLogForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PaymentLogTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		int pagesize = 20;
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("pl.makeorganid");
			}
			String Condition = " pl.ispay=1 " + visitorgan;
				
			String[] tablename = { "PaymentLog" };
			String whereSql = getWhereSql(tablename);
			String timeCondition =getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppPaymentLog ail = new AppPaymentLog();

			List slls = ail.getPaymentLog(request, pagesize, whereSql);
			double allsum = ail.getTotalSum(whereSql);
			ArrayList arls = new ArrayList();
			Double totalsum = 0.00;
			AppCashBank apcb = new AppCashBank();
			for (int i = 0; i < slls.size(); i++) {
				PaymentLogForm ilf = new PaymentLogForm();
				PaymentLog il = (PaymentLog) slls.get(i);
				ilf.setId(il.getId());
				ilf.setPoid(il.getPoid());
				ilf.setPayee(il.getPayee());
				ilf.setPaysum(il.getPaysum());
				CashBank cb = apcb.getCashBankById(il.getFundsrc());
				ilf.setFundsrcname(cb != null ? cb.getCbname() : "");
				ilf.setPaymodename(Internation.getStringByKeyPosition(
						"PayMode", request, il.getPaymode(),
						"global.sys.SystemResource"));
				ilf.setMakedate(DateUtil.formatDate(il.getMakedate()));
				ilf.setMakeorganid(il.getMakeorganid());
//				ilf.setMakeidname(au.getUsersByid(il.getMakeid())
//								.getRealname());
				arls.add(ilf);
				totalsum += ilf.getPaysum();
			}
			request.setAttribute("arls", arls);
			request.setAttribute("totalsum", DataFormat
					.currencyFormat(totalsum));
			request.setAttribute("allsum", DataFormat.currencyFormat(allsum));

			List cblist = apcb.getAllCashBank();
			request.setAttribute("cblist", cblist);

			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>财务>>付款汇总");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
