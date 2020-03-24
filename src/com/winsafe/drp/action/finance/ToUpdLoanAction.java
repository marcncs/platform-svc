package com.winsafe.drp.action.finance;

import java.util.List;

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

public class ToUpdLoanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		super.initdata(request);super.initdata(request);try{
			AppLoan apa = new AppLoan();
			AppUsers au = new AppUsers();
			Loan l = apa.getLoanByID(id);
			if (l.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			LoanForm lf = new LoanForm();
			lf.setId(id);
			lf.setUid(l.getUid());
			lf.setUidname(au.getUsersByid(l.getUid()).getRealname());
			lf.setLoandate(String.valueOf(l.getLoandate()));
			lf.setPurpose(l.getPurpose());
			lf.setLoansum(Double.valueOf(l.getLoansum()));
			lf.setCompanyidea(l.getCompanyidea());
			lf.setHubidea(l.getHubidea());
			lf.setFundsrc(l.getFundsrc());
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

			AppCashBank appcb = new AppCashBank();
			List cblist = appcb.getAllCashBank();
			request.setAttribute("cblist", cblist);
			request.setAttribute("lf", lf);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
