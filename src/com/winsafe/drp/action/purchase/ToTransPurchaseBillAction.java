package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppPurchasePlanDetail;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.PurchasePlan;

public class ToTransPurchaseBillAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String ppid = request.getParameter("PPID");
		try{
			
			AppPurchasePlan apppp = new AppPurchasePlan();
			PurchasePlan pp = apppp.getPurchasePlanByID(ppid);
			
			AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
		      List ppdls = apad.seachPurchasePlanDetailByPaID(ppid);
		     

//		     
//		      String iscomplete = Internation.getSelectTagByKeyAll("YesOrNo", request,
//		              "iscomplete", "0", null);
//		      
//		      String purchasesortname = Internation.getSelectTagByKeyAllDB("PurchaseSort",
//						 "purchasesort", false);
//		      
//		      String paymodename = Internation.getSelectTagByKeyAll("PayMode", request,
//		                 "paymode", false, null);
//		    	
		    	
		        
		        AppInvoiceConf aic = new AppInvoiceConf();
				List ils = aic.getAllInvoiceConf();
				ArrayList icls = new ArrayList();
				for (int u = 0; u < ils.size(); u++) {
						InvoiceConf ic = (InvoiceConf) ils.get(u);

						icls.add(ic);
				}
				request.setAttribute("icls", icls);
		    	
			  request.setAttribute("pp",pp);
		      request.setAttribute("ppid",ppid);
		      request.setAttribute("als",ppdls);
		      
			return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ActionForward(mapping.getInput());
	}
}
