package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppLargess {

	public List getLargess(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String hql = " from Largess as l "
				+ pWhereClause + " order by l.id desc";
		return PageQuery.hbmQuery(request, hql,pagesize);
	}
	
	public void addLargess(Object r) throws Exception {
		
		EntityManager.save(r);
		
	}
	
	public void delLargess(Integer id)throws Exception{
		
		String sql="delete from Largess where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public Largess getLargessByID(Integer id)throws Exception{
		Largess h=new Largess();
		String sql=" from Largess as l where l.id="+id;
		h = (Largess) EntityManager.find(sql);
		return h;
	}
	
	public void updLargess(Largess h)throws Exception{
		
		EntityManager.update(h);
		
	}
}
