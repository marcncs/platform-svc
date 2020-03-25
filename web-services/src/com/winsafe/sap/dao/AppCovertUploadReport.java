package com.winsafe.sap.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;



import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.CovertUploadReport;
@SuppressWarnings("unchecked")
public class AppCovertUploadReport {
	
	public void addCovertUploadReports(List<CovertUploadReport> reports) throws Exception {		
		EntityManager.batchSave(reports);
	}
	public void addCovertUploadReport(CovertUploadReport report) throws Exception {		
		EntityManager.save(report);
	}

	public List getCovertUploadReports(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql=" from CovertUploadReport as o "+whereSql +" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getCovertUploadReportsByHql(HttpServletRequest request,
			int pagesize, String hql) throws Exception {
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getCovertUploadReportsBySql(HttpServletRequest request,
			int pagesize, String sql) throws Exception {
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}
	public CovertUploadReport loadCovertUploadReportById(Long id) throws Exception  {
		return (CovertUploadReport)EntityManager.load(CovertUploadReport.class, id);
	}
	/**
	 * 导出查询
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List getCovertUploadReportsBySql(String sql) throws Exception {
		return EntityManager.jdbcquery(sql);
	}
	
	/**
	 * 导出查询
	 * @param request
	 * @param pagesize
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */
	public List getCovertUploadReports(String whereSql) throws Exception {
		String hql=" from CovertUploadReport as o "+whereSql +" order by o.id desc";
		return EntityManager.getAllByHql(hql);
	}
}

