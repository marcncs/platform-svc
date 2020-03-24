package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-8-8 上午10:06:48 www.winsafe.cn
 */
public class AppSaleForecastDetail {

	@SuppressWarnings("unchecked")
	public List<SaleForecastDetail> findAll(HttpServletRequest request, String whereStr,
			Integer pageSize) throws Exception {
		String hql = " from SaleForecastDetail as s " + whereStr
				+ " order by s.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public SaleForecastDetail findByID(Integer id) throws Exception {
		String hql = " from SaleForecastDetai as s where s.id = " + id;
		return (SaleForecastDetail) EntityManager.find(hql);
	}
	
	public List<SaleForecastDetail> findBySID(Integer sid) throws Exception {
		String hql = " from SaleForecastDetail as s where s.sid = " + sid;
		return  (List<SaleForecastDetail>) EntityManager.getAllByHql(hql);
	}

	public void save(SaleForecastDetail saleForecastDetail) throws Exception {
		EntityManager.save(saleForecastDetail);
	}
	
	public void save(SaleForecastDetail[] saleForecastDetail) throws Exception {
		EntityManager.save(saleForecastDetail);
	}

	public void update(SaleForecastDetail saleForecastDetail) throws Exception {
		EntityManager.update(saleForecastDetail);
	}

	public void delete(SaleForecastDetail saleForecastDetail) throws Exception {
		EntityManager.delete(saleForecastDetail);
	}
	
	public void deleteBySID(Integer sid)throws Exception{
		String hql = "delete from Sale_Forecast_Detail where sid = " + sid;
		EntityManager.updateOrdelete(hql);
	}

	public void delete(Integer id) throws Exception {
		String hql = "delete from Sale_Forecast_Detail where id = " + id;
		EntityManager.updateOrdelete(hql);
	}

	public void deleteAll() throws Exception {
		String hql = "delete from Sale_Forecast_Detail ";
		EntityManager.updateOrdelete(hql);
	}
}
