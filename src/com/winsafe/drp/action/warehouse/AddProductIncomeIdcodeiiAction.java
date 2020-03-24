package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.winsafe.drp.dao.AppProductIncomeIdcode;
import com.winsafe.drp.dao.ProductIncomeIdcode;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddProductIncomeIdcodeiiAction extends AddBaseIdcodeiiAction {

	public void addIdcode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppProductIncomeIdcode app = new AppProductIncomeIdcode();	
		ProductIncomeIdcode oldpii=app.getProductIncomeIdcodeByidcode(productid, billid, idcode);
		if ( oldpii != null ){
			addMsg(request, response, "该条码已经存在当前列表中，不能新增！");
			return;
		}
		
		ProductIncomeIdcode pi = new ProductIncomeIdcode();
		pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_income_idcode", 0, "")));
		pi.setPiid(billid);
		pi.setProductid(productid);
		pi.setIsidcode(1);
		pi.setWarehousebit("000");
		//pi.setBatch(crs.getBatch(idcode));
		pi.setProducedate(crs.getProduceDate(idcode));
		pi.setVad("");
		pi.setUnitid(2);
		//pi.setUnitid(appcu.getCodeUnitByID(crs.getUcode(idcode)).getUnitid());
		pi.setQuantity(1d);
		pi.setPackquantity(1d);
		//pi.setPackquantity(crs.getRealQuantity(idcode));
		pi.setIdcode(idcode);
		pi.setLcode(crs.getLcode(idcode));
		pi.setStartno(crs.getStartNo(idcode));
		pi.setEndno(crs.getEndNo(idcode));
		pi.setMakedate(DateUtil.getCurrentDate());
		app.addProductIncomeIdcode(pi);
		addMsg(request, response, "条码新增成功！");		
	}
	
}
