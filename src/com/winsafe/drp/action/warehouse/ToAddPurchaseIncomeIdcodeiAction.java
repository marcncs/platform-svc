package com.winsafe.drp.action.warehouse;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.AppPurchaseIncomeIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.PurchaseIncomeDetail;

public class ToAddPurchaseIncomeIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppPurchaseIncomeDetail appd = new AppPurchaseIncomeDetail();
		PurchaseIncomeDetail pid = appd.getPurchaseIncomeDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppPurchaseIncomeIdcode apdidcode = new AppPurchaseIncomeIdcode();
		return apdidcode.getPurchaseIncomeIdcodeByPidBatch(bbd.getProductid(), billid, 0);
	}
}
