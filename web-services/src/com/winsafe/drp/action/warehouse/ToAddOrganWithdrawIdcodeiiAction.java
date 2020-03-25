package com.winsafe.drp.action.warehouse;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.OrganWithdrawDetail;


public class ToAddOrganWithdrawIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppOrganWithdrawDetail appd = new AppOrganWithdrawDetail();
		OrganWithdrawDetail pid = appd.getOrganWithdrawDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getRatifyquantity());
		bbd.setBatch(pid.getBatch());
		bbd.setUnitid(pid.getUnitid());
		bbd.setSpecmode(pid.getSpecmode());
		return bbd;
	}
}
