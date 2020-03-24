package com.winsafe.drp.action.machin;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.AppOtherIncomeDetailAll;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.OtherIncomeDetail;
import com.winsafe.drp.dao.OtherIncomeDetailAll;


public class ToAddOtherIncomeIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppOtherIncomeDetailAll appd = new AppOtherIncomeDetailAll();
		OtherIncomeDetailAll pid = appd.getOtherIncomeDetailById(bdid);		
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
