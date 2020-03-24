package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppPeddleOrderDetail {

  public void addPeddleOrderDetail(Object sod)throws Exception{
    
    EntityManager.save(sod);
    
  }
  

  
  public PeddleOrderDetail getPeddleOrderDetailByID(String id)throws Exception{	   
	    String sql=" from PeddleOrderDetail where id='"+id+"'";
	    return (PeddleOrderDetail)EntityManager.find(sql);	     
	  }
  
  public void updPeddleOrderDetail(PeddleOrderDetail sod)throws Exception{
	    
	    EntityManager.update(sod);
	    
	  }
  
  public List getPeddleOrderDetailObjectByPOID(String soid)throws Exception{
	    List sl = null;
	    String sql=" from PeddleOrderDetail as d where d.poid='"+soid+"'";
	    sl = EntityManager.getAllByHql(sql);
	    return sl;
	  }
  
  public List getPeddleOrderDetailBySOIDForWithdraw(int pagesize, String pWhereClause,
          SimplePageInfo pPgInfo)throws Exception{
	    List sl = null;
	    int targetPage = pPgInfo.getCurrentPageNo();
	    String sql="select d.id,d.soid,d.productid,d.unitid,d.unitprice,d.quantity,d.subsum from PeddleOrderDetail as d "+pWhereClause;
	    sl = EntityManager.getAllByHql(sql, targetPage, pagesize);
	    return sl;
	  }

  public void delPeddleOrderBySOID(String soid)throws Exception{
    
    String sql="delete from peddle_order_detail where poid='"+soid+"'";
    EntityManager.updateOrdelete(sql);
    
  }
  

	public List getPerDayPeddleReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		List ls = null;
		String sql = "select sod.productid,sum(sod.quantity),sum(sod.subsum) from PeddleOrderDetail as sod, PeddleOrder as so "
				+ whereSql + " group by sod.productid order by sum(sod.subsum) desc ";
//System.out.println("---sql==="+sql);
		ls = EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
		return ls;
	}
	
	
//	public List getPeddleReport(String pWhereClause) throws Exception {
//		List ls = new ArrayList();
//		String hql = "select sod.productid,sum(sod.quantity) from Peddle_Order_Detail as sod, Peddle_Order as so "
//				+ pWhereClause + " group by sod.productid order by sum(sod.quantity) desc";
//		ResultSet rs = EntityManager.query(hql);
//		PeddleReportForm srf = null;
//		do {
//			srf = new PeddleReportForm();
//			srf.setProductid(rs.getString(1));
//			srf.setCount(rs.getInt(2));
//			ls.add(srf);
//		} while (rs.next());
//		rs.close();
//		// System.out.println("ls====="+ls.size());
//		return ls;
//	}
	
//	public List getPeddleSumReport(String pWhereClause) throws Exception {
//		List ls = new ArrayList();
//		String hql = "select sod.productid,sum(sod.subsum) from Peddle_Order_Detail as sod, Peddle_Order as so "
//				+ pWhereClause + " group by sod.productid order by sum(sod.subsum) desc";
//		ResultSet rs = EntityManager.query(hql);
//		PeddleReportForm srf = null;
//		do {
//			srf = new PeddleReportForm();
//			srf.setProductid(rs.getString(1));
//			srf.setCount(rs.getInt(2));
//			ls.add(srf);
//		} while (rs.next());
//		rs.close();
//		// System.out.println("ls====="+ls.size());
//		return ls;
//	}
//	
	
	public void updTakeQuantity(String soid,String productid, String batch, double takequantity)throws Exception{
		
		String sql="update peddle_order_detail set takequantity=takequantity +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updTakeQuantity(String soid,String productid, String batch, double takequantity, long warehouseid)throws Exception{
		
		String sql="update peddle_order_detail set takequantity=takequantity +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public List getPeddleAreasReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo)throws Exception{
		List sr = new ArrayList();
		int targetPage = tmpPgInfo.getCurrentPageNo();
		String sql="select sod.productid,sum(sod.quantity) as quantity from PeddleOrder as so,PeddleOrderDetail as sod "+whereSql+" group by sod.productid order by sum(sod.quantity) desc";
		//System.out.println("----"+sql);
		sr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return sr;
	}
	
	
	public List getCustomerAreas(String whereSql)throws Exception{
		List cls = new ArrayList();
		String sql="select so.cid, so.cname, sum(sod.quantity) as quantity from PeddleOrder as so,PeddleOrderDetail as sod "+whereSql+" group by so.cid, so.cname ";
		cls = EntityManager.getAllByHql(sql);
		return cls;
	}
	
	
	public List getWarehouseIdBySoid(String soid) throws Exception{
		List cls = new ArrayList();
		String sql =" select distinct warehouseid from PeddleOrderDetail where soid='"+soid+"'";
		cls = EntityManager.getAllByHql(sql);
		return cls;
	}
	
	public void updIsSettlement(String soid, Integer issettlement)throws Exception{
		
		String sql="update peddle_order_detail set issettlement="+issettlement+" where soid='"+soid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updIsSettlementBySoidBatch(String soid, String productid, String batch, Integer issettlement)throws Exception{
		
		String sql="update Peddle_order_detail set issettlement="+issettlement+" where soid='"+soid+"' and productid='"+productid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public List getSettlementPeddleOrderDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select d from PeddleOrder as s ,PeddleOrderDetail as d,Product as p "
				+ pWhereClause + " order by d.id ";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List getPeddleOrderDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.cname, sod from PeddleOrder as so ,PeddleOrderDetail as sod "
				+ pWhereClause + " order by sod.productid, so.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	public List getPeddleOrderDetail(String pWhereClause) throws Exception {
		String sql = "select so.cname, sod from PeddleOrder as so ,PeddleOrderDetail as sod "
				+ pWhereClause + " order by sod.productid, so.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	public double getTotalSubum(String whereSql)throws Exception{		    
	    String sql="select sum(sod.subsum) from PeddleOrder as so ,PeddleOrderDetail as sod "+whereSql;
	    return EntityManager.getdoubleSum(sql);		    
	}
	

}
