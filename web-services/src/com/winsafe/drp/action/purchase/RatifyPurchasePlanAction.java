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

public class RatifyPurchasePlanAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		// Connection conn =null;
		try {
			String ppid = request.getParameter("PPID");
			AppPurchasePlan app = new AppPurchasePlan();
			PurchasePlan pp = new PurchasePlan();
			pp = app.getPurchasePlanByID(ppid);

			if (pp.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.noauditnoratify");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (pp.getIsratify() == 1) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			pp.setRatifyid(userid);
			pp.setIsratify(1);
			pp.setRatifydate(DateUtil.getCurrentDate());
			app.updPurchasePlan(pp);

			request.setAttribute("result", "databases.audit.success");

			DBUserLog.addUserLog(userid, 2,"采购管理>>批准采购计划,编号："+ppid);
			return mapping.findForward("ratify");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
