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
import com.winsafe.drp.dao.CashWasteBook;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditCashBankAdjustAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppCashBankAdjust ap = new AppCashBankAdjust();
			CashBankAdjust p = ap.getCashBankAdjustById(id);

			if (p.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			
			AppCashBank acb = new AppCashBank();
			AppCashWasteBook acwb = new AppCashWasteBook();
			CashWasteBook cwb = new CashWasteBook();
			cwb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"cash_waste_book", 0, "")));
			cwb.setCbid(p.getCbid());
			cwb.setBillno(p.getId());
			cwb.setMemo("现金银行调整");
			Double firstsum  = acb.getCashBankById(p.getCbid()).getTotalsum();
			cwb.setCyclefirstsum(firstsum);
			cwb.setCycleinsum(p.getAdjustsum());
			cwb.setCycleoutsum(0d);
			cwb.setCyclebalancesum(firstsum+p.getAdjustsum());
			cwb.setRecorddate(DateUtil.getCurrentDate());
			acwb.addCashWasteBook(cwb);
		
			acb.AdjustTotalSum(p.getCbid(), p.getAdjustsum());

			ap.updIsAudit(id, userid, 1);

			request.setAttribute("result", "databases.audit.success");

			DBUserLog.addUserLog(userid, 9, "现金银行>>复核现金银行调整,编号：" + id);
			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
