package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.util.DBUserLog;

public class UpdReceivableAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  super.initdata(request);
    try {
      AppReceivable ar = new AppReceivable();
      String id = request.getParameter("id");
      Receivable r = ar.getReceivableByID(id);
      Receivable oldW = (Receivable)BeanUtils.cloneBean(r);
      
      r.setReceivablesum(Double.valueOf(request.getParameter("receivablesum")));
      r.setBillno(request.getParameter("billno"));
      r.setReceivabledescribe(request.getParameter("receivabledescribe"));
      r.setPaymentmode(Integer.valueOf(request.getParameter("paymentmode")));
      ar.updReceivable(r);

      request.setAttribute("result", "databases.upd.success");


      DBUserLog.addUserLog(userid,9,"收款管理>>修改应收款,编号："+id,oldW,r); 
      
      return mapping.findForward("updresult");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
