package com.winsafe.app.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.app.pojo.AppUpdate;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppUpdateDao {
	public void addAppUpdate(AppUpdate d) throws Exception {		
		EntityManager.save(d);		
	}

	public void updAppUpdate(AppUpdate appUpdate)  throws HibernateException{
		EntityManager.saveOrUpdate(appUpdate);
		
	}

	public List getAppUpdates(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql=" from AppUpdate as a "+whereSql +" order by a.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public AppUpdate getAppUpdateById(String id) {
		return (AppUpdate)EntityManager.find("from AppUpdate where id="+id);
	}
	
	public boolean checkAppVersion(String publishName, String appVersion) {
		String sql = "select count(id) from AppUpdate where publishName='" + publishName + "' and appVersion > '" +appVersion+ "'";
		return EntityManager.getRecordCount(sql) > 0;
	}
	
	public AppUpdate getLatestAppUpdate(String publishName) throws Exception {
		String hql=" from AppUpdate as a where a.publishName = '"+publishName+"' order by a.appVersion desc";
		return (AppUpdate)EntityManager.find(hql);
	}
	
	public void updDownloadCountById(String id) throws Exception {
		String hql="update APP_UPDATE set downloadcount = downloadcount + 1 where id='" + id+"'";
		EntityManager.executeUpdate(hql);
	}
}
