package com.winsafe.drp.action.warehouse;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.OtherShipmentBillDetail;


public class ToAddOtherShipmentBillIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

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
}
