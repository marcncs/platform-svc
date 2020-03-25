package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Warehouse;

public class ToAddProductInterconvertAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  super.initdata(request);
    try{

//      
      AppWarehouse aw = new AppWarehouse();
//      List wls = aw.getWarehouseListByOID(users.getMakeorganid());//.getEnableWarehouseByVisit(userid);
//      ArrayList alw = new ArrayList();
//      for (int i = 0; i < wls.size(); i++) {
//        Warehouse w = (Warehouse) wls.get(i);
//        
//        alw.add(w);
//      }
//      
      
      List iwls = aw.getCanUseWarehouseByOid(users.getMakeorganid());//getEnableWarehouse();
      ArrayList aliw = new ArrayList();
      for (int i = 0; i < iwls.size(); i++) {
        Warehouse iw = (Warehouse) iwls.get(i);
        
        aliw.add(iw);
      }

      //request.setAttribute("alpls",alpls);
//      request.setAttribute("alw",alw);
      request.setAttribute("aliw", aliw);
      //request.setAttribute("pid",pid);

      return mapping.findForward("success");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
