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
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddLoanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			Loan l = new Loan();

			l.setId(MakeCode.getExcIDByRandomTableName("loan", 2, "LN"));
			l.setUid(RequestTool.getInt(request, "uid"));
			l.setLoandate(DateUtil.StringToDate(request
					.getParameter("loandate")));
			l.setPurpose(request.getParameter("purpose"));
			l.setLoansum(Double.valueOf(request.getParameter("loansum")));
			l.setCompanyidea(request.getParameter("companyidea"));
			l.setHubidea(request.getParameter("hubidea"));
			l.setFundsrc(RequestTool.getInt(request, "fundsrc"));
			l.setMakeorganid(users.getMakeorganid());
			l.setMakedeptid(users.getMakedeptid());
			l.setMakeid(userid);
			l.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			l.setIsaudit(0);
			l.setIsendcase(0);

			
			AppLoan apo = new AppLoan();
			apo.addLoan(l);
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 9, "借款>>新增借款,编号：" + l.getId());
			return mapping.findForward("addresult");
		} catch (Exception e) {
			request.setAttribute("result", "databases.add.fail");
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}
}
