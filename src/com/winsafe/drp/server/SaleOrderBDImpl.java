package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderDetail;
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
public class SaleOrderBDImpl extends BaseDealTakeBill{
	private AppSaleOrder aso = new AppSaleOrder();
	
	public SaleOrderBDImpl(UsersBean users, String billno) {
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
		SaleOrder so = aso.getSaleOrderByID(billno);
		AppSaleOrderDetail apid = new AppSaleOrderDetail();
		List<SaleOrderDetail> pils = apid.getSaleOrderDetailBySoid(billno);
		
		ShipmentBill sb = null;
		if ( !pils.isEmpty() && pils != null ){
			sb = new ShipmentBill();
			sb.setId(so.getId());
			sb.setBsort(0);
			sb.setObjectsort(1);
			sb.setCid(so.getCid());
			sb.setCname(so.getCname());
			sb.setCmobile(so.getCmobile());
			sb.setLinkman(so.getReceiveman());
			sb.setTel(so.getReceivemobile()+"/"+so.getReceivetel());
			sb.setReceiveaddr(so.getTransportaddr());
			sb.setRequiredate(so.getConsignmentdate());
			sb.setPaymentmode(so.getPaymentmode());
			sb.setInvmsg(so.getInvmsg());
			sb.setTickettitle(so.getTickettitle());
			sb.setTransportmode(so.getTransportmode());
			sb.setTransportnum("");
			sb.setTotalsum(so.getTotalsum());
			sb.setRemark(so.getRemark());
			sb.setIsaudit(0);
			sb.setMakeorganid(so.getEquiporganid());
			sb.setMakedeptid(so.getMakedeptid());
			sb.setMakeid(so.getMakeid());
			sb.setMakedate(DateUtil.getCurrentDate());
			sb.setInwarehouseid("");
			sb.setIstrans(0);
			sb.setIsblankout(0);
			sb.setKeyscontent(so.getKeyscontent());	

			ShipmentBillDetail sbd = null;			
			for (SaleOrderDetail pid : pils ) {
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
					sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"shipment_bill_detail", 0, "")));
					sbd.setSbid(pid.getSoid());
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(pid.getWarehouseid());
					sbd.setUnitid(pid.getUnitid());
					sbd.setBatch(pid.getBatch());
					sbd.setUnitprice(pid.getTaxunitprice());
					sbd.setQuantity(pid.getQuantity());
					sbd.setDiscount(pid.getDiscount());
					sbd.setTaxrate(pid.getTaxrate());
					sbd.setSubsum(pid.getSubsum());
					appsbd.addShipmentBillDetail(sbd);
				}
			}
			appsb.addShipmentBill(sb);
		}
		aso.updIsEndcase(billno, users.getUserid(), 1);
	}
	
	
	private void cancelEndCaseSaleOrder() throws Exception{
		
		appsb.delShipmentBill(billno);
		appsbd.delShipmentProductBillBySbID(billno);
		aso.updIsEndcase(billno, users.getUserid(), 0);
	}

	
}
