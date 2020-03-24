package com.winsafe.drp.action.warehouse;

import java.util.List;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiAction;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppStockMoveIdcode;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.StockMoveDetail;

public class ToAddStockMoveIdcodeiAction extends ToAddBaseIdcodeDetailiAction {

	
	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppStockMoveDetail appd = new AppStockMoveDetail();
		StockMoveDetail pid = appd.getStockMoveDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		return bbd;
	}
	
	protected List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception{
		AppStockMoveIdcode apdidcode = new AppStockMoveIdcode();
		return apdidcode.getStockMoveIdcodeByPidBatch(bbd.getProductid(), billid, 0);
	}
}
