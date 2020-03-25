package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppPurchasePlanDetail;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelPurchasePlanAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");
		try {
			AppPurchasePlan app = new AppPurchasePlan();
			PurchasePlan pp = app.getPurchasePlanByID(id);
			if (pp.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
			apad.delPurchasePlanDetailByPpID(id);
			app.delPurchasePlan(id);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid,2, "采购管理>>删除采购计划,编号："+id,pp);

			return mapping.findForward("delresult");

		} catch (Exception e) {

			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
