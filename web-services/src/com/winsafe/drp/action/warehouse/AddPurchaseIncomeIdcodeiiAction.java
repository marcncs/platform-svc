package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.winsafe.drp.dao.AppPurchaseIncomeIdcode;
import com.winsafe.drp.dao.PurchaseIncomeIdcode;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddPurchaseIncomeIdcodeiiAction extends AddBaseIdcodeiiAction {

	
	public void addIdcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppPurchaseIncomeIdcode app = new AppPurchaseIncomeIdcode();
		PurchaseIncomeIdcode oldpii=app.getPurchaseIncomeIdcodeByidcode(productid, billid, idcode);
		if ( oldpii != null ){
			addMsg(request, response, "该条码已经存在当前列表中，不能新增！");
			return;
		}
		
		PurchaseIncomeIdcode pi = new PurchaseIncomeIdcode();
		pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("purchase_income_idcode", 0, "")));
		pi.setPiid(billid);
		pi.setProductid(productid);
		pi.setIsidcode(1);
		pi.setWarehousebit(warehousebit);
		pi.setBatch(crs.getBatch(idcode));
		pi.setProducedate(crs.getProduceDate(idcode));
		pi.setValidate("");
		pi.setUnitid(appcu.getCodeUnitByID(crs.getUcode(idcode)).getUnitid());
		pi.setQuantity(1d);
		pi.setPackquantity(crs.getRealQuantity(idcode));
		pi.setIdcode(idcode);
		pi.setLcode(crs.getLcode(idcode));
		pi.setStartno(crs.getStartNo(idcode));
		pi.setEndno(crs.getEndNo(idcode));
		pi.setMakedate(DateUtil.getCurrentDate());
		app.addPurchaseIncomeIdcode(pi);
		addMsg(request, response, "条码新增成功！");			
	}
}
