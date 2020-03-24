package com.winsafe.drp.action.warehouse;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.ProductIncomeDetail;


public class ToAddProductIncomeIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppProductIncomeDetail appd = new AppProductIncomeDetail();
		ProductIncomeDetail pid = appd.getProductIncomeDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		bbd.setBatch(pid.getBatch());
		return bbd;
	}
}
