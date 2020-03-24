package com.winsafe.drp.action.warehouse;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.ProductInterconvertDetail;


public class ToAddProductInterconvertIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppProductInterconvertDetail appd = new AppProductInterconvertDetail();
		ProductInterconvertDetail pid = appd.getProductInterconvertDetailByID(bdid);
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
