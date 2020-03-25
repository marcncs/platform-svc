package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppPurchaseInvoiceDetail {

	public void addPurchaseInvoiceDetail(Object pid) throws Exception {
		EntityManager.save(pid);
	}

	public void addPurchaseInvoiceDetail(Object[] pid) throws Exception {
		EntityManager.save(pid);
	}

	public List getPurchaseInvoiceDetailByPbId(Integer piid) throws Exception {
		List pid = null;
		String sql = " from PurchaseInvoiceDetail as d where d.piid=" + piid
				+ "";
		pid = EntityManager.getAllByHql(sql);
		return pid;
	}

	
	public PurchaseInvoiceDetail getPurchaseInvoiceDetailByID(Long id)
			throws Exception {
		PurchaseInvoiceDetail pid = new PurchaseInvoiceDetail();
		String sql = " from PurchaseInvoiceDetail where id=" + id;
		pid = (PurchaseInvoiceDetail) EntityManager.find(sql);
		return pid;
	}

	
	public Double getSumQuantityByProductID(String productid, Long piid)
			throws Exception {
		Double s = Double.valueOf(0.00);
		String sql = "select sum(d.quantity) from PurchaseInvoiceDetail as d where d.productid='"
				+ productid + "' and piid=" + piid;
		s = EntityManager.getdoubleSum(sql);
		return s;
	}

	
	public void delPurchaseInvoiceDetailByPiID(Integer piid) throws Exception {

		String sql = "delete from purchase_invoice_detail where piid=" + piid
				+ "";
		EntityManager.updateOrdelete(sql);

	}

	// 
	// public List getPerDayIncomeReport(String whereSql, int pagesize,
	// SimplePageInfo tmpPgInfo) throws Exception {
	// List ls = null;
	// String sql =
	// "select pid.productid,pid.unitid,sum(pid.quantity),sum(pid.subsum) from PurchaseIncomeDetail as pid, PurchaseIncome as pi "
	// + whereSql + " group by pid.productid order by sum(pid.subsum) desc ";
	//
	// ls =
	// EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
	// return ls;
	// }

	
	public void alreadySettlement(Long id, Long settlementid) throws Exception {

		String sql = "update purchase_invoice_detail set issettlement=1,settlementid="
				+ settlementid + " where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	
	public void delSettlementReturn(Long settlementid) throws Exception {

		String sql = "update purchase_invoice_detail set issettlement =0 where settlementid="
				+ settlementid;
		EntityManager.updateOrdelete(sql);

	}

}
