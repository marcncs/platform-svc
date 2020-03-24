package com.winsafe.drp.action.aftersale;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppSaleTradesDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.SaleTradesDetail;


public class ToAddSaleTradesIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

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
}
