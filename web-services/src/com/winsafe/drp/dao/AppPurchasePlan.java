package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchasePlan {



	public List getPurchasePlan(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pa = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from PurchasePlan as pp "
				+ pWhereClause + " order by pp.id desc ";
		pa = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pa;
	}
	
	public List<PurchasePlan> searchPurchasePlan(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from PurchasePlan as pp "
			+ pWhereClause + " order by pp.id desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	

	
	public List getPurchasePlanAndDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pa = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select distinct(pp) from PurchasePlan as pp,PurchasePlanDetail as ppd "
				+ pWhereClause + " order by pp.id desc ";
		pa = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pa;
	}



	public List getPurchasePlanToPurchase(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String hql = "select pp.id,pp.purchasesort,pp.plandate,pp.plandept,pp.planid from PurchasePlan as pp "
				+ pWhereClause + " order by pp.plandate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	

	public void addPurchasePlan(Object purchaseplan) throws Exception {
		EntityManager.save(purchaseplan);
		
	}


	public PurchasePlan getPurchasePlanByID(String id) throws Exception {
		String sql = " from PurchasePlan as pp where pp.id='" + id + "'";
		return (PurchasePlan) EntityManager.find(sql);
	}


	public void updPurchasePlan(PurchasePlan pp) throws Exception {
		EntityManager.update(pp);
		
	}


	public void updIsRefer(String id) throws Exception {
		
		String sql = "update purchase_plan set isrefer=1 where id='" + id+"'";
		EntityManager.updateOrdelete(sql);
		
	}


	public List waitApprovePurchasePlan(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wpa = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pp.id,pp.purchasesort,pp.plandate,pp.plandept,pp.planid,ppa.actid,ppa.approve from PurchasePlan as pp,PurchasePlanApprove as ppa "
				+ pWhereClause + " order by ppa.approve, pp.plandate desc";
		wpa = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wpa;
	}

	

	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update purchase_plan set approvestatus=" + isapprove
				+ " where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsComplete(String ppid, Integer iscomplete) throws Exception {
		
		String sql = "update purchase_plan set iscomplete=" + iscomplete
				+ " where id = '" + ppid+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public int waitInputPurchasePlanCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from PurchasePlan as pa where pa.isratify=1 and pa.iscomplete=0";
		count = EntityManager.getRecordCount(sql);
		return count;
	}

	
	public void delPurchasePlan(String id)throws Exception{
		
		String sql="delete from purchase_plan where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	public List<PurchasePlan> searchPurchasePlan(String whereSql) {
		String hql = " from PurchasePlan as pp "
			+ whereSql + " order by pp.id desc ";
		return EntityManager.getAllByHql(hql);
	}

}
