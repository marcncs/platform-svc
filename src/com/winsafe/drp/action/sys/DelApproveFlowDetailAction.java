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

public class DelApproveFlowDetailAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			Long id = Long.valueOf(request.getParameter("ID"));
			AppApproveFlowDetail aaf = new AppApproveFlowDetail();
			ApproveFlowDetail afd = aaf.getApproveFlowDetailByID(id);
			aaf.delApproveFlowDetail(id);
			
			request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      DBUserLog.addUserLog(userid, 11, "基础设置>>删除审阅流程详情,编号:"+id, afd);
			return mapping.findForward("delResult");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mapping.findForward("");
	}
}
