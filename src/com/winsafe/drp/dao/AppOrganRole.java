package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganRole {

	
	public List searchOrganRole(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String hql = " from OrganRole "
			+ pWhereClause + " order by organid desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	
	public List getOrganNotInOrganRole(HttpServletRequest request, int pagesize, String pWhereClause)throws Exception{
		String hql=" from Organ as o "+pWhereClause+" ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getRoleNotInOrganRole(HttpServletRequest request, int pagesize, String pWhereClause)throws Exception{
		String hql=" from Role as r "+pWhereClause+" ";
		//System.out.println("-------"+hql);
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	
	public void addOrganRole(OrganRole s) throws Exception {
		EntityManager.save(s);	
	}
	
	public void updOrganRole(OrganRole s) throws Exception {
		EntityManager.update(s);	
	}
	
	public void delOrganRole(int id)throws Exception{		
		String sql="delete from Organ_Role where id="+id+"";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public OrganRole getOrganRoleByID(int id)throws Exception{
		String sql=" from OrganRole  where id="+id+"";
		return (OrganRole) EntityManager.find(sql);
	}

	
}
