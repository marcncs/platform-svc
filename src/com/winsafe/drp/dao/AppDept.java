package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppDept {
	
	public void InsertDept(Dept d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public List getDept(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		String hql=" from Dept as d "+whereSql +" order by d.id";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void modifyDept(Dept d)throws Exception {		
		EntityManager.update(d);
		
	}
	
	public void delDeptByID(int id)throws Exception{
		EntityManager.updateOrdelete(" delete from dept where id="+id);
	}
	
	public Dept getDeptByID(int id)throws Exception{
		return (Dept)EntityManager.find(" from Dept as d where d.id="+id);
	}
	
	public List getAllDept()throws Exception{
		return EntityManager.getAllByHql("from Dept");
	}
	
	public List getDeptByOID(String oid)throws Exception{
		String sql = " from Dept as d where d.oid='"+oid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getAllDeptName()throws Exception{
		String sql = " select distinct deptname from Dept ";
		return EntityManager.getAllByHql(sql);
	}
	
	
	
}
