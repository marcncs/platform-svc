package com.winsafe.drp.action.warehouse;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.AppOrganWithdrawIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.OrganWithdrawDetail;

public class ToAddOrganWithdrawIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppOrganWithdrawDetail appd = new AppOrganWithdrawDetail();
		OrganWithdrawDetail pid = appd.getOrganWithdrawDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setBatch(pid.getBatch());
		bbd.setQuantity(pid.getRatifyquantity());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppOrganWithdrawIdcode apdidcode = new AppOrganWithdrawIdcode();
		return apdidcode.getOrganWithdrawIdcodeByPidBatch(bbd.getProductid(), billid, 0, bbd.getBatch());
	}
}
