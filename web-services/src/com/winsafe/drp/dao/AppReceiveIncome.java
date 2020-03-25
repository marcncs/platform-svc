package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppReceiveIncome {
	public List<ReceiveIncome> getReceiveIncome(String whereSql) {
		String sql = "select pi from ReceiveIncome as pi,WarehouseVisit as wv " + whereSql + " order by pi.id desc ";
		return EntityManager.getAllByHql(sql);
	}

	public ReceiveIncome getReceiveIncomeByID(String id) throws Exception {
		ReceiveIncome pi = null;
		String sql = " from ReceiveIncome where id='" + id + "'";
		pi = (ReceiveIncome) EntityManager.find(sql);
		return pi;
	}

	public List<ReceiveIncome> getReceiveIncome(HttpServletRequest request, int pagesize, String whereSql) throws Exception {
		String hql = " from ReceiveIncome " + whereSql + " order by makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

}
