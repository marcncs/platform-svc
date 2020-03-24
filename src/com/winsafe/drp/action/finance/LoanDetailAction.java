package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppLoan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Loan;
import com.winsafe.drp.dao.LoanForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class LoanDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppLoan al = new AppLoan();
			AppCashBank appcb = new AppCashBank();
			AppUsers au = new AppUsers();

			Loan l = al.getLoanByID(id);
			LoanForm lf = new LoanForm();
			lf.setId(id);
			lf.setUid(l.getUid());
			lf.setUidname(au.getUsersByid(l.getUid()).getRealname());
			lf.setLoandate(String.valueOf(l.getLoandate()));
			lf.setPurpose(l.getPurpose());
			lf.setLoansum(Double.valueOf(l.getLoansum()));
			lf.setCompanyidea(l.getCompanyidea());
			lf.setHubidea(l.getHubidea());
			lf
					.setFundsrcname(appcb.getCashBankById(l.getFundsrc())
							.getCbname());
			lf.setMakeorganid(l.getMakeorganid());
			lf.setMakedeptid(l.getMakedeptid());
			lf.setMakeidname(au.getUsersByid(l.getMakeid())
					.getRealname());
			lf.setMakedate(l.getMakedate().toString());
			lf.setIsaudit(l.getIsaudit());
			lf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, l.getIsaudit(), "global.sys.SystemResource"));
			if (l.getAuditid() != null && l.getAuditid() > 0) {
				lf.setAuditidname(au.getUsersByid(l.getAuditid())
						.getRealname());
			} else {
				lf.setAuditidname("");
			}
			lf.setAuditdate(DateUtil.formatDate(l.getAuditdate()));
			lf.setIsendcase(l.getIsendcase());
			lf.setIsendcasename(Internation.getStringByKeyPosition("YesOrNo",
					request, Integer.valueOf(l.getIsendcase().toString()), "global.sys.SystemResource"));
			if (l.getEndcaseid() != null && l.getEndcaseid() > 0) {
				lf.setEndcaseidname(au.getUsersByid(l.getEndcaseid())
						.getRealname());
			}
			lf.setEndcasedate(DateUtil.formatDate(l.getEndcasedate()));
			
			

			request.setAttribute("lf", lf);

			//DBUserLog.addUserLog(userid,9, "个人借支>>借款详情,编号："+id);
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
