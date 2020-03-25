package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSaleRepair;
import com.winsafe.drp.dao.SaleRepair;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class BlankoutSaleRepairAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		try{
			AppSaleRepair apb = new AppSaleRepair();
			SaleRepair pb = apb.getSaleRepairByID(id);
			if (pb.getIsaudit()==1) {
				String result = "databases.record.approvestatus";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

	        UsersBean users = UserManager.getUser(request);
//		    Long userid = users.getUserid();
//	        apb.BlankoutSaleRepair(id, userid);
	        
		      request.setAttribute("result", "databases.del.success");
		     
//		      DBUserLog.addUserLog(userid,"作废销售返修"); 
		      return mapping.findForward("delresult");
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
