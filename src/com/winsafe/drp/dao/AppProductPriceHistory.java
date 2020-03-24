package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppProductPriceHistory {
	
	public List getProductPriceHistory(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pi = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from ProductPriceHistory as ph "
				+ pWhereClause + " order by ph.makeDate desc ";
		pi = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pi;
	}
	
	public List getProductPriceHistory(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = " from ProductPriceHistory as ph " + pWhereClause
				+ " order by ph.makeDate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getProductPriceHistoryByNoPage(String pWhereClause) throws Exception {
		List pi = null;
		String sql = "from ProductPriceHistory as ph " + pWhereClause + " order by ph.makeDate desc ";
		pi = EntityManager.getAllByHql(sql);
		return pi;
	}


	public void addProductPriceHistory(ProductPriceHistory pp) throws Exception{
		
		EntityManager.save(pp);
		
	}
	
	public void updProductPriceHistory(ProductPriceHistory pp) throws Exception{
		
		EntityManager.saveOrUpdate(pp);
		
	}
	
	public List getAllProductPriceHistory()throws Exception{
		String sql=" from ProductPriceHistory";
		List ls = EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public void delProductPriceHistoryByProductID(String pid) throws Exception{
		
		String sql="delete from Product_Price where productid='"+pid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void delProductPriceHistoryByID(Long pphid) throws Exception{
		
		String sql="delete from Product_Price_History where id="+pphid;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public ProductPriceHistory getProductPriceHistoryById(Long id) throws Exception{
		ProductPriceHistory bank=null;
		String sql="from ProductPriceHistory where id="+id;
		bank=(ProductPriceHistory)EntityManager.find(sql);
		return bank;
	}
	
	public List getProductPriceHistoryByProductID(String pid) throws Exception{
		List list = null;
		String sql = " from ProductPriceHistory where productid='"+pid+"'";
		list = EntityManager.getAllByHql(sql);
		return list;
	}
	
	
	public ProductPriceHistory getProdutPriceByPlcyPIDUnitID(Long plcyid,String pid,int unitid)throws Exception{
		String sql=" from ProductPriceHistory as pp where pp.policyid="+plcyid+" and pp.productid='"+pid+"' and pp.unitid="+unitid+" ";
		ProductPriceHistory pp= (ProductPriceHistory)EntityManager.find(sql);
		return pp;
	}
	
	public List getAAAA()throws Exception{
		//String sql = "select productid,unitid,policyid,unitprice from ProductPriceHistory where policyid=3";
		String sql="from ProductPriceHistory where policyid=6";
		List ls=EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public boolean  getProductPriceHistoryByProductIdAndTime(String productId,String startTime,String endTime ) throws Exception{
		ProductPriceHistory bank=null;
//		String sql = "select count(idcode) from Idcode where ( startno between '" + startno +"' and '" + endno +"'" 
//		 + " or endno between '" + startno +"' and '" + endno + "'"
//		 + " or ( startno >= '" + startno + "' and endno <= '" + endno + "')" 
//		 + " or ( startno <= '" + startno + "' and endno >= '" + endno + "') ) "
//		 + " and ( startno != '" + startno + "' and endno != '" + endno + "') ";
		String sql = "select count(id) from ProductPriceHistory where ( starttime between '" + startTime +"' and '" + endTime +"'" 
		+ " or endtime between '" + startTime +"' and '" + endTime + "'"
		+ " or ( starttime >= '" + startTime + "' and endtime <= '" + endTime + "')" 
		+ " or ( starttime <= '" + startTime + "' and endtime >= '" + endTime + "') ) " 
		+ "  AND productid='" + productId +  "'";
		int count = EntityManager.getRecordCount(sql);
		if (count > 0) {
			return true;
		}
		return false;

	}
	
	public boolean  getProductPriceHistoryByProductIdAndTime(Long pphid,String productId,String startTime,String endTime ) throws Exception{
//		String sql = "select count(idcode) from Idcode where ( startno between '" + startno +"' and '" + endno +"'" 
//		 + " or endno between '" + startno +"' and '" + endno + "'"
//		 + " or ( startno >= '" + startno + "' and endno <= '" + endno + "')" 
//		 + " or ( startno <= '" + startno + "' and endno >= '" + endno + "') ) "
//		 + " and ( startno != '" + startno + "' and endno != '" + endno + "') ";
		String sql = "select count(id) from ProductPriceHistory where ( starttime between '" + startTime +"' and '" + endTime +"'" 
		+ " or endtime between '" + startTime +"' and '" + endTime + "'"
		+ " or ( starttime >= '" + startTime + "' and endtime <= '" + endTime + "')" 
		+ " or ( starttime <= '" + startTime + "' and endtime >= '" + endTime + "') ) " 
		+ "  AND productid='" + productId +  "' and id<>" + pphid;
		int count = EntityManager.getRecordCount(sql);
		if (count > 0) {
			return true;
		}
		return false;

	}
	
	public List getProductPriceHistoryList(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select ph,br from ProductPriceHistory as ph,BaseResource as br  " + pWhereClause
				+ " order by ph.makeDate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getProductPriceHistoryList(String pWhereClause) throws Exception {
		String hql = "select ph,br from ProductPriceHistory as ph,BaseResource as br  " + pWhereClause
				+ " order by ph.makeDate desc ";
		return EntityManager.getAllByHql(hql);
	}
}
