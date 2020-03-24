package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppCar {
	public List getCar(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {

		String hql = "from Car as c " + pWhereClause
				+ " order by c.makedate desc";

		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List<Car> getAllCars(String whereSql) {

		String sql = "from Car as c where "+ whereSql;
		return  EntityManager.getAllByHql(sql);
	}

	public void addCar(Car c) throws Exception {

		EntityManager.save(c);

	}

	public void delCar(String id) throws Exception {

		String sql = "delete from car where id='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public Car getCarByID(String id) throws Exception {
		Car p = null;
		String sql = " from Car as p where p.id='" + id + "'";
		p = (Car) EntityManager.find(sql);
		return p;
	}

	public void updCar(Car c) throws Exception {

		EntityManager.update(c);

	}
}
