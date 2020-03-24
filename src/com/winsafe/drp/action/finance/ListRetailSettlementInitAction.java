package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ListRetailSettlementInitAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;
 
    String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo", request,
          "IsAudit", true, null);
    String issettlementselect = Internation.getSelectTagByKeyAll("YesOrNo", request,
            "IsSettlement", true, null);
      

      ArrayList alsb = new ArrayList();
    try {
    	
    	AppWarehouse aw = new AppWarehouse();
    	List wls = aw.getCanUseWarehouse();        
        request.setAttribute("alw",wls);

    	AppUsers au = new AppUsers();
    	 List uls = au.getIDAndLoginName();
	      ArrayList als = new ArrayList();
	      for(int u=0;u<uls.size();u++){
	      	UsersBean ubs = new UsersBean();
	      	Object[] ub = (Object[]) uls.get(u);
	      	ubs.setUserid(Integer.valueOf(ub[0].toString()));
	      	ubs.setRealname(ub[2].toString());
	      	als.add(ubs);
	      }
	      
	      request.setAttribute("als", als);
	      request.setAttribute("alsb", alsb);
      request.setAttribute("isauditselect", isauditselect);
      request.setAttribute("issettlementselect",issettlementselect);

      //DBUserLog.addUserLog(userid,"列表零售单"); 
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}