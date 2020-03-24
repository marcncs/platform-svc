package com.winsafe.drp.action.warehouse;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.AppOtherShipmentBillIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.OtherShipmentBillDetail;

public class ToAddOtherShipmentBillIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppOtherShipmentBillDetail appd = new AppOtherShipmentBillDetail();
		OtherShipmentBillDetail pid = appd.getOtherShipmentBillDetailByID(bdid);
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
		AppOtherShipmentBillIdcode apdidcode = new AppOtherShipmentBillIdcode();
		return apdidcode.getOtherShipmentBillIdcodeByPidBatch(bbd.getProductid(), billid, 0, bbd.getBatch());
	}
}
