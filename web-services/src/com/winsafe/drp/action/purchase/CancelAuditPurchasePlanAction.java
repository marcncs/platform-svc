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

public class CancelAuditPurchasePlanAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String ppid = request.getParameter("PPID");
			AppPurchasePlan app = new AppPurchasePlan();
			PurchasePlan pp = app.getPurchasePlanByID(ppid);

			if (pp.getIsaudit() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");// ;mapping.findForward("lock");
			}

			if (pp.getIsratify() == 1) {
				String result = "databases.record.nocancelaudittwo";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");// ;mapping.findForward("lock");
			}

			if (!String.valueOf(pp.getAuditid()).contains(userid.toString())) {
				String result = "databases.record.cancelaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");// ;mapping.findForward("lock");
			}

			pp.setIsaudit(0);
			pp.setAuditid(null);
			pp.setAuditdate(null);
			app.updPurchasePlan(pp);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 2, "采购管理>>取消复核采购计划,编号：" + ppid);

			return mapping.findForward("noaudit");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
