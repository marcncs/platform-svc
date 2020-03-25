package com.winsafe.drp.action.warehouse;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.StockMoveDetail;


public class ToAddStockMoveIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppStockMoveDetail appd = new AppStockMoveDetail();
		StockMoveDetail pid = appd.getStockMoveDetailByID(bdid);
		BaseBillDetail bbd = new BaseBillDetail();
		bbd.setBdid(pid.getId());
		bbd.setProductid(pid.getProductid());
		bbd.setProductname(pid.getProductname());
		bbd.setQuantity(pid.getQuantity());
		bbd.setUnitid(pid.getUnitid());
		bbd.setSpecmode(pid.getSpecmode());
		return bbd;
	}
}
