package com.winsafe.drp.action.aftersale;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppSaleTradesDetail;
import com.winsafe.drp.dao.AppSaleTradesIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.SaleTradesDetail;

public class ToAddSaleTradesIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppSaleTradesDetail appd = new AppSaleTradesDetail();
		SaleTradesDetail pid = appd.getSaleTradesDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppSaleTradesIdcode apdidcode = new AppSaleTradesIdcode();
		return apdidcode.getSaleTradesIdcodeByPidBatch(bbd.getProductid(), billid, 0);
	}
}
