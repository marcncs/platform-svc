package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppApproveFlow;
import com.winsafe.drp.dao.ApproveFlow;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class UpdApproveFlowAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			ApproveFlow af= new ApproveFlow();
			af.setId(request.getParameter("id"));
			af.setFlowname(request.getParameter("flowname"));
			af.setMemo(request.getParameter("memo"));

			AppApproveFlow aaf = new AppApproveFlow();

			aaf.updApproveFlow(af);
			
			
			request.setAttribute("result", "databases.upd.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      DBUserLog.addUserLog(userid,"修改审阅流");
			
			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			//
		}
		return mapping.findForward("updResult");
	}
}
