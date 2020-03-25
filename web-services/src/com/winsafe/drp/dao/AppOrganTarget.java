package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganTarget {
	public List getOrganTargetList(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = " select o,ot from Organ as o,OrganTarget as ot  " + pWhereClause
				+ " order by ot.organid,ot.targettype,ot.targetdate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public boolean chkOrganTarget(String organid,String targetdate,String targettype) throws Exception {
		String sql = " from OrganTarget where organid='" + organid + "' and targettype='" + targettype + "' and targetdate='" + targetdate + "'";
		OrganTarget ot = (OrganTarget) EntityManager.find(sql);
		if(ot!=null)
			return true;
		return false;
	}
	
	public void insertOrganTarget(OrganTarget ot) throws Exception {
		EntityManager.save(ot);
	}
	
	public OrganTarget getOrganTargetDetailById(int otid) throws Exception {
		String hql = " select o,ot from Organ as o,OrganTarget as ot  where o.id=ot.organid and ot.id=" + otid;
		List list = EntityManager.getAllByHql(hql);
		OrganTarget ot = null;
		if(list!=null && !list.isEmpty()){
			Object[] oo = (Object[]) list.get(0);
			Organ o = (Organ) oo[0];
			ot = (OrganTarget) oo[1];
			ot.setObjcode(o.getOecode());
			ot.setObjname(o.getOrganname());
		}
		return ot;
	}
	
	public OrganTarget getOrganTargetById(int otid) throws Exception {
		String hql = " from OrganTarget where id=" + otid;
		return  (OrganTarget) EntityManager.find(hql);
	}
	
	public void updOrganTarget(OrganTarget ot) throws Exception {
		EntityManager.update(ot);
	}
	
	public void delOrganTargetById(int otid) throws Exception {		
		EntityManager.updateOrdelete("delete from organ_target where id=" + otid);;
	}

	public boolean chkOrganTargetUpd(String organid,String targetdate,Integer otid,String targettype) throws Exception {
		String sql = " from OrganTarget where organid='" + organid + "' and targettype='" + targettype + "' and targetdate='" + targetdate + "' and id<>" + otid;
		OrganTarget ot = (OrganTarget) EntityManager.find(sql);
		if(ot!=null)
			return true;
		return false;
	}
	
	public OrganTarget getOrganTarget(String organid,String targetdate,String targettype) throws Exception {
		String sql = " from OrganTarget where organid='" + organid + "' and targettype='" + targettype + "' and targetdate='" + targetdate + "'";
		OrganTarget ot = (OrganTarget) EntityManager.find(sql);
		return ot;
	}
}
