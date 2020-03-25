package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.ReceivableObjectForm;

public class ToUpdReceivableObjectAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String oid = request.getParameter("OID");
    String orgid = request.getParameter("ORGID");

    super.initdata(request);try{
      AppReceivableObject aro = new AppReceivableObject();
      ReceivableObject ro = aro.getReceivableObjectByIDOrgID(oid,orgid);
      
//      if (ro.getTotalreceivablesum() > 0||ro.getAlreadyreceivablesum()>0) { 
//          String result = "databases.record.lock";
//          request.setAttribute("result", result);
//          return mapping.findForward("lock");
//        }

      ReceivableObjectForm rof = new ReceivableObjectForm();
      rof.setId(ro.getId());
      rof.setObjectsort(ro.getObjectsort());
      rof.setPayer(ro.getPayer());
    

      request.setAttribute("rof",rof);
      return mapping.findForward("toupd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
