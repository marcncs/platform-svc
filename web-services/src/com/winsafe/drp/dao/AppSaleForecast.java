package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-8-8 上午10:06:48 www.winsafe.cn
 */
public class AppSaleForecast {

	@SuppressWarnings("unchecked")
	public List<SaleForecast> findAll(HttpServletRequest request, String whereStr,
			Integer pageSize) throws Exception {
		String hql = " from SaleForecast as s " + whereStr
				+ " order by s.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public SaleForecast findByID(Integer id) throws Exception {
		String hql = " from SaleForecast as s where s.id = " + id;
		return (SaleForecast) EntityManager.find(hql);
	}

	public void save(SaleForecast saleForecast) throws Exception {
		EntityManager.save(saleForecast);
	}

	public void update(SaleForecast saleForecast) throws Exception {
		EntityManager.update(saleForecast);
	}

	public void delete(SaleForecast saleForecast) throws Exception {
		EntityManager.delete(saleForecast);
	}

	public void delete(Integer id) throws Exception {
		String hql = "delete from Sale_Forecast as s where s.id = " + id;
		EntityManager.updateOrdelete(hql);
	}

	public void deleteAll() throws Exception {
		String hql = "delete from Sale_Forecast as s ";
		EntityManager.updateOrdelete(hql);
	}
}
