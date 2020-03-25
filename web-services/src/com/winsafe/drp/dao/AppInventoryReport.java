package com.winsafe.drp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppInventoryReport {

	public void saveInventoryReport(InventoryReport ir) throws Exception {
		EntityManager.save(ir);
	}

	public void updInventoryReport(InventoryReport ir) throws Exception {
		EntityManager.update(ir);
	}

	public List<InventoryReport> getInventoryByDate(String date)
			throws Exception {
		String sql = " from InventoryReport where recorddate = to_date('"
				+ date + "','yyyy-mm-dd hh24:mi:ss')";
		return EntityManager.getAllByHql(sql);
	}

	public InventoryReport getInventoryReportSwb(String warehouseid,
			String productid, String batch) throws Exception {
		String sql = "from InventoryReport where warehouseid = '" + warehouseid
				+ "' and productid ='" + productid + "' and batch = '" + batch
				+ "'";
		List list = EntityManager.getAllByHql(sql);
		return list == null || list.size() == 0 ? null : (InventoryReport) list
				.get(0);
	}

	public List getInventoryReportByPageNoBatch(HttpServletRequest request,
			int pagesize, String whereSql, String orderby) throws Exception {
		String sql = "select ps.sortname,  s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode ,S.RECORDDATE ,"
				+ " sum(s.cyclebalancequantity) quantity "
				+ " from inventory_report s, product p, product_struct ps "
				+ whereSql
				+ " group by ps.sortname, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode,S.RECORDDATE";
		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery
					.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
					pagesize);
		}
	}

	public List getInventoryReportByPage(HttpServletRequest request,
			int pagesize, String whereSql, String orderby) throws Exception {
		String sql = "select ps.sortname, s.warehouseid, s.batch, s.productid, p.productname,p.specmode,p.countunit, p.nccode,S.RECORDDATE ,"
				+ " sum(s.cyclebalancequantity) quantity "
				+ " from inventory_report s, product p, product_struct ps "
				+ whereSql
				+ " group by ps.sortname, s.warehouseid, s.batch, s.productid, p.productname,p.specmode,p.countunit, p.nccode,S.RECORDDATE";
		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery
					.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
					pagesize);
		}
	}

	public List getInventoryReport(String whereSql) throws Exception {
		String sql = "select ps.sortname, s.warehouseid, s.batch, s.productid, p.productname,p.specmode,p.countunit, p.nccode,S.RECORDDATE ,"
				+ " sum(s.cyclebalancequantity) quantity "
				+ " from inventory_report s, product p, product_struct ps "
				+ whereSql
				+ " group by ps.sortname, s.warehouseid, s.batch, s.productid, p.productname,p.specmode,p.countunit, p.nccode,S.RECORDDATE";
		return EntityManager.jdbcquery(sql);
	}

	public List getInventoryReportNoBatch(String whereSql) throws Exception {
		String sql = "select ps.sortname,  s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode ,S.RECORDDATE ,"
				+ " sum(s.cyclebalancequantity) quantity "
				+ " from inventory_report s, product p, product_struct ps "
				+ whereSql
				+ " group by ps.sortname, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode,S.RECORDDATE";
		return EntityManager.jdbcquery(sql);
	}

	public List getCurrentInventoryReportByPageNoBatch(
			HttpServletRequest request, int pagesize, String whereSql,
			String orderby) throws Exception {
		String sql = "select ps.sortname, sysdate RECORDDATE, s.warehouseid,s.productid,p.productname,p.specmode,p.countunit,p.nccode, sum((STOCKPILE+PREPAREOUT)) quantity "
				+ " from product_stockpile s, product p, product_struct ps  "
				+ whereSql
				+ " group by ps.sortname, warehouseid,productid,productname,p.specmode,p.countunit,p.nccode, sysdate";

		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery
					.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
					pagesize);
		}
	}

	public List getCurrentInventoryReportByPage(HttpServletRequest request,
			int pagesize, String whereSql, String orderby) throws Exception {
		String sql = "select ps.sortname, sysdate RECORDDATE, s.batch, s.warehouseid,s.productid,p.productname,p.specmode,p.countunit,p.nccode, sum((STOCKPILE+PREPAREOUT)) quantity "
				+ " from product_stockpile s, product p, product_struct ps "
				+ whereSql
				+ " group by ps.sortname, s.batch, warehouseid,productid,productname,p.specmode,p.countunit,p.nccode, sysdate";
		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery
					.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
					pagesize);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Map> getOutQuantityDaily(HttpServletRequest request,
			int pagesize, String whereSql, String orderby, int days)
			throws Exception {
		String sql = "SELECT ps.sortname, S.PRODUCTID, S.WAREHOUSEID, P.PRODUCTNAME, P.SPECMODE, p.countunit, p.nccode, "
				+ " round((SUM(S.CYCLEBALANCEQUANTITY)/"
				+ days
				+ "),2) avgstockpile "
				+ " FROM INVENTORY_REPORT S, PRODUCT P ,PRODUCT_STRUCT PS "
				+ whereSql
				+ " GROUP BY ps.sortname, S.PRODUCTID,S.WAREHOUSEID, P.PRODUCTNAME, P.SPECMODE, p.countunit, p.nccode ";
		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery
					.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
					pagesize);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> getOutQuantityDailySum(String whereSql, int days)
			throws Exception {
		String sql = "SELECT  "
				+ " SUM(round((SUM(S.CYCLEBALANCEQUANTITY)/"
				+ days
				+ "),2)) avgstockpile "
				+ " FROM INVENTORY_REPORT S, PRODUCT P ,PRODUCT_STRUCT PS "
				+ whereSql
				+ " GROUP BY ps.sortname, S.PRODUCTID,S.WAREHOUSEID, P.PRODUCTNAME, P.SPECMODE, p.countunit, p.nccode ";
		return EntityManager.jdbcquery(sql);
	}

	public void addHistoryReport() throws Exception {
		String sql = "INSERT INTO PRODUCT_STOCKPILE_HISTORY "
			+ " SELECT PRODUCT_STOCKPILE_HISTORY_SEQ.nextval, PRODUCTID, COUNTUNIT, WAREHOUSEID, STOCKPILE, SYSDATE"
			+ " FROM ("
			+ " SELECT PRODUCTID , MAX(COUNTUNIT) as COUNTUNIT, WAREHOUSEID,SUM(STOCKPILE) as STOCKPILE from PRODUCT_STOCKPILE_ALL"
			+ " GROUP BY PRODUCTID, WAREHOUSEID "
            + " ) psa";
		EntityManager.executeUpdate(sql);
	}
	
	public void getMaxDate() throws Exception {
		String sql = "INSERT INTO PRODUCT_STOCKPILE_HISTORY "
			+ " SELECT PRODUCT_STOCKPILE_HISTORY_SEQ.nextval, PRODUCTID, COUNTUNIT, WAREHOUSEID, STOCKPILE, SYSDATE"
			+ " FROM ("
			+ " SELECT PRODUCTID , MAX(COUNTUNIT) as COUNTUNIT, WAREHOUSEID,SUM(STOCKPILE) as STOCKPILE from PRODUCT_STOCKPILE_ALL"
			+ " GROUP BY PRODUCTID, WAREHOUSEID "
            + " ) psa";
		EntityManager.executeUpdate(sql);
	}
	
	public Date getEarlierHistoryInventoryDate(String date) throws Exception {
		String sql = "select max(MAKEDATE) as historyDate from PRODUCT_STOCKPILE_HISTORY where MAKEDATE < to_date('"+date+"','yyyy-MM-dd hh24:mi:ss')";
		return (Date)EntityManager.queryResultByNativeSql(sql);
	}
	
	public Date getLaterHistoryInventoryDate(String date) throws Exception {
		String sql = "select max(MAKEDATE) as historyDate from PRODUCT_STOCKPILE_HISTORY where MAKEDATE > to_date('"+date+"','yyyy-MM-dd hh24:mi:ss')";
		return (Date)EntityManager.queryResultByNativeSql(sql);
	}
	


}
