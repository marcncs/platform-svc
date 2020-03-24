package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSaleTradesDetail {
	
	public void addSaleTradesDetail(Object spb) throws Exception {		
		EntityManager.save(spb);		
	}

	public SaleTradesDetail getSaleTradesDetailByID(int id) throws Exception {
		String sql = " from SaleTradesDetail where id=" + id + "";
		return (SaleTradesDetail) EntityManager.find(sql);
	}

	public List<SaleTradesDetail> getSaleTradesDetailByStid(String stid) throws Exception {		
		String sql = " from SaleTradesDetail where stid='" + stid + "'";
		return  EntityManager.getAllByHql(sql);		
	}
	
	public List getSaleTradesDetailByPiidPid(String piid, String productid)
	throws Exception {
		String sql = " from SaleTradesDetail where stid='" + piid
				+ "' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public List getSaleTradesDetailByID(String stid) throws Exception {
		String sql = "from SaleTradesDetail where stid='"+ stid + "'";
		return EntityManager.getAllByHql(sql);
	}

	
	public void delSaleTradesDetailByRID(String rid) throws Exception {		
		String sql = "delete from sale_trades_detail where stid='" + rid + "'";
		EntityManager.updateOrdelete(sql);		
	}

	
	public List getPerDayShipmentReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		List ls = null;
		String sql = "select sbd.productid,sum(sbd.quantity),sum(sbd.subsum) from ShipmentBillDetail as sbd, ShipmentBill as sb "
				+ whereSql
				+ " group by sbd.productid order by sum(sbd.quantity) desc ";

		ls = EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(),
				pagesize);
		return ls;
	}

	
	public List getShipmentCountReport(String pWhereClause) throws Exception {
		List ls = new ArrayList();
		String hql = "select sbd.productid,sum(sbd.quantity) from Shipment_Bill_Detail as sbd, Shipment_Bill as sb "
				+ pWhereClause
				+ " group by sbd.productid order by sum(sbd.quantity) desc";
		ResultSet rs = EntityManager.query(hql);
		SaleReportForm srf = null;
		do {
			srf = new SaleReportForm();
			srf.setProductid(rs.getString(1));
			srf.setCount(rs.getInt(2));
			ls.add(srf);
		} while (rs.next());
		rs.close();
		// System.out.println("ls====="+ls.size());
		return ls;
	}

	
	public List getShipmentSumReport(String pWhereClause) throws Exception {
		List ls = new ArrayList();
		String hql = "select spb.productid,sum(spb.subsum) from Shipment_Product_Bill as spb, Shipment_Bill as sb "
				+ pWhereClause
				+ " group by spb.productid order by sum(spb.subsum) desc";
		ResultSet rs = EntityManager.query(hql);
		SaleReportForm srf = null;
		do {
			srf = new SaleReportForm();
			srf.setProductid(rs.getString(1));
			srf.setCount(rs.getInt(2));
			ls.add(srf);
		} while (rs.next());
		rs.close();
		// System.out.println("ls====="+ls.size());
		return ls;
	}
	
	
	public List getSaleTradesDetail(HttpServletRequest request, int pageSize,
			String pWhereClause) throws Exception {
		String sql = "select cid, cname,so.id,so.makedate,so.makeorganid,sod.productid,sod.productname,"
				+ "sod.specmode,sod.unitid,sod.batch,sod.quantity "
				+ "from Sale_Trades so,Sale_Trades_Detail sod " + pWhereClause;
		return PageQuery.jdbcSqlserverQuery(request, sql, pageSize);
	}

	public List getSaleTradesDetail(String pWhereClause) throws Exception {
		String sql = "select cid,so.cname,so.id,so.makedate,so.makeorganid,sod.productid,sod.productname,"
			+ "sod.specmode,sod.unitid,sod.batch,sod.quantity"
			+ " from Sale_Trades so,Sale_Trades_Detail sod " + pWhereClause;
		//System.out.println(sql);
		return EntityManager.jdbcquery(sql);
	}
	
	public double getDetailTotalSubum(String whereSql) throws Exception {
		String sql = "select sum(sod.quantity) from SaleTrades as so ,SaleTradesDetail as sod "
				+ whereSql;
		return EntityManager.getdoubleSum(sql);
	}
	
	public List getDetailReport(String whereSql) {
		String sql = "select st,std from SaleTrades as st ,SaleTradesDetail as std,Product as p "
			+ whereSql + " order by st.id,st.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
}
