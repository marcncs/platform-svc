package com.winsafe.drp.action.warehouse;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppReceiveIncomeDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.ReceiveIncomeDetail;


public class ToAddReceiveIncomeIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppReceiveIncomeDetail appd = new AppReceiveIncomeDetail();
		ReceiveIncomeDetail pid = appd.getReceiveIncomeDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
}
