package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSaleOrderDetail {

	public void addSaleOrderDetail(Object sod) throws Exception {
		EntityManager.save(sod);
	}

	public SaleOrderDetail getSaleOrderDetailByID(Integer id) throws Exception {
		String sql = "from SaleOrderDetail where id=" + id + "";
		return (SaleOrderDetail) EntityManager.find(sql);
	}

	public void updSaleOrderDetail(SaleOrderDetail sod) throws Exception {
		EntityManager.update(sod);
	}

	public void delSaleOrderBySOID(String soid) throws Exception {
		String sql = "delete from sale_order_detail where soid='" + soid + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List<SaleOrderDetail> getSaleOrderDetailBySoid(String soid) throws Exception {
		String sql = " from SaleOrderDetail as d where d.soid='" + soid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public List getSaleOrderDetailBySoidWise(String soid, int wise)
			throws Exception {
		String sql = " from SaleOrderDetail  where soid='" + soid
				+ "' and wise=" + wise;
		return EntityManager.getAllByHql(sql);
	}

	public List getSaleOrderDetailBySOIDForWithdraw(int pagesize,
			String pWhereClause, SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select d.id,d.soid,d.productid,d.unitid,d.unitprice,d.quantity,d.subsum from SaleOrderDetail as d "
				+ pWhereClause;
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	
	public List getPerDaySaleReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		List ls = null;
		String sql = "select sod.productid,sum(sod.quantity),sum(sod.subsum) from SaleOrderDetail as sod, SaleOrder as so "
				+ whereSql
				+ " group by sod.productid order by sum(sod.subsum) desc ";
		// System.out.println("---sql==="+sql);
		ls = EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(),
				pagesize);
		return ls;
	}

	
	public List getSaleReport(String pWhereClause) throws Exception {
		List ls = new ArrayList();
		String hql = "select sod.productid,sum(sod.quantity) from Sale_Order_Detail as sod, Sale_Order as so "
				+ pWhereClause
				+ " group by sod.productid order by sum(sod.quantity) desc";
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

	
	public List getSaleSumReport(String pWhereClause) throws Exception {
		List ls = new ArrayList();
		String hql = "select sod.productid,sum(sod.subsum) from Sale_Order_Detail as sod, Sale_Order as so "
				+ pWhereClause
				+ " group by sod.productid order by sum(sod.subsum) desc";
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

	
	public void updTakeQuantityRemove(String soid, String productid,
			String batch, double takequantity, long warehouseid)
			throws Exception {
		// String
		// sql="update sale_order_detail set takequantity=takequantity +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"'";
		String sql = "update sale_order_detail set takequantity="
				+ takequantity + " where soid='" + soid + "' and batch='"
				+ batch + "' and productid='" + productid
				+ "' and warehouseid=" + warehouseid;
		EntityManager.updateOrdelete(sql);
	}

	
	public void updTakeQuantity(String soid, String productid, String batch,
			double takequantity, long warehouseid) throws Exception {
		// String
		// sql="update sale_order_detail set takequantity=takequantity +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		String sql = "update sale_order_detail set takequantity="
				+ takequantity + " where soid='" + soid + "' and batch='"
				+ batch + "' and productid='" + productid
				+ "' and warehouseid=" + warehouseid;
		EntityManager.updateOrdelete(sql);
	}

	
	public void updTakeQuantity(String soid, String productid, String batch,
			double takequantity) throws Exception {
		// String
		// sql="update sale_order_detail set takequantity=takequantity +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		String sql = "update sale_order_detail set takequantity="
				+ takequantity + " where soid='" + soid + "' and batch='"
				+ batch + "' and productid='" + productid + "' ";
		EntityManager.updateOrdelete(sql);
	}

	
	public List getSaleAreasReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		List sr = new ArrayList();
		int targetPage = tmpPgInfo.getCurrentPageNo();
		String sql = "select sod.productid,sum(sod.quantity) as quantity from SaleOrder as so,SaleOrderDetail as sod "
				+ whereSql
				+ " group by sod.productid order by sum(sod.quantity) desc";
		// System.out.println("----"+sql);
		sr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return sr;
	}

	
	public List getCustomerAreas(String whereSql) throws Exception {
		List cls = new ArrayList();
		String sql = "select so.cid, so.cname, sum(sod.quantity) as quantity from SaleOrder as so,SaleOrderDetail as sod "
				+ whereSql + " group by so.cid, so.cname ";
		cls = EntityManager.getAllByHql(sql);
		return cls;
	}

	
	public List getWarehouseIdBySoid(String soid) throws Exception {
		List cls = new ArrayList();
		String sql = " select distinct warehouseid from SaleOrderDetail where soid='"
				+ soid + "'";
		cls = EntityManager.getAllByHql(sql);
		return cls;
	}

	
	public void updIsSettlement(String soid, Integer issettlement)
			throws Exception {

		String sql = "update sale_order_detail set issettlement="
				+ issettlement + " where soid='" + soid + "'";
		EntityManager.updateOrdelete(sql);

	}

	
	public void updIsSettlementBySoidBatch(String soid, String productid,
			String batch, Integer issettlement) throws Exception {

		String sql = "update sale_order_detail set issettlement="
				+ issettlement + " where soid='" + soid + "' and productid='"
				+ productid + "' and batch='" + batch + "'";
		EntityManager.updateOrdelete(sql);

	}

	
	public List getSettlementSaleOrderDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select d from SaleOrder as s ,SaleOrderDetail as d,Product as p "
				+ pWhereClause + " order by d.id ";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	public List getSaleOrderDetail(HttpServletRequest request, int pageSize,
			String pWhereClause) throws Exception {

		String sql = "select cid,cname,so.id,so.makedate,so.makeorganid,so.equiporganid,sod.productid,sod.productname,"
				+ "sod.specmode,sod.unitid,sod.unitprice,sod.quantity,sod.subsum "
				+ "from sale_order so,sale_order_detail sod " + pWhereClause;
		return PageQuery.jdbcSqlserverQuery(request, sql, pageSize);
	}

	public List getSaleOrderDetail(String pWhereClause) throws Exception {
		String sql = "select cid,cname,so.id,so.makedate,so.makeorganid,so.equiporganid,sod.productid,sod.productname,"
			+ "sod.specmode,sod.unitid,sod.unitprice,sod.quantity,sod.subsum "
			+ "from sale_order so,sale_order_detail sod " + pWhereClause;
		return EntityManager.jdbcquery(sql);
	}

	public double getTotalSubum(String whereSql) throws Exception {
		String sql = "select sum(sod.subsum) from SaleOrder as so ,SaleOrderDetail as sod "
				+ whereSql;
		return EntityManager.getdoubleSum(sql);
	}

}
