package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdReceivableAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");

    super.initdata(request);try{
      AppReceivable ar = new AppReceivable();
      Receivable r = ar.getReceivableByID(id);
      
      ReceivableForm rf = new ReceivableForm();
      rf.setId(id);
      rf.setRoid(r.getRoid());
      rf.setBillno(r.getBillno());
      rf.setReceivablesum(r.getReceivablesum());
      rf.setReceivabledescribe(r.getReceivabledescribe());      
      rf.setPaymentmodename(Internation.getSelectTagByKeyAll("PaymentMode",
				request, "paymentmode", String.valueOf(r.getPaymentmode()), null));

      request.setAttribute("rf",rf);
      return mapping.findForward("toupd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
