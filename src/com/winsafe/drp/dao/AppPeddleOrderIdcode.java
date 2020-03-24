package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppPeddleOrderIdcode {

	public void addPeddleOrderIdcode(PeddleOrderIdcode pii) throws Exception{
		
		EntityManager.save(pii);
		
	}
	
	public void updPeddleOrderIdcode(PeddleOrderIdcode pii) throws Exception{
		
		EntityManager.update(pii);
		
	}
	
	public PeddleOrderIdcode getPeddleOrderIdcodeById(Long id) throws Exception{
		String sql = "from PeddleOrderIdcode where id="+id;
		return (PeddleOrderIdcode)EntityManager.find(sql);
		}
	
	public void delPeddleOrderIdcodeByPidBatch(String productid, String batch) throws Exception{
		
		String sql="delete from peddle_order_idcode where  productid='"+productid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getPeddleOrderIdcodeByTtid(String poid) throws Exception{
		String sql = "from PeddleOrderIdcode where  poid='"+poid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getPeddleOrderIdcodeByPidBatch(String productid, String poid, String batch) throws Exception{
		String sql = "from PeddleOrderIdcode where productid='"+productid+"' and poid='"+poid+"' and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
}
