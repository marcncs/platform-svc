package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashWasteBook;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.CashWasteBook;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class PayPaymentLogAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String lid = request.getParameter("PLID");
			AppPaymentLog ap = new AppPaymentLog();
			PaymentLog p = ap.getPaymentLogByID(lid);

			if (p.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.noaudit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (p.getIspay() == 1) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppCashBank apcb = new AppCashBank();
			AppCashWasteBook acwb = new AppCashWasteBook();
			
			CashWasteBook cwb = new CashWasteBook();
			cwb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"cash_waste_book", 0, "")));
			cwb.setCbid(p.getFundsrc());
			cwb.setBillno(p.getId());
			cwb.setMemo("支出--付款");
			cwb.setCyclefirstsum(apcb.getCashBankById(p.getFundsrc())
					.getTotalsum());
			cwb.setCycleinsum(0d);
			cwb.setCycleoutsum(p.getPaysum());
			cwb.setCyclebalancesum(cwb.getCyclefirstsum()
					- cwb.getCycleoutsum());
			cwb.setRecorddate(DateUtil.getCurrentDate());
			acwb.addCashWasteBook(cwb);

			
			apcb.CancelAdjustTotalSum(p.getFundsrc(), p.getPaysum());
			ap.updIsPay(lid, userid, 1);

			request.setAttribute("result", "databases.audit.success");

			DBUserLog.addUserLog(userid,9, "付款管理>>付款,编号:"+lid);
			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
