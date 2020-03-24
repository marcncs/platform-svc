package com.winsafe.drp.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseSalesReport {

	public void savePurchaseSalesReport(PurchaseSalesReport psr)
			throws Exception {
		EntityManager.save(psr);
	}

	public List getPurchaseSalesReportByPageNoBatch(HttpServletRequest request,
			int pagesize, String whereSql, String orderby) throws Exception {
		String sql = "select ps.sortname, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode ,S.RECORDDATE ,"
				+ " sum(s.cycleoutquantity) outquantity ,sum(s.cycleinquantity) inquantity "
				+ " from purchase_sales_report s, product p, product_struct ps  "
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

	public List getSalesDailyAvgReportByPageNoBatch(HttpServletRequest request,
			int pagesize, String whereSql, String orderby, int avgDays)
			throws Exception {
		String sql = "select ps.sortname, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode, "
				+ " sum(s.cycleoutquantity) outquantity , round(sum(s.cycleoutquantity)/"
				+ avgDays
				+ ",2) sda"
				+ " from purchase_sales_report s, product p, product_struct ps  "
				+ whereSql
				+ " group by ps.sortname, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode";
		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery
					.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
					pagesize);
		}
	}

	public List getSalesDailyAvgReportNoBatch(String whereSql, int avgDays)
			throws Exception {
		String sql = "select ps.sortname, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode, "
				+ " sum(s.cycleoutquantity) outquantity , round(sum(s.cycleoutquantity)/"
				+ avgDays
				+ ",2) sda"
				+ " from purchase_sales_report s, product p, product_struct ps  "
				+ whereSql
				+ " group by ps.sortname, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode";
		return EntityManager.jdbcquery(sql);
	}

	public List getSalesDailyAverageNoBatch(String whereSql) throws Exception {
		String sql = "select sum(s.cycleoutquantity) outquantity  "
				+ " from purchase_sales_report s, product p, product_struct ps  "
				+ whereSql;
		return EntityManager.jdbcquery(sql);

	}

	public List getPurchaseSalesReportByPage(HttpServletRequest request,
			int pagesize, String whereSql, String orderby) throws Exception {
		String sql = "select ps.sortname, s.warehouseid, s.batch, s.productid, p.productname,p.specmode,p.countunit, p.nccode,S.RECORDDATE ,"
				+ " sum(s.cycleoutquantity) outquantity, sum(s.cycleinquantity) inquantity "
				+ " from purchase_sales_report s, product p, product_struct ps "
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

	public List getPurchaseSalesReport(String whereSql) throws Exception {
		String sql = "select ps.sortname, s.warehouseid, s.batch, s.productid, p.productname,p.specmode,p.countunit, p.nccode,S.RECORDDATE ,"
				+ " sum(s.cycleoutquantity) outquantity, sum(s.cycleinquantity) inquantity "
				+ " from purchase_sales_report s, product p, product_struct ps "
				+ whereSql
				+ " group by ps.sortname, s.warehouseid, s.batch, s.productid, p.productname,p.specmode,p.countunit, p.nccode,S.RECORDDATE";
		return EntityManager.jdbcquery(sql);

	}

	public List getPurchaseSalesReportNoBatch(String whereSql) throws Exception {
		String sql = "select ps.sortname, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode ,S.RECORDDATE ,"
				+ " sum(s.cycleoutquantity) outquantity ,sum(s.cycleinquantity) inquantity "
				+ " from purchase_sales_report s, product p, product_struct ps  "
				+ whereSql
				+ " group by ps.sortname, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode,S.RECORDDATE";
		return EntityManager.jdbcquery(sql);

	}

	public List getStockPileDaysByPage(HttpServletRequest request,
			int pagesize, String whereSql, String orderby, int days) throws Exception {
		String sql="select k.SORTNAME,k.WAREHOUSEID,k.PRODUCTID,k.PRODUCTNAME,k.SPECMODE,k.COUNTUNIT,k.NCCODE,k.STOCKPILE,k.PREPAREOUT,k.quantity,i.sda,round((k.quantity/ decode(i.sda,0,'',null,'',i.sda)),2) days " +
				" from (select PS.SORTNAME ,S.WAREHOUSEID ,S.PRODUCTID ,P.PRODUCTNAME,P.SPECMODE,P.COUNTUNIT,P.NCCODE, sum(s.STOCKPILE) STOCKPILE,sum(s.PREPAREOUT) PREPAREOUT, sum((s.STOCKPILE+s.PREPAREOUT)) quantity" +
				" from product_stockpile_all s, product p, product_struct ps where s.PRODUCTID=p.id and p.PSID= ps.STRUCTCODE " +
				" group by  PS.SORTNAME ,S.WAREHOUSEID ,S.PRODUCTID ,P.PRODUCTNAME ,P.SPECMODE ,P.COUNTUNIT ,P.NCCODE) k" +
				" left join " +
				" (SELECT PS.SORTNAME ,S.WAREHOUSEID,S.PRODUCTID ,P.PRODUCTNAME ,P.SPECMODE  ,P.COUNTUNIT ,P.NCCODE ,SUM(S.CYCLEOUTQUANTITY) OUTQUANTITY , " +
				" ROUND(SUM(S.CYCLEOUTQUANTITY) /" + days +	" ,2) SDA " +
				" FROM PURCHASE_SALES_REPORT S ,PRODUCT P ,PRODUCT_STRUCT PS " +
				whereSql +
				" GROUP BY PS.SORTNAME ,S.WAREHOUSEID  ,S.PRODUCTID ,P.PRODUCTNAME,P.SPECMODE ,P.COUNTUNIT,P.NCCODE) i" +
				" on i.warehouseid= k.warehouseid and i.productid = k.productid";
		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery
					.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
					pagesize);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> getSalesCount(String whereSql) throws Exception {
		String sql = "SELECT ps.sortname, S.PRODUCTID, S.WAREHOUSEID, P.PRODUCTNAME, P.SPECMODE, p.countunit, p.nccode, " +
				" SUM(S.CYCLEOUTQUANTITY) SALESCOUNT " +
				" FROM PURCHASE_SALES_REPORT S, PRODUCT P, PRODUCT_STRUCT PS " +
				whereSql +
				" GROUP BY ps.sortname, S.PRODUCTID,S.WAREHOUSEID, P.PRODUCTNAME, P.SPECMODE, p.countunit, p.nccode ";
		
		return EntityManager.jdbcquery(sql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> getSalesSumCount(String whereSql) throws Exception {
		String sql = "SELECT " +
				" SUM(SUM(S.CYCLEOUTQUANTITY)) SALESCOUNT " +
				" FROM PURCHASE_SALES_REPORT S, PRODUCT P, PRODUCT_STRUCT PS " +
				whereSql +
				" GROUP BY ps.sortname, S.PRODUCTID,S.WAREHOUSEID, P.PRODUCTNAME, P.SPECMODE, p.countunit, p.nccode ";
		
		return EntityManager.jdbcquery(sql);
	}
	
	

}
