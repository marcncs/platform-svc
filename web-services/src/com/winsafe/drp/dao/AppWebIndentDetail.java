package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppWebIndentDetail {

  public void addWebIndentDetail(Object sod)throws Exception{
    
    EntityManager.save(sod);
    
  }
  

  public List getWebIndentDetailBySOID(String soid)throws Exception{
    List sl = null;
    String sql="select d.id,d.woid,d.productid,d.productname,d.specmode,d.unitid,d.unitprice,d.quantity,d.subsum,d.cost from WebIndentDetail as d where d.woid='"+soid+"'";
    sl = EntityManager.getAllByHql(sql);
    return sl;
  }
  
  public WebIndentDetail getWebIndentDetailByID(Long id)throws Exception{	   
	    String sql="from WebIndentDetail where id="+id+"";
	    return (WebIndentDetail)EntityManager.find(sql);	     
	  }
  
  public void updWebIndentDetail(WebIndentDetail sod)throws Exception{
	    
	    EntityManager.update(sod);
	    
	  }
  
  public List getWebIndentDetailObjectBySOID(String soid)throws Exception{
	    List sl = null;
	    String sql=" from WebIndentDetail as d where d.woid='"+soid+"'";
	    sl = EntityManager.getAllByHql(sql);
	    return sl;
	  }
  
  public List getWebIndentDetailBySOIDForWithdraw(int pagesize, String pWhereClause,
          SimplePageInfo pPgInfo)throws Exception{
	    List sl = null;
	    int targetPage = pPgInfo.getCurrentPageNo();
	    String sql="select d.id,d.woid,d.productid,d.unitid,d.unitprice,d.quantity,d.subsum from WebIndentDetail as d "+pWhereClause;
	    sl = EntityManager.getAllByHql(sql, targetPage, pagesize);
	    return sl;
	  }

  public void delWebIndentBySOID(String soid)throws Exception{
    
    String sql="delete from web_indent_detail where woid='"+soid+"'";
    EntityManager.updateOrdelete(sql);
    
  }
  

	public List getPerDaySaleReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		List ls = null;
		String sql = "select sod.productid,sum(sod.quantity),sum(sod.subsum) from WebIndentDetail as sod, WebIndent as so "
				+ whereSql + " group by sod.productid order by sum(sod.subsum) desc ";
//System.out.println("---sql==="+sql);
		ls = EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
		return ls;
	}
	
	
	public List getSaleReport(String pWhereClause) throws Exception {
		List ls = new ArrayList();
		String hql = "select sod.productid,sum(sod.quantity) from web_indent_Detail as sod, web_indent as so "
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
		String hql = "select sod.productid,sum(sod.subsum) from web_indent_Detail as sod, web_indent as so "
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
	
	
	public void updTakeQuantityRemove(String soid,String productid, String batch, double takequantity,long warehouseid)throws Exception{
		//String sql="update web_indent_detail set takequantity=takequantity +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"'";
		String sql="update web_indent_detail set takequantity="+takequantity+" where woid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void updTakeQuantity(String soid,String productid, String batch, double takequantity, long warehouseid)throws Exception{
		//String sql="update web_indent_detail set takequantity=takequantity +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		String sql="update web_indent_detail set takequantity="+takequantity+" where woid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		EntityManager.updateOrdelete(sql);
	}
	

	public void updTakeQuantity(String soid,String productid, String batch, double takequantity)throws Exception{
		//String sql="update web_indent_detail set takequantity=takequantity +"+takequantity+" where soid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"' and warehouseid="+warehouseid;
		String sql="update web_indent_detail set takequantity="+takequantity+" where woid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"' ";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public List getSaleAreasReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo)throws Exception{
		List sr = new ArrayList();
		int targetPage = tmpPgInfo.getCurrentPageNo();
		String sql="select sod.productid,sum(sod.quantity) as quantity from WebIndent as so,WebIndentDetail as sod "+whereSql+" group by sod.productid order by sum(sod.quantity) desc";
		//System.out.println("----"+sql);
		sr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return sr;
	}
	
	
	public List getCustomerAreas(String whereSql)throws Exception{
		List cls = new ArrayList();
		String sql="select so.cid, so.cname, sum(sod.quantity) as quantity from WebIndent as so,WebIndentDetail as sod "+whereSql+" group by so.cid, so.cname ";
		cls = EntityManager.getAllByHql(sql);
		return cls;
	}
	
	
	public List getWarehouseIdBySoid(String soid) throws Exception{
		List cls = new ArrayList();
		String sql =" select distinct warehouseid from WebIndentDetail where woid='"+soid+"'";
		cls = EntityManager.getAllByHql(sql);
		return cls;
	}
	
	public void updIsSettlement(String soid, Integer issettlement)throws Exception{
		
		String sql="update web_indent_detail set issettlement="+issettlement+" where woid='"+soid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updIsSettlementBySoidBatch(String soid, String productid, String batch, Integer issettlement)throws Exception{
		
		String sql="update web_indent_detail set issettlement="+issettlement+" where woid='"+soid+"' and productid='"+productid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public List getSettlementWebIndentDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select d from WebIndent as s ,WebIndentDetail as d,Product as p "
				+ pWhereClause + " order by d.id ";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List getWebIndentDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select so.cname,sod from WebIndent as so ,WebIndentDetail as sod "
				+ pWhereClause + " order by sod.productid, so.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	public List getWebIndentDetail(String pWhereClause) throws Exception {
		String sql = "select so.cname,sod from WebIndent as so ,WebIndentDetail as sod "
				+ pWhereClause + " order by sod.productid, so.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	  public double getTotalSubum(String whereSql)throws Exception{		    
		    String sql="select sum(sod.subsum) from WebIndent as so ,WebIndentDetail as sod "+whereSql;
		    return EntityManager.getdoubleSum(sql);		    
	  }
	  
	  public void updWarehourseId(long id, long warehouseid)throws Exception{
			String sql="update web_indent_detail set warehouseid="+warehouseid+" where id="+id;
			EntityManager.updateOrdelete(sql);
		}
	

}
