package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.hbm.util.Internation;

public class ToAddIncomeLogAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {

    super.initdata(request);try{
     
    	 String paymodeselect =Internation.getSelectTagByKeyAll("PaymentMode",
                 request,
                 "paymentmode", false,null);
    	 
    	 AppReceivableObject aro = new AppReceivableObject();
    	 
    	 String orgid = (String)request.getSession().getAttribute("orgid");
         String roid = (String)request.getSession().getAttribute("roid");
         String payer=aro.getReceivableObjectByIDOrgID(roid, orgid).getPayer();
         
         request.setAttribute("orgid", orgid);
         request.setAttribute("roid", roid);
         request.setAttribute("payer", payer);
         
      AppCashBank apcb = new AppCashBank();
      List cblist = apcb.getAllCashBank();

      request.setAttribute("cblist",cblist);
      request.setAttribute("paymodeselect", paymodeselect);
      return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
