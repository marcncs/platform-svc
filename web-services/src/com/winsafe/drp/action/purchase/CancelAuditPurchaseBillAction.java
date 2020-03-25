package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditPurchaseBillAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String pbid = request.getParameter("PBID");
			AppPurchaseBill apb = new AppPurchaseBill();
			PurchaseBill pb = apb.getPurchaseBillByID(pbid);

			if (pb.getIsaudit() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (pb.getIsratify() == 1) {
				String result = "databases.record.nocancelaudittwo";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (!String.valueOf(pb.getAuditid()).contains(userid.toString())) {
				String result = "databases.record.cancelaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			pb.setAuditdate(null);
			pb.setIsaudit(0);
			pb.setAuditid(null);
			apb.updPurchaseBillByID(pb);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid,2,"采购管理,取消复核采购单,编号："+pbid);

			return mapping.findForward("noaudit");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
