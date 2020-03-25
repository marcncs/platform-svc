package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.PayableObjectForm;

public class ToUpdPayableObjectAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String oid = request.getParameter("OID");
    String orgid = request.getParameter("ORGID");

    super.initdata(request);try{
      AppPayableObject aro = new AppPayableObject();
      AppUsers au = new AppUsers();
      AppCustomer ac = new AppCustomer();     
      PayableObject ro = aro.getPayableObjectByOIDOrgID(oid,orgid);
      
//      if (ro.getTotalpayablesum() > 0||ro.getAlreadypayablesum()>0) { 
//          String result = "databases.record.lock";
//          request.setAttribute("result", result);
//          return mapping.findForward("lock");
//        }

      PayableObjectForm rof = new PayableObjectForm();
      rof.setId(ro.getId());
      rof.setObjectsort(ro.getObjectsort());
      rof.setPayee(ro.getPayee());
      

      request.setAttribute("rof",rof);
      return mapping.findForward("toupd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
