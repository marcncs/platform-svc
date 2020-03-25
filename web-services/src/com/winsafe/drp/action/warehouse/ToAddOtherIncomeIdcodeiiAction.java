package com.winsafe.drp.action.warehouse;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.OtherIncomeDetail;


public class ToAddOtherIncomeIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppOtherIncomeDetail appd = new AppOtherIncomeDetail();
		OtherIncomeDetail pid = appd.getOtherIncomeDetailById(bdid);		
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setBatch(pid.getBatch());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
}
