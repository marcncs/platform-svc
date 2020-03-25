package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppCAddr {

	public void addCAddr(CAddr ca) throws Exception{
		
		EntityManager.save(ca);
		
	}
	
	
	public void addCAddrNoExists(Integer id,String cid,String caddr)throws Exception{
		String sql="insert into c_addr (id,cid,caddr) select "+id+",'"+cid+"','"+caddr+"' where not exists(select * from c_addr where  cid='"+cid+"' and caddr='"+caddr+"')";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updCAddr(CAddr ca) throws Exception{
		
		EntityManager.saveOrUpdate(ca);
		
	}
	
	
	public void delCAddr(Integer id) throws Exception{
		
		String sql="delete from CAddr where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public Bank getCAddrById(Integer id) throws Exception{
		Bank bank=null;
		String sql="from CAddr where id="+id;
		bank=(Bank)EntityManager.find(sql);
		return bank;
	}
	
	public List getCAddrByCid(String cid) throws Exception{
		String sql = "from CAddr where cid='"+cid+"'";
		return EntityManager.getAllByHql(sql);
		
	}
}
