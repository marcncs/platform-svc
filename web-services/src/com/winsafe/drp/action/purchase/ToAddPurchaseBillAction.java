package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToAddPurchaseBillAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    try{
    	String purchasesortname = Internation.getSelectTagByKeyAllDB("PurchaseSort",
				 "purchasesort", false);
    	 String paymodename = Internation.getSelectTagByKeyAll("PayMode", request,
                 "paymode", false, null);
    	
    	AppDept ad = new AppDept();
    	List aldept = ad.getDeptByOID(users.getMakeorganid());
       
        
        AppUsers au = new AppUsers();
        List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
        
        
        AppInvoiceConf aic = new AppInvoiceConf();
		List ils = aic.getAllInvoiceConf();
		ArrayList icls = new ArrayList();
		for (int u = 0; u < ils.size(); u++) {
				InvoiceConf ic = (InvoiceConf) ils.get(u);

				icls.add(ic);
		}
		request.setAttribute("icls", icls);
    	
        request.setAttribute("als", als);
        request.setAttribute("aldept", aldept);
        request.setAttribute("paymodename", paymodename);
        request.setAttribute("purchasesortname", purchasesortname);
      return mapping.findForward("toadd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
