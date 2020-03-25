package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.util.DBUserLog;

public class AuditReceivableAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		super.initdata(request);
		try{
			String rid = request.getParameter("RID");
			AppReceivable ar = new AppReceivable(); 
			Receivable r = ar.getReceivableByID(rid);

//			if(r.getIsaudit()==1){
//	          	 String result = "databases.record.audit";
//	               request.setAttribute("result", result);
//	               return new ActionForward("/sys/lockrecordclose.jsp");
//	           }
			Double receivablesum = r.getReceivablesum();
			
		    
		   
		    AppReceivableObject aro = new AppReceivableObject();
		    //if(r.getIsred()==0){
		    aro.addReceivableSum(r.getRoid(), receivablesum);
		    //}else{
		    //	String remove = aro.removeReceivableSum(r.getRoid(), receivablesum);
		    //}

			ar.updIsAudit(rid, userid,1);

		      request.setAttribute("result", "databases.audit.success");

			DBUserLog.addUserLog(userid,9,"收款管理>>复核应收款,编号："+rid); 
			return mapping.findForward("audit");
		}catch(Exception e){

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
