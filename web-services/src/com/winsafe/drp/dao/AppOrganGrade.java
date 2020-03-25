package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganGrade {

	public List getOrganGrade(HttpServletRequest request, String pWhereClause) throws Exception {
		String hql = " from OrganGrade  "+ pWhereClause + " order by id ";
		return PageQuery.hbmQuery(request, hql);
	}

	public void addOrganGrade(Object pp) throws Exception {		
		EntityManager.save(pp);		
	}

	public List getAllOrganGrade() throws Exception {
		String sql = " from OrganGrade as mg ";
		return EntityManager.getAllByHql(sql);
	}
	
	

	public OrganGrade getOrganGradeByID(int id) throws Exception {
		String sql = " from OrganGrade where id=" + id;
		return (OrganGrade) EntityManager.find(sql);
	}
	
	public String getOrganGradeName(int id) throws Exception {
		OrganGrade og = getOrganGradeByID(id);
		if ( og == null ){
			return "";
		}
		return og.getGradename();
	}
	

	public void updOrganGrade(OrganGrade w) throws Exception {		
		EntityManager.update(w);		
	}
	
	public OrganGrade getOrganGradeByID(Integer id) throws Exception {
		String sql = " from OrganGrade where id=" + id;
		return (OrganGrade) EntityManager.find(sql);
	}
	
	public void delOrganGrade(int id) throws Exception {
		String sql = " delete from organ_Grade where id="+id;
		EntityManager.updateOrdelete(sql);
	}
	
	public boolean isAready(int id) throws Exception {	
		String sql = " select count(*) from Organ where rate=" + id;
		int i  =EntityManager.getRecordCount(sql);
		if ( i>0 ){
			return true;
		}
		return false;
	}


}
