package com.winsafe.drp.action.machin;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.AppOtherIncomeDetailAll;
import com.winsafe.drp.dao.AppOtherIncomeIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.OtherIncomeDetail;
import com.winsafe.drp.dao.OtherIncomeDetailAll;

public class ToAddOtherIncomeIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppOtherIncomeDetailAll appd = new AppOtherIncomeDetailAll();
		OtherIncomeDetailAll pid = appd.getOtherIncomeDetailById(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setBatch(pid.getBatch());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppOtherIncomeIdcode apdidcode = new AppOtherIncomeIdcode();
		return apdidcode.getOtherIncomeIdcodeByPidBatch(bbd.getProductid(), billid, 0, bbd.getBatch());
	}
}
