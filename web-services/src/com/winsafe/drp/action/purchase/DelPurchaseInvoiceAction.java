package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseInvoice;
import com.winsafe.drp.dao.AppPurchaseInvoiceDetail;
import com.winsafe.drp.dao.PurchaseInvoice;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelPurchaseInvoiceAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			AppPurchaseInvoice apb = new AppPurchaseInvoice();
			PurchaseInvoice pb = new PurchaseInvoice();
			pb = apb.getPurchaseInvoiceByID(id);
			if (pb.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");// ;mapping.findForward("lock");
			}

			apb.delPurchaseInvoice(id);
			AppPurchaseInvoiceDetail apid = new AppPurchaseInvoiceDetail();
			apid.delPurchaseInvoiceDetailByPiID(id);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 2, "采购管理>>删除采购发票,编号：" + id, pb);

			return mapping.findForward("delresult");

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return mapping.getInputForward();
	}
}
