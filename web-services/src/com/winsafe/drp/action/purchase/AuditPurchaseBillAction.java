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
import com.winsafe.hbm.util.DateUtil;

public class AuditPurchaseBillAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String pbid = request.getParameter("PBID");
			AppPurchaseBill apb = new AppPurchaseBill();
			PurchaseBill pb = apb.getPurchaseBillByID(pbid);

			if (pb.getIsaudit() == 1) {
				String result = "databases.record.audit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			pb.setAuditdate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			pb.setIsaudit(1);
			pb.setAuditid(userid);

			apb.updPurchaseBillByID(pb);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid,2 ,"采购管理>>复核采购单,编号：" + pbid);

			return mapping.findForward("audit");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
