package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppLoanOutDetail {
	
	public void addLoanOutDetail(LoanOutDetail lod)throws Exception{
		
		EntityManager.save(lod);
		
	}
	
	public LoanOutDetail getLoanOutDetailByID(Long id)throws Exception{
		String sql=" from LoanOutDetail as wd where wd.id="+id+"";
		return (LoanOutDetail)EntityManager.find(sql);
	}
	
	public List getLoanOutDetailByLoid(String loid)throws Exception{
		List ls = null;
		String sql=" from LoanOutDetail as wd where wd.loid='"+loid+"'";
		ls=EntityManager.getAllByHql(sql);
		return ls;
	}

	public void delLoanOutDetailByLoid(String loid)throws Exception{
		
		String sql="delete from loan_out_detail where loid='"+loid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public List getWarehouseIdByLoid(String loid) throws Exception{
		List cls = new ArrayList();
		String sql =" select distinct warehouseid from LoanOutDetail where loid='"+loid+"'";
		cls = EntityManager.getAllByHql(sql);
		return cls;
	}
	
	
	  public void updBackQuantity(String loid, String productid, String batch,Double quantity) throws Exception{
		
		    String sql = "update  loan_out_detail set backquantity=backquantity+"+quantity+" where loid='"+loid+"' and productid='" +
		        productid+"' and batch='"+batch+"'";
		    EntityManager.updateOrdelete(sql);

	  }
	  
	  
	  public void updTakeQuantity(String soid,String productid, String batch, double takequantity)throws Exception{
	  	
	  	String sql="update loan_out_detail set takequantity=takequantity +"+takequantity+" where loid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"'";
	  	EntityManager.updateOrdelete(sql);
	  	
	  }
}
