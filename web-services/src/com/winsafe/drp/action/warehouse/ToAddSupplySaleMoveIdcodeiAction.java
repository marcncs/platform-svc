package com.winsafe.drp.action.warehouse;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppSupplySaleMoveDetail;
import com.winsafe.drp.dao.AppSupplySaleMoveIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.SupplySaleMoveDetail;

public class ToAddSupplySaleMoveIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppSupplySaleMoveDetail appd = new AppSupplySaleMoveDetail();
		SupplySaleMoveDetail pid = appd.getSupplySaleMoveDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppSupplySaleMoveIdcode apdidcode = new AppSupplySaleMoveIdcode();
		return apdidcode.getSupplySaleMoveIdcodeByPidBatch(bbd.getProductid(), billid, 0);
	}
}
