package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWorkReport;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WorkReportView;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListWorkReportAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		int pagesize = 10;
		initdata(request);
		try {
			String userCondition = " wr.id=wra.reportid and wra.approveid="
					+ userid;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from work_report where "+userCondition;
			String[] tablename = { "WorkReport" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" ReferDate"); 
			String blur = DbUtil.getBlur(map, tmpMap, " ReportContent"); 
			whereSql = whereSql + blur + timeCondition + userCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppWorkReport awr = new AppWorkReport();
			List list = awr.searchWorkReport(request, pagesize, whereSql);

			WorkReportView workReport = null;
			List<WorkReportView> listview = new ArrayList<WorkReportView>();
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				workReport = new WorkReportView();
				workReport.setId(Integer.valueOf(obj[0].toString()));
				workReport.setReportsort(Integer.valueOf(obj[1].toString()));
				workReport.setReportcontent(obj[2].toString());
				workReport.setIsrefer(Integer.valueOf(obj[3].toString()));
				workReport.setApprovestatus(Integer.valueOf(obj[4].toString()));
				workReport.setMakeorganid(obj[5].toString());
				workReport.setMakeid(Integer.valueOf(obj[6].toString()));
				workReport.setMakedate((Date) obj[7]);
				workReport.setApprove(Integer.valueOf(obj[8].toString()));
				listview.add(workReport);
			}

			request.setAttribute("arls", listview);

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>列表工作报告");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
