package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.dao.AppSaleTradesDetail;
import com.winsafe.drp.dao.SaleTrades;
import com.winsafe.drp.dao.SaleTradesDetail;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author jerry
 * @version 2009-8-25 下午06:05:36 
 * www.winsafe.cn 
 */
public class SaleTradesBDImpl extends BaseDealTakeBill{
	private AppSaleTrades aso = new AppSaleTrades();
	
	public SaleTradesBDImpl(UsersBean users, String billno) {
		super(users, billno);
	}
	
	@Override
	protected void deal() throws Exception {
		endCaseSaleTrades();
	}
	
	@Override
	protected void cancelDeal() throws Exception {
		cancelEndCaseSaleTrades();
	}

	
	
	private void endCaseSaleTrades() throws Exception{		
		SaleTrades so = aso.getSaleTradesByID(billno);
		AppSaleTradesDetail apid = new AppSaleTradesDetail();
		List<SaleTradesDetail> pils = apid.getSaleTradesDetailByStid(billno);
		
		ShipmentBill sb = null;
		if ( !pils.isEmpty() && pils != null ){
			sb = new ShipmentBill();
			sb.setId(so.getId());
			sb.setBsort(11);
			sb.setObjectsort(1);
			sb.setCid(so.getCid());
			sb.setCname(so.getCname());
			sb.setCmobile(so.getCmobile());
			sb.setLinkman(so.getClinkman());
			sb.setTel(so.getTel());
			sb.setReceiveaddr(so.getSendaddr());
			sb.setRequiredate(so.getTradesdate());
			sb.setPaymentmode(0);
			sb.setInvmsg(0);
			sb.setTickettitle("");
			sb.setTransportmode(0);
			sb.setTransportnum("");
			sb.setTotalsum(0.00);
			sb.setRemark(so.getRemark());
			sb.setIsaudit(0);
			sb.setMakeorganid(so.getMakeorganid());
			sb.setMakedeptid(so.getMakedeptid());
			sb.setMakeid(so.getMakeid());
			sb.setMakedate(DateUtil.getCurrentDate());
			sb.setInwarehouseid("");
			sb.setIstrans(0);
			sb.setIsblankout(0);
			sb.setKeyscontent(sb.getId()+","+sb.getCid()+","+sb.getCname()+","+sb.getCmobile());	

			ShipmentBillDetail sbd = null;			
			for (SaleTradesDetail pid : pils ) {
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
					sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"shipment_bill_detail", 0, "")));
					sbd.setSbid(pid.getStid());
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(so.getWarehouseid());
					sbd.setUnitid(pid.getUnitid());
					sbd.setBatch(pid.getBatch());
					sbd.setUnitprice(0.00);
					sbd.setQuantity(pid.getQuantity());
					sbd.setDiscount(0.00);
					sbd.setTaxrate(0.00);
					sbd.setSubsum(0.00);
					appsbd.addShipmentBillDetail(sbd);
				}
			}
			appsb.addShipmentBill(sb);
		}
	}
	
	
	private void cancelEndCaseSaleTrades() throws Exception{
		
		appsb.delShipmentBill(billno);
		appsbd.delShipmentProductBillBySbID(billno);
	}

	
}
