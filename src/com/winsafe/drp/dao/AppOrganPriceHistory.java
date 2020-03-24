package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppOrganPriceHistory {
	
	public List getOrganPriceHistory(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pi = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from OrganPriceHistory as oh "
				+ pWhereClause + " order by oh.modifydate desc ";
		pi = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pi;
	}

	public void addOrganPriceHistory(OrganPriceHistory pp) throws Exception {
		
		EntityManager.save(pp);
		
	}

	public void updOrganPriceHistory(OrganPriceHistory pp) throws Exception {
		
		EntityManager.saveOrUpdate(pp);
		
	}

	public void delOrganPriceHistoryByOIDPID(String oid, String pid)
			throws Exception {
		
		String sql = "delete from Organ_Price where organid='" + oid
				+ "' and productid='" + pid + "'";
		EntityManager.updateOrdelete(sql);
		
	}

//	public void delOrganPriceHistoryByProductID(String pid) throws Exception {
//		
//		String sql = "delete from Organ_Price where productid='" + pid + "'";
//		EntityManager.updateOrdelete(sql);
//		
//	}

	public OrganPriceHistory getOrganPriceHistoryByOidPidUidPlid(String oid, String pid,Integer uid,Long plid )
			throws Exception {
		String sql = " from OrganPriceHistory as op where op.organid='" + oid
				+ "' and op.productid='" + pid + "' and op.unitid="+uid+" and op.policyid="+plid+" ";
		OrganPriceHistory bank = (OrganPriceHistory) EntityManager.find(sql);
		return bank;
	}

	public OrganPriceHistory getOrganPriceHistoryById(Long id) throws Exception {
		String sql = "from OrganPriceHistory where id=" + id;
		OrganPriceHistory bank = (OrganPriceHistory) EntityManager.find(sql);
		return bank;
	}

	public List getOrganPriceHistoryByProductID(String wheresql) throws Exception {
		List list = null;
		String sql = " from OrganPriceHistory " + wheresql;
		list = EntityManager.getAllByHql(sql);
		return list;
	}
}
