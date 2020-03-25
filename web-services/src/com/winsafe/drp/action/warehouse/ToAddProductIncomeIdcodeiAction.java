package com.winsafe.drp.action.warehouse;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.AppProductIncomeIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.ProductIncomeDetail;

public class ToAddProductIncomeIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppProductIncomeDetail appd = new AppProductIncomeDetail();
		ProductIncomeDetail pid = appd.getProductIncomeDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppProductIncomeIdcode apdidcode = new AppProductIncomeIdcode();
		return apdidcode.getProductIncomeIdcodeByPidBatch(bbd.getProductid(), billid, 0);
	}
}
