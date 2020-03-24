package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class ApprovePurchasePlanAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		
		try {
			String ppid = request.getParameter("PPID");
			AppPurchasePlan apa = new AppPurchasePlan();
			PurchasePlan pp = apa.getPurchasePlanByID(ppid);
			
			pp.setIsaudit(1);
			pp.setAuditid(userid);
			pp.setAuditdate(DateUtil.getCurrentDate());

			apa.updPurchasePlan(pp);
			
			request.setAttribute("result", "databases.ratify.success");
			DBUserLog.addUserLog(userid,2,"采购管理>>审阅采购计划,编号："+ppid);
			return mapping.findForward("approve");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
