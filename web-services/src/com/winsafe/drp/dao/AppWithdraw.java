package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppWithdraw {

	public List getWithdraw(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = "from Withdraw as w " + pWhereClause
				+ " order by w.makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public void addWithdraw(Object dpd) throws Exception {
		EntityManager.save(dpd);
	}

	public void updWithdraw(Withdraw dpd) throws Exception {

		EntityManager.update(dpd);

	}

	public void delWithdraw(String id) throws Exception {

		String sql = "delete from withdraw where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public Withdraw getWithdrawByID(String id) throws Exception {
		String sql = " from Withdraw where id='" + id + "'";
		return (Withdraw) EntityManager.find(sql);
	}

	
	public void updIsAudit(String id, int userid, int audit)
			throws Exception {
		String sql = "update withdraw set isaudit=" + audit + ",auditid="
				+ userid + ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	
	public void BlankoutWithdraw(String id, int userid) throws Exception {
		String sql = "update withdraw set isblankout=1,blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public double getWithdrawTotal(String pWhereClause) throws Exception {
		String sql = "select sum(w.totalsum) from Withdraw as w "
				+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}

	public List getWithdrawCustomerTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select cid as id,cname as name,cmobile as mobile,sum(totalsum) as totalsum from withdraw "
				+ whereSql + " group by cmobile,cid,cname";
		return PageQuery.jdbcSqlserverQuery(request, hql, pagesize);
	}

	public List getWithdrawCustomerTotal(String whereSql)
			throws HibernateException, SQLException {
		String hql = "select cid as id,cname as name,cmobile as mobile,sum(totalsum) as totalsum from withdraw "
				+ whereSql + " group by cmobile,cid,cname";
		return EntityManager.jdbcquery(hql);
	}

	public double getWithdrawCustomerTotalSum(String whereSql) {
		String sql = "select sum(so.totalsum) from Withdraw so " + whereSql;
		return EntityManager.getdoubleSum(sql);
	}

	public List getWithdrawBillTotal(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql = "select id,makedate,cid,cname,cmobile,makeorganid,totalsum from withdraw so "
			+ whereSql;

	return PageQuery.jdbcSqlserverQuery(request, hql, pagesize);
	}

	public List getWithdrawBillTotal(String whereSql) throws HibernateException, SQLException {
		String hql = "select id,makedate,cid,cname,cmobile,makeorganid,totalsum from withdraw so "+ whereSql;
		return EntityManager.jdbcquery(hql);
	}

	public List getWithdrawProductTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String sql = "select so.makeorganid,productid as productid,productname as productname,specmode as specmode,unitid as unitid,sum(quantity) as quantity ,sum(subsum) as subsum "
			+ " from withdraw_detail as sod ,withdraw as so "
			+ whereSql
			+ " group by so.makeorganid,productid,productname,specmode,unitid ";
	return PageQuery.jdbcSqlserverQuery(request, "productid", sql, pagesize);
	}

	public List getWithdrawProductTotalSum(String whereSql) throws HibernateException, SQLException {
		String sql = "select sum(sod.quantity) as allqt, sum(sod.subsum) as allsubsum "
			+ " from withdraw_detail as sod ,withdraw as so "
			+ whereSql;
	return EntityManager.jdbcquery(sql);
	}

	public List getWithdrawProductTotal(String whereSql) throws HibernateException, SQLException {
		String sql = "select so.makeorganid, productid as productid,productname as productname,specmode as specmode,unitid as unitid,sum(quantity) as quantity ,sum(subsum) as subsum "
			+ " from withdraw_detail as sod ,withdraw as so "
			+ whereSql
			+ " group by so.makeorganid,productid,productname,specmode,unitid ";

		return EntityManager.jdbcquery(sql);
	}

	public List<Withdraw> getWithdraw(String whereSql) {
		String sql = "from Withdraw as w " + whereSql
		+ " order by w.makedate desc";
		return EntityManager.getAllByHql(sql);
	}

}
