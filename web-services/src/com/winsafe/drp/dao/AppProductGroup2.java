package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager; 

public class AppProductGroup2 {

	public List<ProductGroup2> getProductGroup2() throws Exception{
		return EntityManager.getAllByHql("from Product_Group2");
	}
	
	public ProductGroup2 getProductGroup2ByID(String id) throws Exception{
		String sql = "from Product_Group2 as pg2 where pg2.id = '" + id + "'";
		return (ProductGroup2) EntityManager.find(sql);
	}
	
	public List<ProductGroup2> getByManufacturerId(Integer manufacturerId) throws Exception{
		String sql = "FROM ProductGroup2 p WHERE p.id IN (SELECT p2.group2Id FROM PopularProduct p2 WHERE p2.manufacturerId = '" + manufacturerId + "')";
		return EntityManager.getAllByHql(sql);
	}
}
