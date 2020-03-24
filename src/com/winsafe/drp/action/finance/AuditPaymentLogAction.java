package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.util.DBUserLog;

public class AuditPaymentLogAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String lid = request.getParameter("PLID");
			AppPaymentLog ap = new AppPaymentLog();
			PaymentLog p = ap.getPaymentLogByID(lid);

			if (p.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			ap.updIsAudit(lid, userid, 1);

			request.setAttribute("result", "databases.audit.success");

			DBUserLog.addUserLog(userid, 9, "付款管理>>复核付款,编号：" + lid);
			return mapping.findForward("audit");
		} catch (Exception e) {
			request.setAttribute("result", "databases.audit.fail");
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
