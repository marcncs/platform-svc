package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;

public class ToPurchaseBillToIncomeAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    //int pagesize = 10;
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    try {
    	String pbid= request.getParameter("PBID");
    	
    	AppWarehouse aw = new AppWarehouse();
        List wls = aw.getEnableWarehouseByVisit(userid);
        ArrayList alw = new ArrayList();
        for(int i=0;i<wls.size();i++){
          Warehouse w = new Warehouse();
          Object[] o = (Object[])wls.get(i);
          w.setId(o[0].toString());
          w.setWarehousename(o[1].toString());
          alw.add(w);
        }
        String isbatch= Constants.IS_BATCH;
      String curdate = "";
      if(!isbatch.equals("readonly")){
    	  curdate = DateUtil.getCurrentDateString().replace("-", "");
      }

	request.setAttribute("curdate", curdate);
	request.setAttribute("isbatch", isbatch);
        request.setAttribute("alw", alw);
    	request.setAttribute("pbid", pbid);
      return mapping.findForward("toaffirm");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
