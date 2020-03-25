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

public class CancelRatifyPurchasePlanAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String ppid = request.getParameter("PPID");
			AppPurchasePlan app = new AppPurchasePlan();
			PurchasePlan pp = app.getPurchasePlanByID(ppid);

			if (pp.getIsratify() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (!String.valueOf(pp.getRatifyid()).contains(userid.toString())) {
				String result = "databases.record.cancelaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			pp.setRatifyid(null);
			pp.setIsratify(0);
			pp.setRatifydate(null);
			app.updPurchasePlan(pp);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 2,"采购管理>>取消采购计划批准,编号："+ppid);

			return mapping.findForward("noratify");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
