package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPact {
	
		//
	public List getPact(int pagesize, String pWhereClause,
	              SimplePageInfo pPgInfo) throws Exception {
	List pls = null;
	int targetPage = pPgInfo.getCurrentPageNo();
	String sql = "select p.id,p.pactcode,p.pacttype,p.cid,p.cdeputy,p.signdate,p.provide,p.pdeputy,p.signaddr,p.pactscopy from Pact as p " +
	pWhereClause + " order by p.signdate desc";
	pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
	return pls;
	}
	
	
	public List searchPact(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String hql = "select p.id,p.pactcode,p.pacttype,p.objsort,p.cid,p.cname,p.cdeputy,p.signdate,p.pdeputy,p.signaddr,p.makeid,p.makedate from Pact as p " +
		pWhereClause + " order by p.signdate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addNewPact(Object pact) throws Exception {
	    
	    EntityManager.save(pact);
	    
	  }

	public void delPact(Integer id)throws Exception{
		
		String sql="delete from Pact where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	  
	 public Pact getPactByID(Integer id) throws Exception {
		Pact p = null;
	    String sql = " from Pact as p where p.id=" + id;
	    p = (Pact) EntityManager.find(sql);
	    return p;
	 }
	 
	 public void updPact(Pact pat)throws Exception{
		 EntityManager.update(pat);
	 }

	public List initPack(int pagesize, String whereSql)throws Exception {
		List list = null;
		String sql = "select p.id,p.pactcode,p.pacttype,p.cdeputy,p.signdate,p.provide,p.pdeputy,p.signaddr,p.pactscopy from Pact as p " +
		whereSql + " order by p.signdate desc";
		list = EntityManager.getAllByHql(sql, 1, pagesize);
		return list;
	}
}
