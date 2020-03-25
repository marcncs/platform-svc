package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBankAdjust;
import com.winsafe.drp.dao.CashBankAdjust;
import com.winsafe.drp.util.DBUserLog;

public class DelCashBankAdjustAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			
			String id = request.getParameter("ID");
			AppCashBankAdjust apl = new AppCashBankAdjust();
			CashBankAdjust l= apl.getCashBankAdjustById(id);
			
			if(l.getIsaudit()==1){
	          	 String result = "databases.record.audit";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			apl.delCashBankAdjust(id);
		      request.setAttribute("result", "databases.del.success");

		      DBUserLog.addUserLog(userid,9,"现金银行>>删除现金银行调整,编号："+id); 
			return mapping.findForward("del");
		} catch (Exception e) {
			request.setAttribute("result", "databases.del.fail");
			e.printStackTrace();
		}
		return null;
	}

}
