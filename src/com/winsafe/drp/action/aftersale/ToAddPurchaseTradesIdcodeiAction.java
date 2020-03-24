package com.winsafe.drp.action.aftersale;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppPurchaseTradesDetail;
import com.winsafe.drp.dao.AppPurchaseTradesIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.PurchaseTradesDetail;

public class ToAddPurchaseTradesIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppPurchaseTradesDetail appd = new AppPurchaseTradesDetail();
		PurchaseTradesDetail pid = appd.getPurchaseTradesDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppPurchaseTradesIdcode apdidcode = new AppPurchaseTradesIdcode();
		return apdidcode.getPurchaseTradesIdcodeByPidBatch(bbd.getProductid(), billid, 0);
	}
}
