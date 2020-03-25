package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.winsafe.drp.action.warehouse.AddBaseIdcodeiiAction;
import com.winsafe.drp.dao.AppSaleTradesIdcode;
import com.winsafe.drp.dao.SaleTradesIdcode;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddSaleTradesIdcodeiiAction extends AddBaseIdcodeiiAction {

	public void addIdcode(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		AppSaleTradesIdcode app = new AppSaleTradesIdcode();
		
		SaleTradesIdcode oldpii=app.getSaleTradesIdcodeByidcode(productid, billid, idcode);
		if ( oldpii != null ){
			addMsg(request, response, "该条码已经存在当前列表中，不能新增！");
			return;
		}
		
		
		SaleTradesIdcode pi = new SaleTradesIdcode();
		pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("sale_trades_idcode", 0, "")));
		pi.setStid(billid);
		pi.setProductid(productid);
		pi.setIsidcode(1);
		pi.setWarehousebit(request.getParameter("warehousebit"));
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
		app.addSaleTradesIdcode(pi);
		addMsg(request, response, "条码新增成功！");		
		
	}
	
	
}
