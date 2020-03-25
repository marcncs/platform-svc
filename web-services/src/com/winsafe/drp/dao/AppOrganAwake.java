package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganAwake {
	
	public List getOrganAwakeList(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from OrganAwake as oa "
				+ pWhereClause + " order by oa.id desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getOrganAwakeOID(String oid) throws Exception {
		String sql = "  from OrganAwake as oa where oa.organid='"
				+ oid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public OrganAwake getOrganAwakeByOidUserid(String organid, int userid) throws Exception {
		String sql = "  from OrganAwake  where organid='"+organid+"' and userid="+userid;
		return (OrganAwake)EntityManager.find(sql);
	}
	
	
	public OrganAwake getOrganAwakeByID(int id) throws Exception {
		String sql = "  from OrganAwake as oa where oa.id="+id;
		return (OrganAwake)EntityManager.find(sql);
	}
	
	public void addOrganAwake(Object s) throws Exception {		
		EntityManager.save(s);		
	}
	
	public void delOrganAwakeByOID(String oid) throws Exception {		
		String sql = "delete from Organ_Awake where organid='" + oid+"' ";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delOrganAwakeByID(int id) throws Exception {		
		String sql = "delete from Organ_Awake where id=" + id+" ";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public int findOrganAwakeByOid(int wid,Long uid)throws Exception{
		String sql="select count(wv.id) from OrganAwake as wv where wv.wid="+wid+" and userid="+uid;
		return EntityManager.getRecordCount(sql);
	}
}
