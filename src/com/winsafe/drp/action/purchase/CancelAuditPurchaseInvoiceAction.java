package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseInvoice;
import com.winsafe.drp.dao.PurchaseInvoice;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditPurchaseInvoiceAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			Integer piid = Integer.valueOf(request.getParameter("PIID"));
			AppPurchaseInvoice apb = new AppPurchaseInvoice();
			PurchaseInvoice pb = apb.getPurchaseInvoiceByID(piid);

			if (pb.getIsaudit() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");// ;mapping.findForward("lock");
			}

			if (!String.valueOf(pb.getAuditid()).contains(userid.toString())) {
				String result = "databases.record.cancelaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");// ;mapping.findForward("lock");
			}

			pb.setIsaudit(0);
			pb.setAuditid(null);
			pb.setAuditdate(null);
			apb.updPurchaseInvoiceByID(pb);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 2, "采购管理>>取消复核采购发票,编号：" + piid);
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return new ActionForward(mapping.getInput());
	}

}
