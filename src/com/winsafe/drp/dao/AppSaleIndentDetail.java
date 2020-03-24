package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppSaleIndentDetail {
	public List searchSaleIndentDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from SaleIndentDetail as si "
				+ pWhereClause + " order by si.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	public void addSaleIndentDetail(SaleIndentDetail si) throws Exception {
		
		EntityManager.save(si);
		
	}

	public void delSaleIndentDetail(String id) throws Exception {
		
		String sql = "delete from sale_indent_detail where siid='" + id+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	public SaleIndentDetail getSaleIndentDetailByID(String id) throws Exception {
		SaleIndentDetail p = null;
		String sql = " from SaleIndentDetail as p where p.id='" + id+"'";
		p = (SaleIndentDetail) EntityManager.find(sql);
		return p;
	}
	
	public List<SaleIndentDetail> getSaleIndentDetailBySIID(String siid) throws Exception {
		List list = null;
		String sql = " from SaleIndentDetail as p where p.siid='" + siid+"'";
		list = EntityManager.getAllByHql(sql);
		return list;
	}
	
	public void updSaleIndentDetail(SaleIndentDetail si) throws Exception {
		
		EntityManager.update(si);
		
	}
}
