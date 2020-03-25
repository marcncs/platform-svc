package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppRepository {

	
	public List getRepositorynew(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from Repository  "
				+ pWhereClause + " order by id desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	

	public void addRepository(Repository r) throws Exception {
		
		EntityManager.save(r);
		
	}

	public Repository getRepositoryByID(String id) throws Exception {
		Repository p = null;
		String sql = " from Repository as p where p.id='" + id+"'";
		p = (Repository) EntityManager.find(sql);
		return p;
	}

	public void updRepository(Repository p) throws Exception {		
		EntityManager.update(p);
		
	}
	
	public void delRepository(String id)throws Exception{
		
		String sql="delete from Repository where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	

}
