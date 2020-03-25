package com.winsafe.drp.action.warehouse;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.AppOrganTradesPIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.OrganTradesDetail;

public class ToAddOrganTradesPIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppOrganTradesDetail appd = new AppOrganTradesDetail();
		OrganTradesDetail pid = appd.getOrganTradesDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setBatch(pid.getBatch());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppOrganTradesPIdcode apdidcode = new AppOrganTradesPIdcode();
		return apdidcode.getOrganTradesPIdcodeByPidBatch(bbd.getProductid(), billid, 0, bbd.getBatch());
	}
}
