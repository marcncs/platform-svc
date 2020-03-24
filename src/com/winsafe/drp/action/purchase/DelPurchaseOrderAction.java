package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelPurchaseOrderAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) throws Exception {
		//Connection conn = null;
		String id = request.getParameter("ID");
		try{
			AppPurchaseOrder apb = new AppPurchaseOrder();
			PurchaseOrder pb = new PurchaseOrder();
			pb = apb.getPurchaseOrderByID(id);
			if (pb.getIsrefer()==1) {
				String result = "databases.record.approvestatus";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

	       apb.delPurchaseOrder(id);
	        
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      //DBUserLog.addUserLog(userid,"删除采购订单");

		      return mapping.findForward("delresult");
	        
		}catch(Exception e){

			e.printStackTrace();
		}finally {
		      
		    }
		return mapping.getInputForward();
	}
}
