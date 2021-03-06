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

public class CancelAuditLoanAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		super.initdata(request);
		try{
			String lid = request.getParameter("LID");
			AppLoan ar = new AppLoan(); 
			Loan r = ar.getLoanByID(lid);

			if(r.getIsaudit()==0){
	          	 String result = "databases.record.already";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			if(!String.valueOf(r.getAuditid()).contains(userid.toString())){
	          	 String result = "databases.record.cancelaudit";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
		    
			ar.updIsAudit(lid, userid,0);
		      request.setAttribute("result", "databases.cancel.success");

			DBUserLog.addUserLog(userid,9,"个人借支>>取消复核借款,编号："+lid); 
			return mapping.findForward("noaudit");
		}catch(Exception e){
			request.setAttribute("result","databases.cancel.fail");
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
