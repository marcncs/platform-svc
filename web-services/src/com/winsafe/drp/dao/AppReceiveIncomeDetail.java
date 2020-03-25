package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppReceiveIncomeDetail {

	public List<ReceiveIncomeDetail> getReceiveIncomeDetailByPbId(String piid) throws Exception {
		String sql = "from ReceiveIncomeDetail as d where d.piid='" + piid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public ReceiveIncomeDetail getReceiveIncomeDetailByID(int id) throws Exception {
		String sql = "from ReceiveIncomeDetail as d where d.id=" + id;
		return (ReceiveIncomeDetail) EntityManager.find(sql);
	}

}
