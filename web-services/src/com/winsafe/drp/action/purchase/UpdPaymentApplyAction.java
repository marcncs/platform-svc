package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPaymentApply;
import com.winsafe.drp.dao.PaymentApply;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class UpdPaymentApplyAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    //ValidateProvideLinkman vpl = (ValidateProvideLinkman) form;
	  String pid = request.getParameter("pid");


	    try {
	    	if(pid==null||pid.equals("null")||pid.equals("")){
		    	String result = "databases.upd.fail";
		    	request.setAttribute("result", "databases.upd.success");
		    	return new ActionForward("/sys/lockrecord.jsp");
		    }
	    	
	AppPaymentApply apa = new AppPaymentApply();
    PaymentApply pa = new PaymentApply();
    Integer id = Integer.valueOf(request.getParameter("id"));
    pa = apa.getPaymentApplyByID(id);
    
    pa.setId(id);
    pa.setPid(pid);
    pa.setPname(request.getParameter("pname"));
    pa.setPlinkman(request.getParameter("plinkman"));
    pa.setTel(request.getParameter("tel"));
    pa.setPurchasedept(Integer.valueOf(request.getParameter("purchasedept")));
    pa.setBillno(request.getParameter("billno"));
    pa.setTotalsum(Double.valueOf(request.getParameter("totalsum")));
    pa.setPurchaseid(Integer.valueOf(request.getParameter("purchaseid")));
    pa.setPaymentmode(Integer.parseInt(request.getParameter("paymentmode")));
    pa.setBankaccount(request.getParameter("bankaccount"));
    pa.setDoorname(request.getParameter("doorname"));
    pa.setBankname(request.getParameter("bankname"));

    apa.updPaymentApply(pa);
      
     request.setAttribute("result", "databases.upd.success");
     UsersBean users = UserManager.getUser(request);
     Integer userid = users.getUserid();
     //DBUserLog.addUserLog(userid,"修改付款申请");

      return mapping.findForward("updresult");
    }
    catch (Exception e) {

      e.printStackTrace();
    }
    finally {

    }

    return new ActionForward(mapping.getInput());
  }
}
