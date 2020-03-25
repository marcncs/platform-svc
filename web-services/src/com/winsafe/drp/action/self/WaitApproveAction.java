package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DbUtil;


public class WaitApproveAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    try{
    	HibernateUtil.currentSession();
    	UsersBean users = UserManager.getUser(request);
        Integer userid = users.getUserid();
    	int pagesize=1;
    	String wheresql=" where approveid="+userid+" and approve=0 ";
    	
    	int saleorder=DbUtil.getRecordCount(pagesize, wheresql, "SaleOrderApprove");
    	int shipmentbill=DbUtil.getRecordCount(pagesize, wheresql, "ShipmentBillApprove");

    	
    	request.setAttribute("saleorder", saleorder);
    	request.setAttribute("shipmentbill", shipmentbill);


       // DBUserLog.addUserLog(userid,"待审阅信息");
      return mapping.findForward("waitapprove");
    }catch(Exception e){
      e.printStackTrace();
    }finally{
    	//HibernateUtil.closeSession();
    }
    return new ActionForward(mapping.getInput());

  }
}
