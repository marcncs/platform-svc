package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToAddPurchaseInquireAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    try{
    	String purchaseplanid = (String) request.getSession().getAttribute("purchaseplanid");
      AppUsers au = new AppUsers();
      String seeto = "";
      seeto = au.getUsersByID(userid).getRealname();
      request.setAttribute("purchaseplanid", purchaseplanid);
      request.setAttribute("seeto",seeto);
      return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
