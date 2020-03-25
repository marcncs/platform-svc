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
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddPaymentApplyAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    MakeCode mc = new MakeCode();
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();

    try {

      PaymentApply pa = new PaymentApply();
      Integer id = Integer.valueOf(mc.getExcIDByRandomTableName(
              "payment_apply",0,""));
      pa.setId(id);
      pa.setPid(request.getParameter("pid"));
      pa.setPname(request.getParameter("pname"));
      pa.setPlinkman(request.getParameter("plinkman"));
      pa.setTel(request.getParameter("tel"));
      pa.setPurchasedept(Integer.valueOf(request.getParameter("purchasedept")));
      pa.setBillno(request.getParameter("billno"));
      String strtotalsum = request.getParameter("totalsum");
      if( strtotalsum!=null&&!"".equals(strtotalsum) ){
    	  pa.setTotalsum(Double.valueOf(strtotalsum));
	    }else{
	    	pa.setTotalsum(0.00);
	    }
      pa.setPurchaseid(Integer.valueOf(request.getParameter("purchaseid")));
      pa.setPaymentmode(Integer.parseInt(request.getParameter("paymentmode")));
      pa.setBankaccount(request.getParameter("bankaccount"));
      pa.setDoorname(request.getParameter("doorname"));
      pa.setBankname(request.getParameter("bankname"));
      pa.setMakeid(userid);
      pa.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
      pa.setIsendcase(0);
      pa.setEndcaseid(Integer.valueOf(0));
     
      AppPaymentApply apa = new AppPaymentApply();
      apa.addPaymentApply(pa);

      
      request.setAttribute("result", "databases.add.success");

     // DBUserLog.addUserLog(userid,"新增付款申请");

      return mapping.findForward("addresult");
    }
    catch (Exception e) {

      e.printStackTrace();
    }
    finally {

    }

    return mapping.getInputForward();
  }
}
