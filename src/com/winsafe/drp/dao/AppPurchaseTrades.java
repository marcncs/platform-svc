package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseTrades {

	public List<PurchaseTrades> getPurchaseTrades(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String hql = "from PurchaseTrades as dp " + pWhereClause
				+ " order by dp.makedate desc";
		return PageQuery.hbmQuery(request, hql,pagesize);
	}

	public void addPurchaseTrades(PurchaseTrades dpd) throws Exception {		
		EntityManager.save(dpd);		
	}

	public void updPurchaseTrades(PurchaseTrades dpd) throws Exception {		
		EntityManager.update(dpd);		
	}

	public void delPurchaseTrades(String id) throws Exception {		
		String sql = "delete from purchase_trades where id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public void updTakeStatus(String id, int takestatus) throws Exception {		
		String sql = "update purchase_trades set takestatus="+takestatus+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public PurchaseTrades getPurchaseTradesByID(String id) throws Exception {
		String sql = " from PurchaseTrades where id='" + id + "'";
		return (PurchaseTrades) EntityManager.find(sql);
	}

	
	public void updIsAudit(String id, Integer userid, Integer audit)
			throws Exception {		
		String sql = "update purchase_trades set isaudit=" + audit + ",auditid="
				+ userid + ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}


	
	public void updIsReceive(String id, Integer userid, Integer audit)
			throws Exception {		
		String sql = "update purchase_trades set isreceive=" + audit + ",receiveid="
				+ userid + ",receivedate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}

	public List getPurchaseProviderTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select provideid,providename,productid,productname,sum(quantity) as quantity " +
		 " from Purchase_Trades pt , Purchase_Trades_Detail ptd " + whereSql + 
		" group by provideid,providename,productid,productname";
		return PageQuery.jdbcSqlserverQuery(request,"provideid", hql, pagesize);
	}

	public double getTotalSum(String whereSql) {
		String hql = "select sum(ptd.quantity) as quantity " +
		 " from PurchaseTrades pt , PurchaseTradesDetail ptd " + whereSql;
		return EntityManager.getdoubleSum(hql);
	}

	public List getPurchaseProviderTotal(String whereSql) throws HibernateException, SQLException {
		String hql = "select provideid,providename,productid,productname,sum(quantity) as quantity " +
		 " from Purchase_Trades pt , Purchase_Trades_Detail ptd " + whereSql + 
		" group by provideid,providename,productid,productname";
		return EntityManager.jdbcquery(hql);
	}

	public List getPurchaseTradesBill(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql = "select pt.id,productid,productname,sum(quantity) as quantity " +
		 " from Purchase_Trades pt , Purchase_Trades_Detail ptd " + whereSql + 
		" group by pt.id,productid,productname";
		return PageQuery.jdbcSqlserverQuery(request, hql, pagesize);
	}
	public List getPurchaseTradesBill(
			String whereSql) throws Exception {
		String hql = "select pt.id,productid,productname,sum(quantity) as quantity " +
		 " from Purchase_Trades pt , Purchase_Trades_Detail ptd " + whereSql + 
		" group by pt.id,productid,productname";
		return EntityManager.jdbcquery(hql);
	}

	public List<PurchaseTrades> getPurchaseTrades(String whereSql) {
		String hql = "from PurchaseTrades as dp " + whereSql
		+ " order by dp.makedate desc";
		return EntityManager.getAllByHql(hql);
	}

}
