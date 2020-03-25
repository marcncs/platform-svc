package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.PurchaseWithdraw;
import com.winsafe.drp.dao.PurchaseWithdrawDetail;
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
public class PurchaseWithdrawBDImpl extends BaseDealTakeBill{
	private AppPurchaseWithdraw aso = new AppPurchaseWithdraw();
	
	public PurchaseWithdrawBDImpl(UsersBean users, String billno) {
		super(users, billno);
	}
	
	@Override
	protected void deal() throws Exception {
		endCasePurchaseWithdraw();
	}
	
	@Override
	protected void cancelDeal() throws Exception {
		cancelEndCasePurchaseWithdraw();
	}

	
	
	private void endCasePurchaseWithdraw() throws Exception{		
		PurchaseWithdraw so = aso.getPurchaseWithdrawByID(billno);
		AppPurchaseWithdrawDetail apid = new AppPurchaseWithdrawDetail();
		List<PurchaseWithdrawDetail> pils = apid.getPurchaseWithdrawDetailByPWID(billno);
		AppProvider ap = new AppProvider();
		Provider provider = ap.getProviderByID(so.getPid());
		
		ShipmentBill sb = null;
		if ( !pils.isEmpty() && pils != null ){
			sb = new ShipmentBill();
			sb.setId(so.getId());
			sb.setBsort(10);
			sb.setObjectsort(2);
			sb.setCid(so.getPid());
			sb.setCname(so.getPname());
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
			sb.setTotalsum(so.getTotalsum());
			sb.setRemark(so.getWithdrawcause());
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
			for (PurchaseWithdrawDetail pid : pils ) {
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
					sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"shipment_bill_detail", 0, "")));
					sbd.setSbid(pid.getPwid());
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(so.getWarehouseid());
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
		aso.updIsEndCase(billno, users.getUserid(), 1);
	}
	
	
	private void cancelEndCasePurchaseWithdraw() throws Exception{
		
		appsb.delShipmentBill(billno);
		appsbd.delShipmentProductBillBySbID(billno);
		aso.updIsEndCase(billno, users.getUserid(), 0);
	}

	
}
