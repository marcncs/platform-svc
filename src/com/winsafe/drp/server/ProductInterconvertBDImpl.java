package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.dao.ProductInterconvertDetail;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author jerry
 * @version 2009-8-25 下午06:05:36 
 * www.winsafe.cn 
 */
public class ProductInterconvertBDImpl extends BaseDealTakeBill{
	private AppProductInterconvert aso = new AppProductInterconvert();
	
	public ProductInterconvertBDImpl(UsersBean users, String billno) {
		super(users, billno);
	}
	
	@Override
	protected void deal() throws Exception {
		endCaseSaleOrder();
	}
	
	@Override
	protected void cancelDeal() throws Exception {
		cancelEndCaseSaleOrder();
	}

	
	
	private void endCaseSaleOrder() throws Exception{		
		ProductInterconvert so = aso.getProductInterconvertByID(billno);
		AppProductInterconvertDetail apid = new AppProductInterconvertDetail();
		List<ProductInterconvertDetail> pils = apid.getProductInterconvertDetailBySamID(billno);
		UsersService us = new UsersService();
		Users u = us.getUsersByid(so.getMakeid());
		
		ShipmentBill sb = null;
		if ( !pils.isEmpty() && pils != null ){
			sb = new ShipmentBill();
			sb.setId(so.getId());
			sb.setBsort(6);
			sb.setObjectsort(3);
			sb.setCid(so.getMakeid().toString());
			sb.setCname(u.getRealname());
			sb.setCmobile(u.getMobile());
			sb.setLinkman(u.getRealname());
			sb.setTel(u.getMobile());
			sb.setReceiveaddr(u.getAddr());
			sb.setRequiredate(so.getMovedate());
			sb.setPaymentmode(0);
			sb.setInvmsg(0);
			sb.setTickettitle("");
			sb.setTransportmode(0);
			sb.setTransportnum("");
			sb.setTotalsum(so.getTotalsum());
			sb.setRemark(so.getRemark());
			sb.setIsaudit(0);
			sb.setMakeorganid(so.getMakeorganid());
			sb.setMakedeptid(so.getMakedeptid());
			sb.setMakeid(so.getMakeid());
			sb.setMakedate(DateUtil.getCurrentDate());
			sb.setInwarehouseid(so.getInwarehouseid());
			sb.setIstrans(0);
			sb.setIsblankout(0);
			sb.setKeyscontent(so.getKeyscontent());	

			ShipmentBillDetail sbd = null;			
			for (ProductInterconvertDetail pid : pils ) {
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
					sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"shipment_bill_detail", 0, "")));
					sbd.setSbid(billno);
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(so.getOutwarehouseid());
					sbd.setUnitid(pid.getUnitid());
					sbd.setBatch(pid.getBatch());
					sbd.setUnitprice(pid.getUnitprice());
					sbd.setQuantity(pid.getQuantity());
					sbd.setDiscount(100d);
					sbd.setTaxrate(0d);
					sbd.setSubsum(pid.getSubsum());
					appsbd.addShipmentBillDetail(sbd);
				}
			}
			appsb.addShipmentBill(sb);
		}
		aso.updProductInterconvertIsShipment(billno, 1, users.getUserid());
	}
	
	
	private void cancelEndCaseSaleOrder() throws Exception{
		
		appsb.delShipmentBill(billno);
		appsbd.delShipmentProductBillBySbID(billno);
		aso.updProductInterconvertIsShipment(billno, 0, users.getUserid());
	}

	
}
