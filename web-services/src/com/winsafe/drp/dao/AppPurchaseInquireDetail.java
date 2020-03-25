package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppPurchaseInquireDetail {
	
	public void addPurchaseInquireDetail(Object pid) throws Exception {
		
		EntityManager.save(pid);
		
	}

	public List getPurchaseInquireDetailByPiID(Integer piid) throws Exception {
		List ls = null;
		String sql = "select pid.id,pid.piid,pid.productid,pid.productname,pid.specmode,pid.unitid,pid.unitprice,pid.quantity,pid.subsum from PurchaseInquireDetail as pid where pid.piid="
				+ piid;
		ls = EntityManager.getAllByHql(sql);
		return ls;
	}

	public void delPurchaseInquireDetailByPiID(Long piid) throws Exception {
		
		String sql = "delete from purchase_inquire_detail where piid=" + piid;
		EntityManager.updateOrdelete(sql);
		
	}

}
