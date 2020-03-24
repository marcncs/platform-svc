package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLoan;
import com.winsafe.drp.dao.AppLoanObject;
import com.winsafe.drp.dao.AppReckoning;
import com.winsafe.drp.dao.LoanObject;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class DelLoanObjectAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			Integer id = RequestTool.getInt(request, "ID");
			AppLoanObject apo = new AppLoanObject();
			LoanObject lo = apo.getLoanObjectByID(id);

			AppLoan al = new AppLoan();
			Double loansum = al.getLoanSumByUID(lo.getUid());

			AppReckoning ar = new AppReckoning();
			Double backsum = ar.getReckoningSumByUID(lo.getUid());

			if (loansum > 0 || backsum > 0) {
				request.setAttribute("result", "databases.record.lock");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			apo.delLoanObject(id);
			// String updr = ar.delReceivableByROID(id);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 9, "删除借款对象,编号:" + id, lo);
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
