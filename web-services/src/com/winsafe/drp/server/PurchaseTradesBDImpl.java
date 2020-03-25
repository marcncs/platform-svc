package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseTrades;
import com.winsafe.drp.dao.AppPurchaseTradesDetail;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.PurchaseTrades;
import com.winsafe.drp.dao.PurchaseTradesDetail;
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
public class PurchaseTradesBDImpl extends BaseDealTakeBill{
	private AppPurchaseTrades aso = new AppPurchaseTrades();
	
	public PurchaseTradesBDImpl(UsersBean users, String billno) {
		super(users, billno);
	}
	
	@Override
	protected void deal() throws Exception {
		endCasePurchaseTrades();
	}
	
	@Override
	protected void cancelDeal() throws Exception {
		cancelEndCasePurchaseTrades();
	}

	
	
	private void endCasePurchaseTrades() throws Exception{		
		PurchaseTrades so = aso.getPurchaseTradesByID(billno);
		AppPurchaseTradesDetail apid = new AppPurchaseTradesDetail();
		List<PurchaseTradesDetail> pils = apid.getPurchaseTradesDetailByPtid(billno);
		AppProvider ap = new AppProvider();
		Provider provider = ap.getProviderByID(so.getProvideid());
		
		ShipmentBill sb = null;
		if ( !pils.isEmpty() && pils != null ){
			sb = new ShipmentBill();
			sb.setId(so.getId());
			sb.setBsort(13);
			sb.setObjectsort(2);
			sb.setCid(so.getProvideid());
			sb.setCname(so.getProvidename());
			sb.setCmobile(so.getTel());
			sb.setLinkman(so.getPlinkman());
			sb.setTel(so.getTel());
			sb.setReceiveaddr(provider.getAddr());
			sb.setRequiredate(so.getMakedate());
			sb.setPaymentmode(0);
			sb.setInvmsg(0);
			sb.setTickettitle("");
			sb.setTransportmode(0);
			sb.setTransportnum("");
			sb.setTotalsum(0.00);
			sb.setRemark(so.getRemark());
			sb.setIsaudit(0);
			sb.setMakeorganid(users.getMakeorganid());
			sb.setMakedeptid(users.getMakedeptid());
			sb.setMakeid(users.getUserid());
			sb.setMakedate(DateUtil.getCurrentDate());
			sb.setInwarehouseid("");
			sb.setIstrans(0);
			sb.setIsblankout(0);
			sb.setKeyscontent(so.getKeyscontent());	

			ShipmentBillDetail sbd = null;			
			for (PurchaseTradesDetail pid : pils ) {
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
					sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"shipment_bill_detail", 0, "")));
					sbd.setSbid(pid.getPtid());
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(so.getWarehouseoutid());
					sbd.setUnitid(pid.getUnitid());
					sbd.setBatch(pid.getBatch());
					sbd.setUnitprice(pid.getUnitprice());
					sbd.setQuantity(pid.getQuantity());
					sbd.setDiscount(0.00);
					sbd.setTaxrate(0.00);
					sbd.setSubsum(pid.getSubsum());
					appsbd.addShipmentBillDetail(sbd);
				}
			}
			appsb.addShipmentBill(sb);
		}
	}
	
	
	private void cancelEndCasePurchaseTrades() throws Exception{
		
		appsb.delShipmentBill(billno);
		appsbd.delShipmentProductBillBySbID(billno);
	}

	
}
