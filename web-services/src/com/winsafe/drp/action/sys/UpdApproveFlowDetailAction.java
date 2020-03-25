package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppApproveFlowDetail;
import com.winsafe.drp.dao.ApproveFlowDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class UpdApproveFlowDetailAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			Long id = Long.valueOf(request.getParameter("id"));
			AppApproveFlowDetail aaf = new AppApproveFlowDetail();
			ApproveFlowDetail afd= aaf.getApproveFlowDetailByID(id);
			afd.setApproveid(Integer.valueOf(request.getParameter("approveid")));
			afd.setActid(Integer.valueOf(request.getParameter("actid")));
			afd.setApproveorder(Integer.valueOf(request.getParameter("approveorder")));	

			aaf.updApproveFlowDetail(afd);
			
			
			request.setAttribute("result", "databases.upd.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      DBUserLog.addUserLog(userid,"修改审阅流详情");
			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mapping.findForward("updResult");
	}
}
