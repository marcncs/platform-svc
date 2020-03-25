package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLoan;
import com.winsafe.drp.dao.Loan;
import com.winsafe.drp.util.DBUserLog;

public class DelLoanAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String lid = request.getParameter("LID");
			AppLoan apl = new AppLoan();
			Loan l = apl.getLoanByID(lid);

			if (l.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			apl.delLoan(lid);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 9, "借款>>删除借款,编号：" + lid, l);
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
