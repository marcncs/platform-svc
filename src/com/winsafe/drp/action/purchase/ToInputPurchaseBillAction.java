package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.PurchasePlan;
public class ToInputPurchaseBillAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String paid = request.getParameter("PAID");

    try{
    	System.out.println("=======================");
      request.setAttribute("paid",paid);
      AppPurchasePlan appPup = new AppPurchasePlan();
      
      PurchasePlan pp  = appPup.getPurchasePlanByID(paid);
      
      
      request.setAttribute("", pp);
     return mapping.findForward("");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
