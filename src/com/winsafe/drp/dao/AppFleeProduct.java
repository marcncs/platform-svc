package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppFleeProduct {

	public List getFleeProduct(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = "from FleeProduct  " + pWhereClause
				+ " order by makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getFleeProduct(String pWhereClause) throws Exception {
		String sql = "from FleeProduct  " + pWhereClause
				+ " order by makedate desc";
		return EntityManager.getAllByHql(sql);
	}

	public void addFleeProduct(Object dpd) throws Exception {
		EntityManager.save(dpd);
	}

	public void updFleeProduct(FleeProduct dpd) throws Exception {
		EntityManager.update(dpd);
	}

	public void delFleeProduct(int id) throws Exception {
		String sql = "delete from FleeProduct where id=" + id + "";
		EntityManager.updateOrdelete(sql);

	}

	public FleeProduct getFleeProductByID(int id) throws Exception {
		String sql = " from FleeProduct where id=" + id + "";
		return (FleeProduct) EntityManager.find(sql);
	}

	
}
