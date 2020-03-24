package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppProductPrice {

	public void addProductPrice(Object pp) throws Exception{
		
		EntityManager.save(pp);
		
	}
	
	public void updProductPrice(ProductPrice pp) throws Exception{
		
		EntityManager.saveOrUpdate(pp);
		
	}
	
	public List getAllProductPrice()throws Exception{
		String sql=" from ProductPrice";
		List ls = EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public void delProductPriceByProductID(String pid) throws Exception{
		
		String sql="delete from Product_Price where productid='"+pid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public ProductPrice getProductPriceById(Long id) throws Exception{
		ProductPrice bank=null;
		String sql="from ProductPrice where id="+id;
		bank=(ProductPrice)EntityManager.find(sql);
		return bank;
	}
	
	public List getProductPriceByProductID(String pid) throws Exception{
		List list = null;
		String sql = " from ProductPrice where productid='"+pid+"'";
		list = EntityManager.getAllByHql(sql);
		return list;
	}
	
	
	public ProductPrice getProdutPriceByPlcyPIDUnitID(int plcyid,String pid,int unitid)throws Exception{
		String sql=" from ProductPrice as pp where pp.policyid="+plcyid+" and pp.productid='"+pid+"' and pp.unitid="+unitid+" ";
		ProductPrice pp= (ProductPrice)EntityManager.find(sql);
		return pp;
	}
	
	public List getAAAA()throws Exception{
		//String sql = "select productid,unitid,policyid,unitprice from ProductPrice where policyid=3";
		String sql="from ProductPrice where policyid=6";
		List ls=EntityManager.getAllByHql(sql);
		return ls;
	}
}
