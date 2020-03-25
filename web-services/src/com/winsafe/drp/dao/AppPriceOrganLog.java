package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppPriceOrganLog {

	public void addPriceOrganLog(PriceOrganLog p) throws Exception{
		
		EntityManager.save(p);
		
	}
	
	public void updPriceOrganLog(PriceOrganLog p) throws Exception{
		
		EntityManager.saveOrUpdate(p);
		
	}
	
	
	public void delPriceOrganLogByPid(String productid) throws Exception{
		
		String sql="delete from Price_Organ_Log where productid='"+productid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public List getPriceOrganLogByPid(String pid) throws Exception{
		List list = null;
		String sql = "from PriceOrganLog where productid='"+pid+"'";
		list = EntityManager.getAllByHql(sql);
		return list;
	}
}
