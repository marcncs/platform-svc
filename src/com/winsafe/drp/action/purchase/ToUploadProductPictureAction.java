package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ToUploadProductPictureAction
    extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
   try{
	   String pid = "";
		//String cname = "";
		//AppCustomer appCustomer = new AppCustomer();
		pid = (String) request.getSession().getAttribute("pid");
//		if (pid != null && !pid.equals("")&&!pid.equals("null")) {
//			Customer customer = appCustomer.getCustomer(cid);
//			cname = customer.getCname();
//		}
		request.setAttribute("pid", pid);
//		request.setAttribute("cname", cname);
		
     return mapping.findForward("toupload");

   }catch(Exception e){
     e.printStackTrace();
   }
   finally{

   }

   return mapping.getInputForward();
 }

}
