package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDemandPrice;
import com.winsafe.drp.dao.DemandPrice;

public class AuditDemandPriceAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
//	    Long userid = users.getUserid();
	   
		try{
			Long dpid = Long.valueOf(request.getParameter("DPID"));
			AppDemandPrice aso = new AppDemandPrice(); 
			DemandPrice so = aso.getDemandPriceByID(dpid);

			if(so.getIsaudit()==1){
	          	 String result = "databases.record.audit";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
	           }
						
//		 aso.updIsAudit(dpid, userid,1);
//
//		      request.setAttribute("result", "databases.audit.success");
//		      DBUserLog.addUserLog(userid,"复核销售报价"); 
			
			return mapping.findForward("audit");
		}catch(Exception e){			
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
