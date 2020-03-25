package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.dao.PurchaseWithdraw;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelPurchaseWithdrawAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
		
			String id = request.getParameter("id");			
			AppPurchaseWithdraw aso = new AppPurchaseWithdraw();
			AppPurchaseWithdrawDetail apppwd = new AppPurchaseWithdrawDetail();
			PurchaseWithdraw so= aso.getPurchaseWithdrawByID(id);
			PurchaseWithdraw oldso = (PurchaseWithdraw)BeanUtils.cloneBean(so);
			if(so.getIsaudit()==1){
	          	 String result = "databases.record.nodel";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			aso.delPurchaseWithdraw(id);
			apppwd.delPurchaseWithdrawDetailByPWID(id);
			
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      DBUserLog.addUserLog(userid,2,"采购退货>>删除采购退货,编号："+id, oldso); 
			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

}
