package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppCustomerSort {

	public List searchCustomerSort(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "from CustomerSort as c  "
				+ pWhereClause + " order by c.id desc";
		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}
	
	public void addCustomerSort(CustomerSort cs) throws Exception{
		
		EntityManager.save(cs);
		
	}
	
	public void updCustomerSort(CustomerSort cs) throws Exception{
		
		EntityManager.update(cs);
		
	}
	
	
	public void delCustomerSort(Long id) throws Exception{
		
		String sql="delete from CustomerSort where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getSortByUserid(Integer userid) throws Exception{
		String sql = "select c from CustomerSort as c, UserSort as u where c.id=u.sortid and u.userid="+userid;
		return EntityManager.getAllByHql(sql);
	}
	
	public CustomerSort getCustomerSortById(Long id) throws Exception{
		CustomerSort CustomerSort=null;
		String sql="from CustomerSort where id="+id;
		CustomerSort=(CustomerSort)EntityManager.find(sql);
		return CustomerSort;
	}
}
