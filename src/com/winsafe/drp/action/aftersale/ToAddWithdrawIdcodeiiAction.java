package com.winsafe.drp.action.aftersale;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppWithdrawDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.WithdrawDetail;


public class ToAddWithdrawIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppWithdrawDetail appd = new AppWithdrawDetail();
		WithdrawDetail pid = appd.getWithdrawDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
}
