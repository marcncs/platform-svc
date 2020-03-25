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

public class CancelAuditPaymentLogAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		super.initdata(request);
		try{
			String lid = request.getParameter("PLID");
			AppPaymentLog ar = new AppPaymentLog(); 
			PaymentLog r = ar.getPaymentLogByID(lid);

			if(r.getIsaudit()==0){
	          	 String result = "databases.record.already";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			if(r.getIspay()==1){
	          	 String result = "databases.record.already";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
		    
			ar.updIsAudit(lid, userid,0);

		      request.setAttribute("result", "databases.cancel.success");

			DBUserLog.addUserLog(userid,9,"付款管理>>取消复核付款,编号："+lid); 
			return mapping.findForward("noaudit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
