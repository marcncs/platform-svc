package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditMoveApplyAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		super.initdata(request);try{
			String aaid = request.getParameter("AAID");
			AppMoveApply api = new AppMoveApply();
			// AppAlterMoveApplyDetail asamd = new AppAlterMoveApplyDetail();
			MoveApply pi = api.getMoveApplyByID(aaid);

			if (pi.getIsaudit() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (pi.getIsratify() == 1) {
				request.setAttribute("result", "对不起，该单据已经批准，不能取消！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			pi.setIsaudit(0);
			pi.setAuditid(null);
			pi.setAuditdate(null);

			api.updMoveApply(pi);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 4, "转仓申请审核>>取消复核转仓申请单,编号：" + aaid);

			return mapping.findForward("noaudit");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
