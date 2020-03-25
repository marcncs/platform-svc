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

public class CancelRatifyPurchaseBillAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String pbid = request.getParameter("PBID");
			AppPurchaseBill apb = new AppPurchaseBill();
			PurchaseBill pb = apb.getPurchaseBillByID(pbid);

			if (pb.getIsratify() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (!String.valueOf(pb.getRatifyid()).contains(userid.toString())) {
				String result = "databases.record.cancelaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
		
			pb.setRatifydate(null);
			pb.setRatifyid(null);
			pb.setIsratify(0);
			apb.updPurchaseBillByID(pb);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid,2,"采购管理>>取消采购单批准,编号："+pbid);

			return mapping.findForward("noratify");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
