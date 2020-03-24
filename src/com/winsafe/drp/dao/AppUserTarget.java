package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppUserTarget {
	@SuppressWarnings("unchecked")
	public List getUserTargetList(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = " select u,ut from Users as u,UserTarget as ut " + pWhereClause
				+ " order by ut.userid,ut.targettype,ut.targetdate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public boolean chkUserTarget(Integer userid,String targetdate,String targettype) throws Exception {
		String sql = " from UserTarget where userid=" + userid + " and targettype='" + targettype + "' and targetdate='" + targetdate + "'";
		UserTarget ut = (UserTarget) EntityManager.find(sql);
		if(ut!=null)
			return true;
		return false;
	}
	
	public void insertUserTarget(UserTarget ut) throws Exception {
		EntityManager.save(ut);
	}
	
	@SuppressWarnings("unchecked")
	public UserTarget getUserTargetDetailById(int utid) throws Exception {
		String hql = " select u,ut from Users as u,UserTarget as ut  where u.userid=ut.userid and ut.id=" + utid;
		List list = EntityManager.getAllByHql(hql);
		UserTarget ut = null;
		if(list!=null && !list.isEmpty()){
			Object[] oo = (Object[]) list.get(0);
			Users u = (Users) oo[0];
			ut = (UserTarget) oo[1];
			ut.setObjcode(u.getUserid()+"");
			ut.setObjname(u.getRealname());
		}
		return ut;
	}
	
	public UserTarget getUserTargetById(int utid) throws Exception {
		String hql = " from UserTarget where id=" + utid;
		return  (UserTarget) EntityManager.find(hql);
	}
	
	public void updUserTarget(UserTarget ut) throws Exception {
		EntityManager.update(ut);
	}
	
	public void delUserTargetById(int utid) throws Exception {		
		EntityManager
		.updateOrdelete("delete from user_target where id=" + utid);;
	}

	public boolean chkUserTargetUpd(Integer userid,String targetdate,Integer utid,String targettype) throws Exception {
		String sql = " from UserTarget where userid=" + userid + " and targettype='" + targettype + "' and targetdate='" + targetdate + "' and id<>" + utid;
		UserTarget ut = (UserTarget) EntityManager.find(sql);
		if(ut!=null)
			return true;
		return false;
	}
	
	public UserTarget getUserTarget(Integer userid,String targetdate,String targettype) throws Exception {
		String sql = " from UserTarget where userid=" + userid + " and targettype='" + targettype + "' and targetdate='" + targetdate + "'";
		UserTarget ut = (UserTarget) EntityManager.find(sql);
		return ut;
	}
	
}
