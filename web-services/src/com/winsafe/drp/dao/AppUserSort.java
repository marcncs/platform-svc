package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppUserSort {
	public void addUserSort(UserSort cs) throws Exception{
		
		EntityManager.save(cs);
		
	}
	
	public void updUserSort(UserSort cs) throws Exception{
		
		EntityManager.update(cs);
		
	}
	
	
	public void delUserSortBySortid(int sortid) throws Exception{
		
		String sql="delete from user_sort where sortid="+sortid;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public UserSort getUserSortById(Long id) throws Exception{
		UserSort UserSort=null;
		String sql="from UserSort where id="+id;
		UserSort=(UserSort)EntityManager.find(sql);
		return UserSort;
	}
	
	public List getUserSortBySortId(Long sortid) throws Exception{
		String sql="from UserSort where sortid="+sortid;
		return EntityManager.getAllByHql(sql);
	}
}
