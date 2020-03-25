package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppIdcodeReset;
import com.winsafe.drp.dao.AppIdcodeResetDetail;
import com.winsafe.drp.dao.IdcodeReset;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelIdcodeDetailAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();

			String id = request.getParameter("ID");

			AppIdcodeReset ar = new AppIdcodeReset();
			IdcodeReset ir = ar.getIdcodeResetById(id);
			if (ir.getIsaudit() == 1) {
				request.setAttribute("result","databases.record.approvestatus");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			ar.delIdcodeReset(id);
			AppIdcodeResetDetail apird = new AppIdcodeResetDetail();
			apird.delIdcodeResetDetailByIrid(id);
			AppIdcodeDetail apid = new AppIdcodeDetail();
			apid.delIdcodeDetailByBillid(id);

			request.setAttribute("result", "databases.del.success");
//			DBUserLog.addUserLog(userid, "删除序号重置");// 日志

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
