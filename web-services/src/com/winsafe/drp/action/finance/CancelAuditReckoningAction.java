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

public class CancelAuditReckoningAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String rid = request.getParameter("RID");
			AppReckoning ar = new AppReckoning();
			Reckoning r = ar.getReckoningByID(rid);

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

			if (r.getIscash() == 1) {
				AppCashBank apcb = new AppCashBank();
				AppCashWasteBook acwb = new AppCashWasteBook();

				CashWasteBook cwb = new CashWasteBook();
				cwb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"cash_waste_book", 0, "")));
				cwb.setCbid(r.getFundattach());
				cwb.setBillno(r.getId());
				cwb.setMemo("支出--取消清算");
				cwb.setCyclefirstsum(apcb.getCashBankById(r.getFundattach())
						.getTotalsum());
				cwb.setCycleinsum(-r.getBacksum());
				cwb.setCycleoutsum(0d);
				cwb.setCyclebalancesum(cwb.getCyclefirstsum()
						+ cwb.getCycleinsum());
				cwb.setRecorddate(DateUtil.StringToDatetime(DateUtil
						.getCurrentDateTime()));
				acwb.addCashWasteBook(cwb);

				apcb.CancelAdjustTotalSum(r.getFundattach(), r.getBacksum());
			}

			ar.updIsAudit(rid, userid, 0);

			request.setAttribute("result", "databases.cancel.success");

			DBUserLog.addUserLog(userid, 9, "个人借支>>取消复核借款清算,编号：" + rid);
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
