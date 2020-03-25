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

public class DelPurchaseBillAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");
		try {
			AppPurchaseBill apb = new AppPurchaseBill();
			PurchaseBill pb = apb.getPurchaseBillByID(id);
			if (pb.getIsaudit() == 1) {
				String result = "databases.record.approvestatus";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			apb.delPurchaseBill(id);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid,2,"采购管理>>删除采购单,编号："+id,pb);

			return mapping.findForward("delresult");

		} catch (Exception e) {

			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
