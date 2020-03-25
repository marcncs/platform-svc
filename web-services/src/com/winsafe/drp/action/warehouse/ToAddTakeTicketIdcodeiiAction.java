package com.winsafe.drp.action.warehouse;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.TakeTicketDetail;


public class ToAddTakeTicketIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppTakeTicketDetail appd = new AppTakeTicketDetail();
		TakeTicketDetail pid = appd.getTakeTicketDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setBatch(pid.getBatch());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		bbd.setSpecmode(pid.getSpecmode());
		return bbd;
	}
}
