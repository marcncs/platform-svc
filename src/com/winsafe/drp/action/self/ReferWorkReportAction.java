package com.winsafe.drp.action.self;

import java.util.StringTokenizer;

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
import com.winsafe.drp.dao.WorkReportApprove;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class ReferWorkReportAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MakeCode mc = new MakeCode();
		String strreportid = request.getParameter("reportid");
		Integer reportid = Integer.valueOf(strreportid);
		String strspeed = request.getParameter("speedstr");
		int count = Integer.parseInt(request.getParameter("uscount"));
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			AppWorkReportApprove awre = new AppWorkReportApprove();
			awre.delWorkReportApproveByReportID(reportid);

			StringTokenizer st = new StringTokenizer(strspeed, ",");
			WorkReportApprove wre = null;
			for (int p = 0; p < count; p++) {
				Integer newuserid = Integer.valueOf(st.nextToken().trim());
				if (!userid.equals(newuserid)) {
					wre = new WorkReportApprove();
					wre.setId(Integer.valueOf(mc.getExcIDByRandomTableName(
							"work_report_approve", 0, "")));
					wre.setReportid(reportid);
					wre.setApproveid(newuserid);
					wre.setApprovecontent("");
					wre.setApprove(Integer.valueOf(0));
					awre.addWorkReportApprove(wre);
				}

			}
			wre = new WorkReportApprove();
			wre.setId(Integer.valueOf(mc.getExcIDByRandomTableName(
					"work_report_approve", 0, "")));
			wre.setReportid(reportid);
			wre.setApproveid(userid);
			wre.setApprovecontent("");
			wre.setApprove(0);
			awre.addWorkReportApprove(wre);
			
			
			AppWorkReport awr = new AppWorkReport();
			awr.updIsRefer(reportid);

			request.setAttribute("result", "databases.refer.success");

			DBUserLog.addUserLog(userid,0,"我的办公桌>>提交工作报告");

			return mapping.findForward("refer");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
