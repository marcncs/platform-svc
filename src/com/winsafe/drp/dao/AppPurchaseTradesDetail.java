package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseTradesDetail {

	public void addPurchaseTradesDetail(PurchaseTradesDetail[] ptd)
			throws Exception {
		EntityManager.save(ptd);
	}

	public void addPurchaseTradesDetail(PurchaseTradesDetail ptd)
			throws Exception {
		EntityManager.save(ptd);
	}

	public PurchaseTradesDetail getPurchaseTradesDetailByID(int id)
			throws Exception {
		String sql = " from PurchaseTradesDetail as wd where wd.id=" + id + "";
		return (PurchaseTradesDetail) EntityManager.find(sql);
	}

	public List<PurchaseTradesDetail> getPurchaseTradesDetailByPtid(String ptid)
			throws Exception {
		String sql = " from PurchaseTradesDetail as wd where wd.ptid='" + ptid
				+ "'";
		return EntityManager.getAllByHql(sql);
	}

	public void delPurchaseTradesDetailByPtid(String ptid) throws Exception {
		String sql = "delete from purchase_trades_detail where ptid='" + ptid
				+ "'";
		EntityManager.updateOrdelete(sql);
	}
	
	public List getPurchaseTradesDetailByPiidPid(String piid, String productid)
	throws Exception {
		String sql = " from PurchaseTradesDetail where ptid='" + piid
				+ "' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}

	
	public void updTakeQuantity(String soid, String productid, String batch,
			double takequantity) throws Exception {

		String sql = "update purchase_trades_detail set takequantity=takequantity +"
				+ takequantity
				+ " where ptid='"
				+ soid
				+ "' and batch='"
				+ batch + "' and productid='" + productid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public List getDetailReport(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String sql = "select pt,ptd from PurchaseTrades as pt ,PurchaseTradesDetail as ptd "
				+ whereSql + " order by pt.id, pt.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public Double getTotalSubum(String whereSql) {
		String sql ="select sum(ptd.quantity) as quantity from PurchaseTrades pt,PurchaseTradesDetail as ptd "+whereSql;
		return EntityManager.getdoubleSum(sql);
	}

	public List getPurchaseProductTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select pt.makeorganid,ptd.productid, ptd.productname, ptd.specmode, ptd.unitid, sum(ptd.quantity) as quantity " +
		 " from Purchase_Trades_Detail ptd, Purchase_Trades pt " + whereSql + 
		" group by pt.makeorganid,ptd.productid,ptd.productname, ptd.specmode, ptd.unitid ";
		return PageQuery.jdbcSqlserverQuery(request,"productid", hql, pagesize);
	}

	public List getPurchaseProductTotal(String whereSql) throws HibernateException, SQLException {
		String hql = "select pt.makeorganid,ptd.productid, ptd.productname, ptd.specmode, ptd.unitid, sum(ptd.quantity) as quantity " +
		 " from Purchase_Trades_Detail ptd, Purchase_Trades pt " + whereSql + 
		" group by pt.makeorganid,ptd.productid,ptd.productname, ptd.specmode, ptd.unitid ";
		return EntityManager.jdbcquery(hql);
	}

	public List getDetailReport(String whereSql) {
		String sql = "select pt,ptd from PurchaseTrades as pt ,PurchaseTradesDetail as ptd "
			+ whereSql + " order by pt.id, pt.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getTxtDetailReport(String whereSql) {
		String sql = "select pt,ptd from PurchaseTrades as pt ,PurchaseTradesDetail as ptd,Product as p "
			+ whereSql + " order by pt.id, pt.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}

	

}
