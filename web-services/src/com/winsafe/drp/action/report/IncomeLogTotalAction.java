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
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CashBank;
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.dao.IncomeLogForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class IncomeLogTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		AppUsers au = new AppUsers();
		int pagesize = 20;
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = "  il.isreceive=1 " + visitorgan;
			String[] tablename = {"IncomeLog"};
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppIncomeLog ail = new AppIncomeLog();

			List slls = ail.getIncomeLog(request,pagesize, whereSql);
			double allsum = ail.getTotalSum(whereSql);
			ArrayList arls = new ArrayList();
			Double totalsum = 0.00;
			AppCashBank apcb = new AppCashBank();
			for (int i = 0; i < slls.size(); i++) {
				IncomeLogForm ilf = new IncomeLogForm();
				IncomeLog il = (IncomeLog) slls.get(i);
				ilf.setId(il.getId());
				ilf.setRoid(il.getRoid());
				ilf.setDrawee(il.getDrawee());				
				ilf.setIncomesum(il.getIncomesum());	
				CashBank cb = apcb.getCashBankById(il.getFundattach());
				ilf.setFundattachname(cb!=null?cb.getCbname():"");
				ilf.setPaymentmodename(Internation.getStringByPayPositionDB(il.getPaymentmode()));
				ilf.setMakedate(DateUtil.formatDateTime(il.getMakedate()));
				ilf.setMakeorganid(il.getMakeorganid());
				ilf.setMakeidname(au.getUsersByid(il.getMakeid()).getRealname());
				arls.add(ilf);
				totalsum += ilf.getIncomesum();
			}
			request.setAttribute("arls", arls);
			request.setAttribute("totalsum", DataFormat.currencyFormat(totalsum));
			request.setAttribute("allsum", DataFormat.currencyFormat(allsum));
			
		    List cblist = apcb.getAllCashBank();
		    request.setAttribute("cblist", cblist);
			request.setAttribute("FundAttach", request.getParameter("FundAttach"));
			request.setAttribute("PaymentMode", request.getParameter("PaymentMode"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ROID", request.getParameter("ROID"));
			request.setAttribute("payername", request.getParameter("payername"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>财务>>收款汇总");
			return mapping.findForward("incomelogtotal");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
