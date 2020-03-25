package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseBillDetail {

  
  public void addPurchaseBillDetail(Object pbd) throws Exception {
    
    EntityManager.save(pbd);
    
  }

  
  public List<PurchaseBillDetail> getPurchaseBillDetailByPbID(String pbid) throws Exception {

    String sql = " from PurchaseBillDetail as pbd where pbd.pbid='" +
        pbid+"'";
    return EntityManager.getAllByHql(sql);
  }



  public void delPurchaseBillDetailByPbID(String pbid) throws Exception {
    
    String sql = "delete from purchase_bill_detail where pbid='" + pbid+"'";
    EntityManager.updateOrdelete(sql);
    
  }
  
  public List getPurchaseBillDetailByPbIDNoTrans(String pbid) throws Exception {
	    String sql = "from PurchaseBillDetail as pbd where pbd.quantity != pbd.incomequantity and  pbd.pbid='" +
	        pbid+"'";
	    //System.out.println("---"+sql);
	    return EntityManager.getAllByHql(sql);
	  }
  

	public List getPerDayPurchaseReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		List ls = null;
		String sql = "select pbd.productid,sum(pbd.quantity),sum(pbd.subsum) from PurchaseBillDetail as pbd, PurchaseBill as pb "
				+ whereSql + " group by pbd.productid order by sum(pbd.subsum) desc ";

		ls = EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
		return ls;
	}
  

  public List getPurchaseCountReport(String pWhereClause) throws Exception {
  	List ls = new ArrayList();
  	String hql = "select pbd.productid,sum(pbd.quantity) from Purchase_Bill_Detail as pbd, Purchase_Bill as pb "
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
  	String hql = "select pbd.productid,sum(pbd.subsum) from Purchase_Bill_Detail as pbd, Purchase_Bill as pb "
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
  
  	
	public List getSettlementPurchaseBillDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select d from PurchaseBill as s ,PurchaseBillDetail as d "
				+ pWhereClause + " order by d.id ";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List getSettlementPurchaseBillDetail2(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select d from PurchaseBill as s ,PurchaseBillDetail as d,Product as p "
				+ pWhereClause + " order by d.id ";
		//System.out.println("--------->sql="+sql);
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	
	public void updIsSettlementByPbidProductid(String pbid, String productid, Integer issettlement)throws Exception{
		
		String sql="update Purchase_Bill_Detail set issettlement="+issettlement+" where pbid='"+pbid+"' and productid='"+productid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getPurchaseBillDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select d from PurchaseBill as s ,PurchaseBillDetail as d,Product as p "
				+ pWhereClause + " order by d.id ";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	  
	  public void updIncomeQuantity(Integer id, Double quantity) throws Exception{

		    String sql = "update  purchase_bill_detail set incomequantity=incomequantity+"+quantity+" where id="+id;
		     EntityManager.updateOrdelete(sql);

	  }
	  
	  public List getPurchaseBillDetail2(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
			String sql = "select pb.pid,pb.pname,pb.makedate,pbd from PurchaseBill as pb ,PurchaseBillDetail as pbd "
					+ pWhereClause + " order by pbd.productid, pb.makedate desc ";
			System.out.println(sql);
			return PageQuery.hbmQuery(request, sql, pagesize);
		}
	  
	  public List getPurchaseBillDetail2(String pWhereClause) throws Exception {
			String sql = "select pb.pid,pb.pname,pb.makedate,pbd from PurchaseBill as pb ,PurchaseBillDetail as pbd "
					+ pWhereClause + " order by pbd.productid, pb.makedate desc ";
			return EntityManager.getAllByHql(sql);
		}
		
		public double getTotalSubum(String whereSql)throws Exception{		    
		    String sql="select sum(pbd.subsum) from PurchaseBill as pb ,PurchaseBillDetail as pbd "+whereSql;
		    return EntityManager.getdoubleSum(sql);		    
		}

}
