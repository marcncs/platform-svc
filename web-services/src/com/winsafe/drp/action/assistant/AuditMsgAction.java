package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMsg;
import com.winsafe.drp.dao.Msg;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class AuditMsgAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		try {
			int id = Integer.valueOf(request.getParameter("id"));
			AppMsg apb = new AppMsg();
			Msg msg = apb.getMsgById(id);

			if (msg.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (msg.getIsdeal() == 1) {
				request.setAttribute("result", "对不起，该记录已完成，不能进行此操作!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			apb.updIsAudit(id, userid, 1);
			
		

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 12, "复核短信信息,编号：" + id);// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
