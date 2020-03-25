package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashWasteBook;
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.CashWasteBook;
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class ReceiveIncomeLogAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String lid = request.getParameter("ILID");
			AppIncomeLog ap = new AppIncomeLog();
			IncomeLog p = ap.getIncomeLogByID(lid);

			if (p.getIsaudit() == 0) {
				String result = "databases.record.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (p.getIsreceive() == 1) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppCashBank apcb = new AppCashBank();
			AppCashWasteBook acwb = new AppCashWasteBook();
			CashWasteBook cwb = new CashWasteBook();
			cwb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"cash_waste_book", 0, "")));
			cwb.setCbid(p.getFundattach());
			cwb.setBillno(p.getId());
			cwb.setMemo("收入--收款");
			cwb.setCyclefirstsum(apcb.getCashBankById(p.getFundattach())
					.getTotalsum());
			cwb.setCycleinsum(p.getIncomesum());
			cwb.setCycleoutsum(0d);
			cwb.setCyclebalancesum(cwb.getCyclefirstsum()
							+ cwb.getCycleinsum());
			cwb.setRecorddate(DateUtil.getCurrentDate());
			acwb.addCashWasteBook(cwb);
			
			apcb.AdjustTotalSum(p.getFundattach(), p.getIncomesum());

			ap.updIsReceive(lid, userid, 1);

			request.setAttribute("result", "databases.audit.success");

			DBUserLog.addUserLog(userid, 9, "收款>>复核收款,编号：" + lid);
			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
