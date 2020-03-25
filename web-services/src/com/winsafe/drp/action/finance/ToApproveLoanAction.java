package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppLoan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Loan;
import com.winsafe.drp.dao.LoanForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToApproveLoanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		String actid = request.getParameter("actid");
	    String logid = request.getParameter("logid");
		super.initdata(request);super.initdata(request);try{
			AppLoan al = new AppLoan();
			AppCustomer ac = new AppCustomer();
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

			 String approvestatus = Internation.getSelectTagByKeyAll("SubApproveStatus2", request,
			            "ApproveStatus", null, null);
			      String stractid=Internation.getStringByKeyPositionDB("ActID",
			              Integer.valueOf(actid));
			      request.setAttribute("approvestatus",approvestatus);
			      request.setAttribute("stractid", stractid);
			      request.setAttribute("logid", logid);
			request.setAttribute("lf", lf);

//			DBUserLog.addUserLog(userid,"借款详情"); 
			return mapping.findForward("toapprove");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
