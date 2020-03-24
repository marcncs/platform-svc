package com.winsafe.drp.action.aftersale;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppWithdrawDetail;
import com.winsafe.drp.dao.AppWithdrawIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.WithdrawDetail;

public class ToAddWithdrawIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
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
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppWithdrawIdcode apdidcode = new AppWithdrawIdcode();
		return apdidcode.getWithdrawIdcodeByPidBatch(bbd.getProductid(), billid, 0);
	}
}
