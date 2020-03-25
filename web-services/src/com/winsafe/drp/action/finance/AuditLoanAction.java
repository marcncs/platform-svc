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

public class AuditLoanAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		super.initdata(request);
		try{
			String lid = request.getParameter("LID");
			AppLoan ap = new AppLoan(); 
			Loan p = ap.getLoanByID(lid);

			if(p.getIsaudit()==1){
	          	 String result = "databases.record.audit";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			ap.updIsAudit(lid, userid,1);

		      request.setAttribute("result", "databases.audit.success");

			DBUserLog.addUserLog(userid,9,"个人借支>>复核借款,编号："+lid); 
			return mapping.findForward("audit");
		}catch(Exception e){
			request.setAttribute("result", "databases.audit.fail");
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
