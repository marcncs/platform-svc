package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppCashWasteBook;
import com.winsafe.drp.dao.AppReckoning;
import com.winsafe.drp.dao.CashWasteBook;
import com.winsafe.drp.dao.Reckoning;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditReckoningAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String rid = request.getParameter("RID");
			AppReckoning ap = new AppReckoning();
			Reckoning p = ap.getReckoningByID(rid);

			if (p.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (p.getIscash() == 1) {

				AppCashBank apcb = new AppCashBank();
				AppCashWasteBook acwb = new AppCashWasteBook();

				CashWasteBook cwb = new CashWasteBook();
				cwb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"cash_waste_book", 0, "")));
				cwb.setCbid(p.getFundattach());
				cwb.setBillno(p.getId());
				cwb.setMemo("支出--借款");
				cwb.setCyclefirstsum(apcb.getCashBankById(p.getFundattach())
						.getTotalsum());
				cwb.setCycleinsum(p.getBacksum());
				cwb.setCycleoutsum(0d);
				cwb.setCyclebalancesum(cwb.getCyclefirstsum()
						+ cwb.getCycleinsum());
				cwb.setRecorddate(DateUtil.StringToDatetime(DateUtil
						.getCurrentDateTime()));
				acwb.addCashWasteBook(cwb);

				apcb.AdjustTotalSum(p.getFundattach(), p.getBacksum());
			}

			ap.updIsAudit(rid, userid, 1);
			request.setAttribute("result", "databases.audit.success");

			DBUserLog.addUserLog(userid,9, "个人借支>>复核借款清算,编号："+rid);

			return mapping.findForward("audit");
		} catch (Exception e) {
			request.setAttribute("result", "databases.audit.fail");
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
