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
import com.winsafe.hbm.util.DateUtil;

public class AuditPurchaseInvoiceAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			Integer piid = Integer.valueOf(request.getParameter("PIID"));
			AppPurchaseInvoice apb = new AppPurchaseInvoice();
			PurchaseInvoice pb = new PurchaseInvoice();
			pb = apb.getPurchaseInvoiceByID(piid);

			if (pb.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");// ;mapping.findForward("lock");
			}
			pb.setIsaudit(1);
			pb.setAuditid(userid);
			pb.setAuditdate(DateUtil.getCurrentDate());
			apb.updPurchaseInvoiceByID(pb);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 2, "采购管理>>复核采购发票,编号：" + piid);

			return mapping.findForward("audit");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}

}
