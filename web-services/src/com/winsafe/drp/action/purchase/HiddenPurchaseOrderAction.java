package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class HiddenPurchaseOrderAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String ppid = request.getParameter("ID");
			Integer iscomplete = 1;
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			AppPurchaseOrder app = new AppPurchaseOrder();
			app.updIsEndCase(ppid, userid, iscomplete);

			
			request.setAttribute("result", "databases.upd.success");
			//DBUserLog.addUserLog(userid, "结案采购订单");
			
			return mapping.findForward("hidden");
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			//
			
		}
		return new ActionForward(mapping.getInput());
	}
}
