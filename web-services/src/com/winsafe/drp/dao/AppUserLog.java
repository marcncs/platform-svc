package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppUserLog {

	public List getUserLog(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from UserLog  "+ pWhereClause + "  order by id desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getUserLog(String pWhereClause) throws Exception {
		String sql = " from UserLog as ul "
				+ pWhereClause + " order by ul.id desc ";
		return EntityManager.getAllByHql(sql);
	}



}
