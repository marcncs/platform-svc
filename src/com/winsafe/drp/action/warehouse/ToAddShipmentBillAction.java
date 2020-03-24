package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToAddShipmentBillAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
//    Long userid = users.getUserid();
    super.initdata(request);try{
      AppUsers au = new AppUsers();
      String seeto = "";
//      seeto = au.getUsersByID(userid).getRealname();
      
      
      AppWarehouse aw = new AppWarehouse();
      List wls = aw.getCanUseWarehouse();
     
      
      
      String transportmodeselect = Internation.getSelectTagByKeyAllDB("TransportMode", "TransportMode", false);
      
      request.setAttribute("transportmodeselect", transportmodeselect);
      request.setAttribute("alw",wls);
      request.setAttribute("seeto",seeto);
      return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
