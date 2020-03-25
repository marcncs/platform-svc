package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToQualityPassToPurchaseIncomeAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;
    super.initdata(request);try{
//    	UsersBean users = UserManager.getUser(request);
//	    Long userid = users.getUserid();
//    	String qpid= request.getParameter("QPID");
//    	
//    	AppWarehouse aw = new AppWarehouse();
//        List wls = aw.getEnableWarehouseByVisit(userid);
//        ArrayList alw = new ArrayList();
//        for(int i=0;i<wls.size();i++){
//          Warehouse w = new Warehouse();
//          Object[] o = (Object[])wls.get(i);
//          w.setId(Long.valueOf(o[0].toString()));
//          w.setWarehousename(o[1].toString());
//          alw.add(w);
//        }

//        request.setAttribute("alw", alw);
//    	request.setAttribute("qpid", qpid);
      return mapping.findForward("toaffirm");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
