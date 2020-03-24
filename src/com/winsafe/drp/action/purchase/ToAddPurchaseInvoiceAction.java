package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToAddPurchaseInvoiceAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    try{
    	String invoicetypename = Internation.getSelectTagByKeyAll("InvoiceType",
				request, "invoicetype", false, null);
//    	String purchasesortname = Internation.getSelectTagByKeyAllDB("PurchaseSort",
//				 "purchasesort", false);
    	
//    	AppDept ad = new AppDept();
//    	List aldept = ad.getDeptByOID(users.getMakeorganid());

//        
//        AppUsers au = new AppUsers();
//        List uls = au.getIDAndLoginNameByOID(users.getMakeorganid());
//        ArrayList als = new ArrayList();
//        for(int u=0;u<uls.size();u++){
//        	UsersBean us = new UsersBean();
//        	Object[] ub = (Object[]) uls.get(u);
//        	us.setUserid(Long.valueOf(ub[0].toString()));
//        	us.setRealname(ub[2].toString());
//        	als.add(us);
//        }
    	
//        request.setAttribute("als", als);
//        request.setAttribute("aldept", aldept);
        request.setAttribute("invoicetypename", invoicetypename);
//        request.setAttribute("purchasesortname", purchasesortname);
      return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
