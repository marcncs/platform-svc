package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppCodeUnit {
	
	public List getCodeUnitList(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from CodeUnit  "+ pWhereClause + " order by ucode desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	
	public CodeUnit getCodeUnitByID(String id) throws Exception {
		String sql = "  from CodeUnit where ucode='"+id+"'";
		return (CodeUnit)EntityManager.find(sql);
	}
	
	public void addCodeUnit(CodeUnit cu) throws Exception {		
		EntityManager.save(cu);		
	}
	
	public void updCodeUnit(CodeUnit cu) throws Exception {		
		EntityManager.update(cu);		
	}
	
	public void delCodeUnitByID(String ucode) throws Exception {		
		String sql = "delete from code_unit where ucode='" + ucode+"' ";
		EntityManager.updateOrdelete(sql);		
	}
	
}
