package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashBankAdjust;
import com.winsafe.drp.dao.AppCashWasteBook;
import com.winsafe.drp.dao.CashBankAdjust;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditCashBankAdjustAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppCashBankAdjust ar = new AppCashBankAdjust();
			CashBankAdjust r = ar.getCashBankAdjustById(id);

			if (r.getIsaudit() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (!String.valueOf(r.getAuditid()).contains(userid.toString())) {
				String result = "databases.record.cancelaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppCashBank acb = new AppCashBank();
			acb.CancelAdjustTotalSum(r.getCbid(), r.getAdjustsum());

			AppCashWasteBook acwb = new AppCashWasteBook();
			acwb.delCashWasteBookByBillno(id);

			ar.updIsAudit(id, userid, 0);

			request.setAttribute("result", "databases.cancel.success");

			DBUserLog.addUserLog(userid, 9, "现金银行>>取消复核调整单，编号：" + id);
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
