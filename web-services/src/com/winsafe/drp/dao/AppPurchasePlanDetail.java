package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppPurchasePlanDetail {

	
	public void addPurchasePlanDetail(Object[] ppd) throws Exception {

		EntityManager.save(ppd);

	}

	public void addPurchasePlanDetail(Object ppd) throws Exception {

		EntityManager.save(ppd);

	}

	
	public List getPurchasePlanDetailByPaID(String ppid) throws Exception {

		String sql = " from PurchasePlanDetail as ppd where ppd.ppid='"
				+ ppid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public List seachPurchasePlanDetailByPaID(String ppid) throws Exception {

		String sql = " from PurchasePlanDetail as ppd where quantity <> changequantity and ppd.ppid='"
				+ ppid + "'";
		return EntityManager.getAllByHql(sql);
	}

	
	public void delPurchasePlanDetailByPpID(String ppid) throws Exception {

		String sql = "delete from purchase_plan_detail where ppid='" + ppid
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	
	public void MakePurchasePlanDetail(Object ppd) throws Exception {

		EntityManager.save(ppd);

	}

	
	public void updPurchasePlanDetailChangeQuantity(String ppid,
			String productid, Double changequantity) throws Exception {

		String sql = "update purchase_plan_detail set changequantity=changequantity+"
				+ changequantity
				+ " where ppid='"
				+ ppid
				+ "' and productid='"
				+ productid + "' ";
		EntityManager.updateOrdelete(sql);

	}

}
