package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppProductPriceii {

	public void addProductPriceii(Object pp) throws Exception{
		
		EntityManager.save(pp);
		
	}
	
	public void updProductPriceii(ProductPriceii pp) throws Exception{
		
		EntityManager.saveOrUpdate(pp);
		
	}
	
	public List getAllProductPriceii()throws Exception{
		String sql=" from ProductPriceii";
		return EntityManager.getAllByHql(sql);
	}
	
	public void delProductPriceiiByProductID(String pid) throws Exception{		
		String sql="delete from Product_Priceii where productid='"+pid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public ProductPriceii getProductPriceiiById(Long id) throws Exception{
		String sql="from ProductPriceii where id="+id;
		return (ProductPriceii)EntityManager.find(sql);
	}
	
	public List getProductPriceiiByProductID(String pid) throws Exception{
		String sql = " from ProductPriceii where productid='"+pid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public ProductPriceii getProdutPriceByPlcyPIDUnitID(int plcyid,String pid,int unitid)throws Exception{
		String sql=" from ProductPriceii as pp where pp.policyid="+plcyid+" and pp.productid='"+pid+"' and pp.unitid="+unitid+" ";
		return (ProductPriceii)EntityManager.find(sql);
	}
	
	public List getAAAA()throws Exception{
		//String sql = "select productid,unitid,policyid,unitprice from ProductPriceii where policyid=3";
		String sql="from ProductPriceii where policyid=6";
		List ls=EntityManager.getAllByHql(sql);
		return ls;
	}
}
