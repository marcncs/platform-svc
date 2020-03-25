package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSaleIndent {
	public List<SaleIndent> getSaleIndent(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {

		String hql = "from SaleIndent as si "
				+ pWhereClause + " order by si.makedate desc";
		
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	


	public void addSaleIndent(SaleIndent si) throws Exception {
		
		EntityManager.save(si);
		
	}

	public void delSaleIndent(String id) throws Exception {
		
		String sql = "delete from sale_indent where id='" + id+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	public SaleIndent getSaleIndentByID(String id) throws Exception {
		SaleIndent p = null;
		String sql = " from SaleIndent as p where p.id='" + id+"'";
		p = (SaleIndent) EntityManager.find(sql);
		return p;
	}
	
	public void updSaleIndent(SaleIndent si) throws Exception {
		
		EntityManager.update(si);
		
	}



	public List<SaleIndent> getSaleIndent(String whereSql) {
		String hql = "from SaleIndent as si "
			+ whereSql + " order by si.makedate desc";
		return EntityManager.getAllByHql(hql);
	}
}
