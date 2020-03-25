package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSaleRepair;
import com.winsafe.drp.dao.AppSaleRepairDetail;
import com.winsafe.drp.dao.SaleRepair;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelSaleRepairAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String id = request.getParameter("id");			
			AppSaleRepair aso = new AppSaleRepair();
			AppSaleRepairDetail appsrd = new AppSaleRepairDetail();
			SaleRepair so= aso.getSaleRepairByID(id);
			if(so.getIsaudit()==1){
	          	 String result = "databases.record.nodel";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			if ( so.getIsbacktrack() ==1 ){
				 String result = "databases.record.nodel";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			aso.delSaleRepair(id);
			 appsrd.delSaleRepairDetailBySrid(id);
			
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
//		      Long userid = users.getUserid();
//		      DBUserLog.addUserLog(userid,"删除销售返修"); 
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

}
