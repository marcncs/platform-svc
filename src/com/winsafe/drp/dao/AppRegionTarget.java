package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppRegionTarget {
	public List getRegionTargetList(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = " select r,rt from Region as r,RegionTarget as rt  " + pWhereClause
				+ " order by rt.regionid,rt.targettype,rt.targetdate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public boolean chkRegionTarget(Integer regionid,String targetdate,String targettype) throws Exception {
		String sql = " from RegionTarget where regionid=" + regionid + " and targettype='" + targettype + "' and targetdate='" + targetdate + "'";
		RegionTarget rt = (RegionTarget) EntityManager.find(sql);
		if(rt!=null)
			return true;
		return false;
	}
	
	public void insertRegionTarget(RegionTarget rt) throws Exception {
		EntityManager.save(rt);
	}
	
	public RegionTarget getRegionTargetDetailById(int rtid) throws Exception {
		String hql = " select r,rt from Region as r,RegionTarget as rt  where r.id=rt.regionid and rt.id=" + rtid;
		List list = EntityManager.getAllByHql(hql);
		RegionTarget rt = null;
		if(list!=null && !list.isEmpty()){
			Object[] oo = (Object[]) list.get(0);
			Region r = (Region) oo[0];
			rt = (RegionTarget) oo[1];
			rt.setObjcode(r.getId()+"");
			rt.setObjname(r.getSortname());
		}
		return rt;
	}
	
	public RegionTarget getRegionTargetById(int rtid) throws Exception {
		String hql = " from RegionTarget where id=" + rtid;
		return  (RegionTarget) EntityManager.find(hql);
	}
	
	public void updRegionTarget(RegionTarget rt) throws Exception {
		EntityManager.update(rt);
	}
	
	public void delRegionTargetById(int rtid) throws Exception {		
		EntityManager.updateOrdelete("delete from region_target where id=" + rtid);;
	}

	public boolean chkRegionTargetUpd(Integer regionid,String targetdate,Integer otid,String targettype) throws Exception {
		String sql = " from RegionTarget where regionid=" + regionid + " and targettype='" + targettype + "' and targetdate='" + targetdate + "' and id<>" + otid;
		RegionTarget rt = (RegionTarget) EntityManager.find(sql);
		if(rt!=null)
			return true;
		return false;
	}
	
	public RegionTarget getRegionTarget(Integer regionid,String targetdate,String targettype) throws Exception {
		String sql = " from RegionTarget where regionid=" + regionid + " and targettype='" + targettype + "' and targetdate='" + targetdate + "'";
		RegionTarget rt = (RegionTarget) EntityManager.find(sql);
		return rt;
	}
}
