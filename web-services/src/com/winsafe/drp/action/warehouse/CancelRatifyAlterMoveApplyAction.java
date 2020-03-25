package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelRatifyAlterMoveApplyAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		super.initdata(request);try{
			String aaid = request.getParameter("AAID");
			AppAlterMoveApply api = new AppAlterMoveApply();
			// AppAlterMoveApplyDetail asamd = new AppAlterMoveApplyDetail();
			AlterMoveApply pi = api.getAlterMoveApplyByID(aaid);

			if (pi.getIsratify() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (pi.getIstrans() == 1) {
				String result = "databases.record.alreadycompletenocancel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			pi.setRatifyid(null);
			pi.setIsratify(0);
			pi.setRatifydate(null);

			api.updAlterMoveApply(pi);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 4,"订购申请审核>>取消复核订购申请单,编号：" + aaid);

			return mapping.findForward("noaudit");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
