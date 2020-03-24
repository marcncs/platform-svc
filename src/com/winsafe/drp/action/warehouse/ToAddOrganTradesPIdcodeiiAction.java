package com.winsafe.drp.action.warehouse;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.OrganTradesDetail;


public class ToAddOrganTradesPIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppOrganTradesDetail appd = new AppOrganTradesDetail();
		OrganTradesDetail pid = appd.getOrganTradesDetailByID(bdid);
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
