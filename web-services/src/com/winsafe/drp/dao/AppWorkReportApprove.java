package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppWorkReportApprove {

	
	public void addWorkReportApprove(WorkReportApprove wra) throws Exception {
		EntityManager.save(wra);
	}

	public void addWorkReportApprove(WorkReportApprove[] wra) throws Exception {
		EntityManager.save(wra);
	}

	
	public List getWorkReportApprove(Integer id) throws Exception {

		String sql = "select wra.approve,wra.approveid,wra.approvecontent from WorkReportApprove as wra where wra.reportid="
				+ id;
		return EntityManager.getAllByHql(sql);
	}

	
	public void addApproveContent(String approvecontent, int approve,
			String approvedate, Integer reportid, Integer userid)
			throws Exception {

		String sql = "update work_report_approve set approvecontent='"
				+ approvecontent + "', approve=" + approve + ",approvedate='"
				+ approvedate + "' where reportid='" + reportid
				+ "' and approveid=" + userid;
		EntityManager.updateOrdelete(sql);

	}

	
	public void delWorkReportApproveByReportID(Integer reportid)
			throws Exception {

		String sql = "delete from work_report_approve where reportid="
				+ reportid;
		EntityManager.updateOrdelete(sql);

	}

	
	public int isApprove(Integer reportid, Integer appover) {
		String sql = "select count(*) from WorkReportApprove where reportid ="
				+ reportid + " and approve = " + appover;
		return EntityManager.getRecordCount(sql);

	}

	public List getWorkReportUsers(Integer id) {
		String sql = " select te.approveid from WorkReportApprove as te where te.reportid="
				+ id;
		return EntityManager.getAllByHql(sql);

	}

}
