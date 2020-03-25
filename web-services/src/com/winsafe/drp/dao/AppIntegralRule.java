package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppIntegralRule {

	public List getIntegralRule(HttpServletRequest request, String pWhereClause) throws Exception {
		String hql = " from IntegralRule as pp "
				+ pWhereClause + " order by pp.id ";
		return PageQuery.hbmQuery(request, hql);
	}

	public void addIntegralRule(Object pp) throws Exception {
		
		EntityManager.save(pp);
		
	}

	public List getAllIntegralRule() throws Exception {
		String sql = " from IntegralRule as pp ";
		return EntityManager.getAllByHql(sql);
	}
	


	public IntegralRule getIntegralRuleByID(int id) throws Exception {
		String sql = " from IntegralRule where id=" + id;
		return (IntegralRule) EntityManager.find(sql);
	}
	

	public void updIntegralRule(IntegralRule w) throws Exception {
		
		EntityManager.update(w);
		
	}

}
