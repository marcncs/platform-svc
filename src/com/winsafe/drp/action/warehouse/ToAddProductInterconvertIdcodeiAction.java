package com.winsafe.drp.action.warehouse;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.AppProductInterconvertIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.ProductInterconvertDetail;

public class ToAddProductInterconvertIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppProductInterconvertDetail appd = new AppProductInterconvertDetail();
		ProductInterconvertDetail pid = appd.getProductInterconvertDetailByID(bdid);
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
		AppProductInterconvertIdcode apdidcode = new AppProductInterconvertIdcode();
		return apdidcode.getProductInterconvertIdcodeByPidBatch(bbd.getProductid(), billid, 0, bbd.getBatch());
	}
}
