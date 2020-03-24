package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWorkReport;
import com.winsafe.drp.dao.AppWorkReportApprove;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class ApproveWorkReportAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		String approvecontent = request.getParameter("approvecontent");
		String reportid = request.getParameter("reportid");
		String status = request.getParameter("status");

		int approve = 1;
		if (status != null && status != "")
			approve = 0;

		String approvedate = DateUtil.getCurrentDateTime();

		try {
			AppWorkReportApprove awre = new AppWorkReportApprove();
			AppWorkReport awr = new AppWorkReport();
			approvecontent = approvecontent== null?"":approvecontent;
			awre.addApproveContent(approvecontent, approve, approvedate,
					Integer.valueOf(reportid), userid);

			if (status != null) {
				awr.updIsApprove(reportid, 1);
			} else {
				int count = awre.isApprove(Integer.valueOf(reportid), 0);
				if (count <= 0) {
					awr.updIsApprove(reportid, 2);
				} else {
					awr.updIsApprove(reportid, 1);
				}
			}

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 0, "审阅工作报告,编号：" + reportid);
			return mapping.findForward("approve");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return new ActionForward(mapping.getInput());
	}
}
