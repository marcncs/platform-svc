package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppCustomerMatchOrder {
	
	public CustomerMatchOrder getCmoBySiName(String siname) throws Exception{
		String hql="from CustomerMatchOrder where siname='"+siname+"'";
		return (CustomerMatchOrder)EntityManager.find(hql);
	}
	
	
	public List<CustomerMatchOrder> getCustomerMatchOrders(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = " from CustomerMatchOrder " + pWhereClause
				+ " order by matchorder asc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public void AddCustomerMatchOrder(CustomerMatchOrder cmo) throws Exception {		
		EntityManager.save(cmo);	
	}
	
	public void updCustomerMatchOrder(CustomerMatchOrder cmo)throws Exception {		
		EntityManager.update(cmo);		
	}

}
