package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppRegionUsers {

	public void insertRegionUser(RegionUsers regionUsers) {
		EntityManager.save(regionUsers);
	}

	public List getRegionUsers(HttpServletRequest request, int pageSize,
			String whereSql) throws Exception {
		String hql = " from RegionUsers as ru " + whereSql
				+ " order by ru.rid desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public List getRegionUsersByRid(Long pid) {
		String sql = "  from RegionUsers as u where u.rid=" + pid;
		return EntityManager.getAllByHql(sql);
	}

	public void del(Long is) throws Exception {
		String sql = "delete from region_users  where id=" + is + "";
		EntityManager.updateOrdelete(sql);
	}
	
	public int getCountByRegioncode(Long dcode) throws Exception {
		String sql = "select count(p.rid) from RegionUsers as p where p.rid='"
				+ dcode + "'";
		return EntityManager.getRecordCount(sql);
	}

	public RegionUsers getRegionUsersByUserid(String ud) {
		String hql = " from RegionUsers as ru where ru.userid = '" + ud+ "'";
		return (RegionUsers) EntityManager.find(hql);
	}
	
	public RegionUsers getRegionUsersByUserLoginAndUsername(String userlogin,String username) {
		String hql = " from RegionUsers as ru where ru.userlogin = '" + userlogin+ "' and  ru.username='"+username+"'";
		return (RegionUsers) EntityManager.find(hql);
	}
	
	public RegionUsers getRegionUsersByUserLogin(String userlogin) {
		String hql = " from RegionUsers as ru where ru.userlogin = '" + userlogin+ "'";
		return (RegionUsers) EntityManager.find(hql);
	}

}
