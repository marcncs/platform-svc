package com.winsafe.app.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.app.pojo.AppUpdateLog;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppUpdateLogDao {
	public void addAppUpdateLog(AppUpdateLog d) throws Exception {		
		EntityManager.save(d);		
	}

	public void updAppUpdateLog(AppUpdateLog appUpdate)  throws HibernateException{
		EntityManager.saveOrUpdate(appUpdate);
		
	}

	public List getAppUpdates(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql=" from AppUpdateLog as a "+whereSql +" order by a.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public AppUpdateLog getAppUpdateById(String id) {
		return (AppUpdateLog)EntityManager.find("from AppUpdateLog where id="+id);
	}
}
