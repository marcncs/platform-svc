package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppStockCheckDetail {

	public List getStockCheckDetail(HttpServletRequest request, int pagesize, String whereSql) throws Exception {
		String hql = " from StockCheckDetail  "
				+ whereSql + " order by id desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public void addStockCheckDetail(Object spb) throws Exception {
		EntityManager.save(spb);
	}
	
	public void delStockCheckDetailByID(int id) throws Exception {
		String sql = "delete from stock_check_detail where id=" + id;
		EntityManager.updateOrdelete(sql);
	}
	
	public void updCheckQuantity(String scid, String warehousebit, String productid, 
			String batch, double quantity) throws Exception {
		String sql ="update stock_check_detail set checkquantity=checkquantity + "+quantity+" where scid='"+scid
		+"' and warehousebit='"+warehousebit+"' and productid='"+productid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
	}

	public List getStockCheckDetailBySmID(String scid) throws Exception {
		String sql = "from StockCheckDetail where scid='" + scid + "'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getStockCheckDetailBySmID(String scid, String productid) throws Exception {
		String sql = "from StockCheckDetail where scid='" + scid + "' and productid='"+productid+"'";
		return EntityManager.getAllByHql(sql);
	}

	public void delStockCheckDetailBySmID(String scid) throws Exception {
		String sql = "delete from stock_check_detail where scid='" + scid + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	
	public List getStockCheckDetail(String pWhereClause) throws Exception {
		String sql = "select sc, scd from StockCheck as sc ,StockCheckDetail as scd,Product as p "
				+ pWhereClause + " order by scd.productid, sc.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
}
