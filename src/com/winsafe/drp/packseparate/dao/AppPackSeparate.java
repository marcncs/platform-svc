package com.winsafe.drp.packseparate.dao;


import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.packseparate.pojo.PackSeparate;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPackSeparate {
	
	/**
	 * 添加拆包单据
	 * @param packSeparate
	 * @return
	 * @author ryan.xi
	 */
	public void addPackSeparate(PackSeparate packSeparate) throws Exception {
		EntityManager.save(packSeparate);
		
	}
	
	/**
	 * 更新拆包单据
	 * @param packSeparate
	 * @return
	 * @author ryan.xi
	 * @throws Exception 
	 */
	public void updPackSeparate(PackSeparate packSeparate) throws Exception {
		EntityManager.update(packSeparate);
		
	}

	public List getPackSeparates(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql="select p from PackSeparate as p "+whereSql +" order by p.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getPackSeparatesList(HttpServletRequest request, int pageSize,
			String whereSql) throws Exception {
		String sql="select p.*,o.ORGANNAME from Pack_Separate p " +
				"join organ o on o.id=p.ORGANID " +	whereSql;
		if(pageSize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, " id desc ", sql, pageSize);
		} else {
			sql+=" order by p.id desc ";
			return EntityManager.jdbcquery(sql);
		} 
		 
	}
	
	public PackSeparate getPackSeparateById(String id) throws Exception {
		String hql="from PackSeparate where id = '"+id+"'";
		return (PackSeparate)EntityManager.find(hql);
	}

	public void delPackSeparateById(String psid) throws Exception {
		String sql = "delete from PACK_SEPARATE where id='" + psid + "'";
		EntityManager.updateOrdelete(sql);
		
	}

}
