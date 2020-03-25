package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.hbm.util.Internation;

public class ToAddPaymentLogAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    super.initdata(request);try{

      String paymodeselect =Internation.getSelectTagByKeyAll("PayMode",
              request,
              "paymode", false,null);
      
      AppPayableObject apo = new AppPayableObject();
      
      String orgid = (String)request.getSession().getAttribute("orgid");
      String poid = (String)request.getSession().getAttribute("poid");
      String payeeid=apo.getPayableObjectByOIDOrgID(poid, orgid).getPayee();
      
      request.setAttribute("orgid", orgid);
      request.setAttribute("poid", poid);
      request.setAttribute("payeeid", payeeid);

      AppCashBank apcb = new AppCashBank();
      List cblist = apcb.getAllCashBank();
      request.setAttribute("cblist", cblist);
      request.setAttribute("paymodeselect", paymodeselect);
      return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
