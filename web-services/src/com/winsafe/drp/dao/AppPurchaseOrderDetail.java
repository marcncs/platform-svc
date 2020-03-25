package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppPurchaseOrderDetail {

  
  public void addPurchaseOrderDetail(PurchaseOrderDetail pbd) throws Exception {
    
    EntityManager.save(pbd);
    
  }

  
  public List getPurchaseOrderDetailByPoID(String poid) throws Exception {
    List pbd = null;
    String sql = " from PurchaseOrderDetail as pbd where pbd.poid='" +
        poid+"' ";
    pbd = EntityManager.getAllByHql(sql);
    return pbd;
  }


  public void delPurchaseOrderDetailByPoID(String poid) throws Exception {
    
    String sql = "delete from purchase_order_detail where poid='" + poid+"'";
    EntityManager.updateOrdelete(sql);
    
  }
  
  
  public void updIncomeQuantity(String poid, String productid, Double quantity) throws Exception{

	    String sql = "update  purchase_order_detail set incomequantity=incomequantity+"+quantity+" where poid='"+poid+"' and productid='" +
	        productid+"'";
	   EntityManager.updateOrdelete(sql);

  }
  

	public List getPerDayPurchaseReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		List ls = null;
		String sql = "select pbd.productid,sum(pbd.quantity),sum(pbd.subsum) from PurchaseOrderDetail as pbd, PurchaseOrder as pb "
				+ whereSql + " group by pbd.productid order by sum(pbd.subsum) desc ";

		ls = EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
		return ls;
	}
  

  public List getPurchaseCountReport(String pWhereClause) throws Exception {
  	List ls = new ArrayList();
  	String hql = "select pbd.productid,sum(pbd.quantity) from Purchase_order_Detail as pbd, Purchase_order as pb "
  			+ pWhereClause + " group by pbd.productid order by sum(pbd.quantity) desc";
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

  public List getPurchaseSumReport(String pWhereClause) throws Exception {
  	List ls = new ArrayList();
  	String hql = "select pbd.productid,sum(pbd.subsum) from Purchase_order_Detail as pbd, Purchase_order as pb "
  			+ pWhereClause + " group by pbd.productid order by sum(pbd.subsum) desc";
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

}
