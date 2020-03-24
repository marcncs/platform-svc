package com.winsafe.drp.action.sales;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.Customer;


public class ToAddDemandPriceAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    try {

    	String cid = "";
		String cname = "";
		AppCustomer appCustomer = new AppCustomer();
		cid = (String) request.getSession().getAttribute("cid");
		if (cid != null && !cid.equals("")&&!cid.equals("null")) {
			Customer customer = appCustomer.getCustomer(cid);
			cname = customer.getCname();
		}
		request.setAttribute("cid", cid);
		request.setAttribute("cname", cname);
		
      return mapping.findForward("toadd");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return mapping.getInputForward();
  }

}
