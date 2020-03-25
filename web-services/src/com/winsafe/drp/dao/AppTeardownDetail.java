package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppTeardownDetail {

	public void addTeardownDetail(TeardownDetail ild)throws Exception{
		
		EntityManager.save(ild);
		
	}
	
	public TeardownDetail getTeardownDetailByID(Long id)throws Exception{
		String sql=" from TeardownDetail as wd where wd.id="+id+"";
		return (TeardownDetail)EntityManager.find(sql);
	}
	
	public List getTeardownDetailByTid(String tid)throws Exception{
		String sql=" from TeardownDetail as wd where wd.tid='"+tid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getWarehouseinidByTid(String tid)throws Exception{
		String sql=" select distinct wd.warehouseinid from TeardownDetail as wd where wd.tid='"+tid+"'";
		return EntityManager.getAllByHql(sql);
	}
	

	public void delTeardownDetailByTid(String id)throws Exception{
		
		String sql="delete from teardown_detail where tid='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
}
