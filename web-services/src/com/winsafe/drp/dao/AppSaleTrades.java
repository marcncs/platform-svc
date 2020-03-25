package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSaleTrades {

	public List getSaleTrades(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = "from SaleTrades as st " + pWhereClause
				+ " order by st.makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public void addSaleTrades(SaleTrades dpd) throws Exception {		
		EntityManager.save(dpd);
		
	}

	public void updSaleTrades(SaleTrades dpd) throws Exception {		
		EntityManager.update(dpd);
		
	}

	public void delSaleTrades(String id) throws Exception {		
		String sql = "delete from sale_trades where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updTakeStatus(String id, int takestatus) throws Exception {		
		String sql = "update sale_trades set takestatus="+takestatus+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	public SaleTrades getSaleTradesByID(String id) throws Exception {
		String sql = " from SaleTrades where id='" + id + "'";
		return (SaleTrades) EntityManager.find(sql);
	}

	
	public void updIsAudit(String id, int userid, int audit)throws Exception {		
		String sql = "update sale_trades set isaudit=" + audit + ",auditid="
				+ userid + ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updIsAuditByTime(String id, int userid, int audit)throws Exception {		
		String sql = "update sale_trades set isaudit=" + audit + ",auditid="
				+ userid + ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void BlankoutSaleTrades(String id, Long userid) throws Exception {
		
		String sql = "update sale_trades set isblankout=1,blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsEndCase(String id, int userid, int audit)
			throws Exception {		
		String sql = "update sale_trades set isendcase=" + audit + ",endcaseid="
				+ userid + ",endcasedate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public List getSaleTradesCustomerTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select cid as id,cname as name,tel as tel,count(id) as count from Sale_Trades "
				+ whereSql + " group by tel,cid,cname";
		return PageQuery.jdbcSqlserverQuery(request, hql, pagesize);
	}

	public List getSaleTradesCustomerTotal(String whereSql)
			throws HibernateException, SQLException {
		String hql = "select cid as id,cname as name,tel as tel,count(id) as count from Sale_Trades "
				+ whereSql + " group by tel,cid,cname";
		return EntityManager.jdbcquery(hql);
	}

	public double getSaleTradesCustomerTotalSum(String whereSql) {
		String sql = "select count(id) from SaleTrades so " + whereSql;
		return EntityManager.getdoubleSum(sql);
	}
	
	public double getSaleTradesProductTotalSum(String whereSql) {
		String sql = "select sum(sod.quantity) from SaleTrades so ,SaleTradesDetail as sod " + whereSql;
		return EntityManager.getdoubleSum(sql);
	}
	public List getSaleTradesProductTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String sql = "select so.makeorganid,productid as productid,productname as productname,specmode as specmode,unitid as unitid,batch as batch,sum(quantity) as quantity "
			+ " from sale_trades_detail as sod ,sale_trades as so "
			+ whereSql
			+ " group by so.makeorganid,productid,productname,specmode,unitid ,batch";
		System.out.println(sql);
	return PageQuery.jdbcSqlserverQuery(request, "productid", sql, pagesize);
	}
	
	public List getSaleTradesProductTotal(String whereSql) throws HibernateException, SQLException {
		String sql = "select so.makeorganid,productid as productid,productname as productname,specmode as specmode,unitid as unitid,batch as batch,sum(quantity) as quantity "
			+ " from sale_trades_detail as sod ,sale_trades as so "
			+ whereSql
			+ " group by so.makeorganid,productid,productname,specmode,unitid,batch";

		return EntityManager.jdbcquery(sql);
	}

	public List<SaleTrades> getSaleTrades(String whereSql) {
		String sql = "from SaleTrades as st " + whereSql
		+ " order by st.makedate desc";
		return EntityManager.getAllByHql(sql);
	}
}
