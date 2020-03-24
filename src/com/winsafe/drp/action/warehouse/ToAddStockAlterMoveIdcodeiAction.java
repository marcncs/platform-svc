package com.winsafe.drp.action.warehouse;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.StockAlterMoveDetail;

public class ToAddStockAlterMoveIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppStockAlterMoveDetail appd = new AppStockAlterMoveDetail();
		StockAlterMoveDetail pid = appd.getStockAlterMoveDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppStockAlterMoveIdcode apdidcode = new AppStockAlterMoveIdcode();
		return apdidcode.getStockAlterMoveIdcodeByPidBatch(bbd.getProductid(), billid, 0);
	}
}
