package com.winsafe.drp.action.warehouse;

import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.StockAlterMoveDetail;


public class ToAddStockAlterMoveIdcodeiiAction extends ToAddBaseIdcodeDetailiiAction {

	protected BaseBillDetail getBillDetail(int bdid) throws Exception{
		AppStockAlterMoveDetail appd = new AppStockAlterMoveDetail();
		StockAlterMoveDetail pid = appd.getStockAlterMoveDetailByID(bdid);
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
