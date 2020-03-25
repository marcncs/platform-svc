package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPaymentMode {

	public List getPaymentMode(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from PaymentMode as pm "
				+ pWhereClause + " order by pm.id ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	public List getPaymentMode(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from PaymentMode as pm "+ pWhereClause + " order by pm.id ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void addPaymentMode(PaymentMode pm) throws Exception {
		EntityManager.save(pm);		
	}
	
	public void updPaymentMode(PaymentMode pm) throws Exception {		
		EntityManager.update(pm);		
	}

	public List getAllPaymentMode() throws Exception {
		String sql = " from PaymentMode as pm ";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public PaymentMode getPaymentModeByID(Integer id) throws Exception {
		String sql = " from PaymentMode where id=" + id;
		return (PaymentMode) EntityManager.find(sql);
	}
	

	

}
