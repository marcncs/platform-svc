package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppProviderProduct {

	
	public List getProviderProductByPId(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pp.id,pp.providerid,pp.productid,pp.pvproductname,pp.pvspecmode,pp.countunit,pp.barcode,pp.price,pp.pricedate from ProviderProduct as pp,Product as p "
				+ pWhereClause + " order by pp.id desc ";
		List pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List getProviderProductByProvideID(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select p, pp from ProviderProduct as pp,Product as p "
				+ pWhereClause + " order by pp.id desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getProviderProduct(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = " from ProviderProduct as pp "
				+ pWhereClause + " order by pp.id desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	
	public ProviderProduct getProvideProductByPPID(String pvid,String productid)throws Exception{
		String sql = " from ProviderProduct as pp where pp.providerid='"+pvid+"' and pp.productid='"+productid+"' ";
		ProviderProduct pp = (ProviderProduct) EntityManager.find(sql);
		return pp;
	}
	

	public List getProductProviderCompare(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {		
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pp.id,p.pid,p.pname,pp.price,p.vocation,p.abcsort,p.province,p.city,p.areas from Provider as p,ProviderProduct as pp "
				+ pWhereClause + " order by pp.pricedate ";
		List ls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return ls;
	}
	
	public List getProductProviderCompare(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {		
		String hql = "select pp.id,p.pid,p.pname,pp.price,p.vocation,p.abcsort,p.province,p.city,p.areas from Provider as p,ProviderProduct as pp "
				+ pWhereClause + " order by pp.pricedate ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	
	public void addProviderProduct(Object providerproduct) throws Exception {
		
		EntityManager.save(providerproduct);
		
	}

	
	public ProviderProduct getProvideProductByID(Long id) throws Exception {
		ProviderProduct pl = null;
		String sql = " from ProviderProduct as pl where pl.id=" + id;
		pl = (ProviderProduct) EntityManager.find(sql);
		return pl;
	}
	
	
	public void DelProviderProduct(Long id)throws Exception{
		
		String sql="delete from provider_product where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	

//	public String DelProductProvider(String pvid,String pdid)throws Exception{
//		
//		String sql="delete from provider_product where ProviderID='"+pvid+"' and ProductID='"+pdid+"'";
//		System.out.println("======="+sql);
//		EntityManager.updateOrdelete(sql);
//		
//	}
	
	
	public void UpdProviderProduct(Double price,String providerid,String productid)throws Exception{
		
		String sql="update provider_product set price="+price+",pricedate='"+DateUtil.getCurrentDateString()+"' where providerid='"+providerid+"' and productid='"+productid+"'";
		//System.out.println("sql===="+sql);
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public Double getUnitpriceByProvideIDProductID(String providerid,String productid)throws Exception{
		Double p= 0.00;
		String sql="select pp.price from Provider_Product as pp where pp.providerid='"+providerid+"' and pp.productid='"+productid+"'";
		ResultSet rs = EntityManager.query(sql);
		if(rs.getRow()>0){
		do {
		p = rs.getDouble(1);
		} while (rs.next());
		}
		return p;
	}

	
	public int getProviderProductByPPID(String provideid, String productid) throws Exception {
		String sql = "select count(*) from Provider_Product where productid='" + productid+ "' and providerid='"+provideid+"'";
		return EntityManager.getRecordCountQuery(sql);
	}
//	
//	public String updProviderProduct(ProviderProduct pp,String birthday) throws Exception {
//		
////		String sql = "update provide_linkman set pid=" + pl.getPid()
////				+ ",name='" + pl.getName() + "',sex=" + pl.getSex()
////				+ ",wedlock=" + pl.getWedlock() + ",birthday='"
////				+ birthday + "',department='" + pl.getDepartment()
////				+ "',duty='" + pl.getDuty() + "',officetel='"
////				+ pl.getOfficetel() + "',fax='" + pl.getFax() + "',mobile='"
////				+ pl.getMobile() + "',qq='" + pl.getQq() + "',email='"
////				+ pl.getEmail() + "',msn='" + pl.getMsn() + "',addr='"
////				+ pl.getAddr() + "',ismain=" + pl.getIsmain() + ",memo='"
////				+ pl.getMemo() + "' where id=" + pl.getId();
////		System.out.println("-----"+sql);
////		EntityManager.updateOrdelete(sql);
//		
//	}

	public ProviderProduct getProvideProductByNCcode(String nccode) throws Exception {
		ProviderProduct pl = null;
		String sql = " from ProviderProduct as pl where pl.nccode=" + nccode;
		pl = (ProviderProduct) EntityManager.find(sql);
		return pl;
	}
	
}
