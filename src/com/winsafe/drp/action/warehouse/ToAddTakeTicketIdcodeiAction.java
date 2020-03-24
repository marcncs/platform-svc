package com.winsafe.drp.action.warehouse;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.TakeTicketDetail;

public class ToAddTakeTicketIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
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
		return bbd;
	}
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppTakeTicketIdcode apdidcode = new AppTakeTicketIdcode();
		return apdidcode.getTakeTicketIdcodeByPidBatch(bbd.getProductid(), billid, 0, bbd.getBatch());
	}
}
