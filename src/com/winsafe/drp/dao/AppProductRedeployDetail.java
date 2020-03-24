package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppProductRedeployDetail {
	
	public List getProductRedeployDetailByPid(String productid) throws Exception {
		List pls = null;
		 
		String sql = "from ProductRedeployDetail as p where p.prid='" +productid
				+ "' order by p.id desc";
		pls = EntityManager.getAllByHql(sql);
		return pls;
	}
	
	 

	
	public void addProductRedeployDetail(Object product) throws Exception {
		
		EntityManager.save(product);
		
	}

	
	public Product getProductByID(String id) throws Exception {
		Product p = null;
		String sql = " from ProductRedeployDetail as p where p.id='" + id+"'";
		p = (Product) EntityManager.find(sql);
		return p;
	}
	
	public void delProductRedeployDetailByPid(String productid) throws Exception {
		
		String sql="delete from product_redeploy_detail where prid='"+productid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	 
}
