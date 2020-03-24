package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdPayableAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");

    super.initdata(request);try{

      AppPayable apa = new AppPayable();
      Payable pa = apa.getPayableByID(id);

      PayableForm paf = new PayableForm();
      paf.setId(id);
      paf.setPayablesum(pa.getPayablesum());
      paf.setBillno(pa.getBillno());
      paf.setPayabledescribe(pa.getPayabledescribe());
      paf.setPaymodename(Internation.getSelectTagByKeyAll("PayMode",
				request, "paymode", String.valueOf(pa.getPaymode()), null));
      request.setAttribute("paf",paf);
      return mapping.findForward("toupd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
