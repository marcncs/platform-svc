package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppSaleRepair {

	public List getSaleRepair(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from SaleRepair as dp " + pWhereClause
				+ " order by dp.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	public void addSaleRepair(SaleRepair dpd) throws Exception {
		
		EntityManager.save(dpd);
		
	}

	public void updSaleRepair(SaleRepair dpd) throws Exception {
		
		EntityManager.update(dpd);
		
	}

	public void delSaleRepair(String id) throws Exception {
		
		String sql = "delete from sale_repair where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public SaleRepair getSaleRepairByID(String id) throws Exception {
		SaleRepair dp = null;
		String sql = " from SaleRepair where id='" + id + "'";
		dp = (SaleRepair) EntityManager.find(sql);
		return dp;
	}

	
	public void updIsAudit(String id, Long userid, Integer audit)
			throws Exception {
		
		String sql = "update sale_repair set isaudit=" + audit + ",auditid="
				+ userid + ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void BlankoutSaleRepair(String id, Long userid) throws Exception {
		
		String sql = "update sale_repair set isblankout=1,blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsBackTrack(String id, Long userid, Integer audit)
			throws Exception {
		
		String sql = "update sale_repair set isbacktrack=" + audit + ",backtrackid="
				+ userid + ",backtrackdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

}
