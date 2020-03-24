package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppCTitle {

	public void addCTitle(CTitle ca) throws Exception{
		
		EntityManager.save(ca);
		
	}
	
	
	public void addCTitleNoExists(Integer id,String cid,String ctitle)throws Exception{
		String sql="insert into c_title (id,cid,ctitle) select "+id+",'"+cid+"','"+ctitle+"' where not exists(select * from c_title where cid='"+cid+"' and ctitle='"+ctitle+"')";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updCTitle(CTitle ca) throws Exception{
		
		EntityManager.saveOrUpdate(ca);
		
	}
	
	
	public void delCTitle(Integer id) throws Exception{
		
		String sql="delete from CTitle where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public CTitle getCTitleById(Integer id) throws Exception{
		String sql="from CTitle where id="+id;
		CTitle ct=(CTitle)EntityManager.find(sql);
		return ct;
	}
	
	public List getCTitleByCid(String cid) throws Exception{
		String sql = "from CTitle where cid='"+cid+"'";
		return EntityManager.getAllByHql(sql);
		
	}
}
