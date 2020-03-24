package com.winsafe.drp.action.warehouse;

import com.eitop.platform.tools.upload.Request;
import com.winsafe.drp.action.common.ToAddBaseBarcodeDetailiiAction;
import com.winsafe.drp.action.common.ToAddBaseIdcodeDetailiiAction;
import com.winsafe.drp.dao.AppBarcodeInventoryDetail;
import com.winsafe.drp.dao.BarcodeInventoryDetail;
import com.winsafe.drp.dao.BaseBillDetail;

public class ToAddBarcodeAction extends ToAddBaseBarcodeDetailiiAction {

	public BaseBillDetail getBillDetail(String billid,String pid,String batch) throws Exception{
		AppBarcodeInventoryDetail abid = new AppBarcodeInventoryDetail();
		BarcodeInventoryDetail bid = abid.getDetailByIdAndBatch(billid,pid,batch);
		Object obj[] = abid.getDetailByIdPidAndBatch(billid,pid,batch);
		BaseBillDetail bbd = new BaseBillDetail();
		if (bid != null) {
//			bbd.setBdid(bid.getId());
//			bbd.setProductid(bid.getProductid());
//			bbd.setProductname(bid.getProductname());
//			bbd.setBatch(bid.getBatch());
//			bbd.setQuantity(bid.getQuantity());
//			bbd.setUnitid(bid.getUnitid());
//			bbd.setSpecmode(bid.getSpecmode());
			
			bbd.setProductid((String)obj[0]);
			bbd.setProductname((String)obj[1]);
			bbd.setBatch((String)obj[2]);
			bbd.setQuantity((Double)obj[3]);
			bbd.setUnitid((Integer)obj[4]);
			bbd.setSpecmode((String)obj[5]);
			
		}
		return bbd;
	}
	
}

