package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppProductProperty {
	public void addProductProperty(Object pp) throws Exception {
		
		EntityManager.save(pp);
		
	}

	public List getProductPropertyByProductID(String pid) throws Exception {
		List ls = null;
		String sql = "select p.id,p.productid,p.propertycode from ProductProperty as p where p.productid='"
				+ pid+"'";
		ls = EntityManager.getAllByHql(sql);
		return ls;
	}

	public void delProductPropertyByProductID(String pid) throws Exception {
		
		String sql = "delete from product_property where ProductID='" + pid+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	
}
