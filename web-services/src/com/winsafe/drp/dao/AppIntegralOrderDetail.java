package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppIntegralOrderDetail {

  public void addIntegralOrderDetail(Object sod)throws Exception{    
    EntityManager.save(sod);    
  }

  
  public IntegralOrderDetail getIntegralOrderDetailByID(Long id)throws Exception{	   
	    String sql="from IntegralOrderDetail where id="+id+"";
	    return (IntegralOrderDetail)EntityManager.find(sql);	     
	  }
  
  public void updIntegralOrderDetail(IntegralOrderDetail sod)throws Exception{	    
	    EntityManager.update(sod);	    
	  }
  
  public List getIntegralOrderDetailByIoid(String ioid)throws Exception{
	    String sql=" from IntegralOrderDetail where ioid='"+ioid+"'";
	    return EntityManager.getAllByHql(sql);
	  }
  

  public void delIntegralOrderByioid(String ioid)throws Exception{   
    String sql="delete from integral_order_detail where ioid='"+ioid+"'";
    EntityManager.updateOrdelete(sql);
    
  }
  

	public List getPerDaySaleReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		List ls = null;
		String sql = "select sod.productid,sum(sod.quantity),sum(sod.subsum) from IntegralOrderDetail as sod, IntegralOrder as so "
				+ whereSql + " group by sod.productid order by sum(sod.subsum) desc ";
//System.out.println("---sql==="+sql);
		ls = EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
		return ls;
	}
	
	
	public List getSaleReport(String pWhereClause) throws Exception {
		List ls = new ArrayList();
		String hql = "select sod.productid,sum(sod.quantity) from integral_order_detail as sod, Sale_Order as so "
				+ pWhereClause + " group by sod.productid order by sum(sod.quantity) desc";
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
		String hql = "select sod.productid,sum(sod.subsum) from integral_order_detail as sod, Sale_Order as so "
				+ pWhereClause + " group by sod.productid order by sum(sod.subsum) desc";
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
	
	
	public void updTakeQuantityRemove(String ioid,String productid, String batch, double takequantity,long warehouseid)throws Exception{
		//String sql="update integral_order_detail set takequantity=takequantity +"+takequantity+" where ioid='"+ioid+"' and batch='"+batch+"' and productid='"+productid+"'";
		String sql="update integral_order_detail set takequantity="+takequantity+" where ioid='"+ioid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void updTakeQuantity(String ioid,String productid, String batch, double takequantity, long warehouseid)throws Exception{
		//String sql="update integral_order_detail set takequantity=takequantity +"+takequantity+" where ioid='"+ioid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		String sql="update integral_order_detail set takequantity="+takequantity+" where ioid='"+ioid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		EntityManager.updateOrdelete(sql);
	}
	

	public void updTakeQuantity(String ioid,String productid, String batch, double takequantity)throws Exception{
		//String sql="update integral_order_detail set takequantity=takequantity +"+takequantity+" where ioid='"+ioid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		String sql="update integral_order_detail set takequantity="+takequantity+" where ioid='"+ioid+"' and productid='"+productid+"' ";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public List getSaleAreasReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo)throws Exception{
		List sr = new ArrayList();
		int targetPage = tmpPgInfo.getCurrentPageNo();
		String sql="select sod.productid,sum(sod.quantity) as quantity from IntegralOrder as so,IntegralOrderDetail as sod "+whereSql+" group by sod.productid order by sum(sod.quantity) desc";
		//System.out.println("----"+sql);
		sr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return sr;
	}
	
	
	public List getCustomerAreas(String whereSql)throws Exception{
		List cls = new ArrayList();
		String sql="select so.cid, so.cname, sum(sod.quantity) as quantity from IntegralOrder as so,IntegralOrderDetail as sod "+whereSql+" group by so.cid, so.cname ";
		cls = EntityManager.getAllByHql(sql);
		return cls;
	}
	
	
	public List getWarehouseIdByioid(String ioid) throws Exception{
		List cls = new ArrayList();
		String sql =" select distinct warehouseid from IntegralOrderDetail where ioid='"+ioid+"'";
		cls = EntityManager.getAllByHql(sql);
		return cls;
	}
	
	public void updIsSettlement(String ioid, Integer issettlement)throws Exception{
		
		String sql="update integral_order_detail set issettlement="+issettlement+" where ioid='"+ioid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updIsSettlementByioidBatch(String ioid, String productid, String batch, Integer issettlement)throws Exception{
		
		String sql="update integral_order_detail set issettlement="+issettlement+" where ioid='"+ioid+"' and productid='"+productid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public List getSettlementIntegralOrderDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select d from IntegralOrder as s ,IntegralOrderDetail as d,Product as p "
				+ pWhereClause + " order by d.id ";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	 public void updWarehourseId(long id, long warehouseid)throws Exception{
			String sql="update integral_order_detail set warehouseid="+warehouseid+" where id="+id;
			EntityManager.updateOrdelete(sql);
		}
	 
	 public List getIntegralOrderDetail(int pagesize, String pWhereClause,
				SimplePageInfo pPgInfo) throws Exception {
			int targetPage = pPgInfo.getCurrentPageNo();
			String sql = "select io,iod from IntegralOrder as io ,IntegralOrderDetail as iod "
					+ pWhereClause + " order by io.makedate desc ";
			return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	 
	 public List getIntegralOrderDetail(String pWhereClause) throws Exception {
			String sql = "select io,iod from IntegralOrder as io ,IntegralOrderDetail as iod "
					+ pWhereClause + " order by io.makedate desc ";
			return EntityManager.getAllByHql(sql);
	}
	 
	 public List getTotalSubum(String whereSql)throws Exception{		    
		    String sql="select sum(iod.quantity), sum(iod.subsum) from IntegralOrder as io ,IntegralOrderDetail as iod "+whereSql;
		    return EntityManager.getAllByHql(sql);		    
		}
	

}
