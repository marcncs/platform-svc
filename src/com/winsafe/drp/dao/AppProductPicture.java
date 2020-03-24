package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppProductPicture {

	public void addProductPicture(ProductPicture pp) throws Exception{
		
		EntityManager.save(pp);
		
	}
	
	public void updProductPicture(ProductPicture pp) throws Exception{
		
		EntityManager.saveOrUpdate(pp);
		
	}
	
	public List getAllProductPicture()throws Exception{
		String sql=" from ProductPicture";
		return EntityManager.getAllByHql(sql);
	}
	
	public void delProductPictureByProductID(String pid) throws Exception{		
		String sql="delete from Product_Picture where productid='"+pid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delProductPictureByID(int id) throws Exception{		
		String sql="delete from Product_Picture where id="+id+"";
		EntityManager.updateOrdelete(sql);		
	}
	
	public ProductPicture getProductPictureById(int id) throws Exception{
		String sql="from ProductPicture where id="+id;
		return (ProductPicture)EntityManager.find(sql);
	}
	
	public List getProductPictureByProductID(String pid) throws Exception{
		String sql = " from ProductPicture where productid='"+pid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
}
