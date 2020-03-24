package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppReceiveIncomeIdcode {
	public List<ReceiveIncomeIdcode> getReceiveIncomeIdcodeByPiid(String piid) throws Exception {
		String sql = "from ReceiveIncomeIdcode where  piid='" + piid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public List searchReceiveIncomeIdcode(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from ReceiveIncomeIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
}
