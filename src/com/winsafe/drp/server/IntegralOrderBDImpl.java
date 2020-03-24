package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppIntegralOrderDetail;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.dao.IntegralOrderDetail;
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
public class IntegralOrderBDImpl extends BaseDealTakeBill{
	private AppIntegralOrder aso = new AppIntegralOrder();
	
	public IntegralOrderBDImpl(UsersBean users, String billno) {
		super(users, billno);
	}
	
	@Override
	protected void deal() throws Exception {
		endCaseIntegralOrder();
	}
	
	@Override
	protected void cancelDeal() throws Exception {
		cancelEndCaseIntegralOrder();
	}

	
	
	private void endCaseIntegralOrder() throws Exception{		
		IntegralOrder so = aso.getIntegralOrderByID(billno);
		AppIntegralOrderDetail apid = new AppIntegralOrderDetail();
		List<IntegralOrderDetail> pils = apid.getIntegralOrderDetailByIoid(billno);
		
		ShipmentBill sb = null;
		if ( !pils.isEmpty() && pils != null ){
			sb = new ShipmentBill();
			sb.setId(so.getId());
			sb.setBsort(12);
			sb.setObjectsort(1);
			sb.setCid(so.getCid());
			sb.setCname(so.getCname());
			sb.setCmobile(so.getCmobile());
			sb.setLinkman(so.getReceiveman());
			sb.setTel(so.getReceivemobile());
			sb.setReceiveaddr(so.getTransportaddr());
			sb.setRequiredate(so.getConsignmentdate());
			sb.setPaymentmode(0);
			sb.setInvmsg(0);
			sb.setTickettitle("");
			sb.setTransportmode(so.getTransportmode());
			sb.setTransportnum("");
			sb.setTotalsum(0.00);
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
			for (IntegralOrderDetail pid : pils ) {
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
					sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"shipment_bill_detail", 0, "")));
					sbd.setSbid(pid.getIoid());
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(pid.getWarehouseid());
					sbd.setUnitid(pid.getUnitid());
					sbd.setBatch("");
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
		aso.updIsEndcase(billno, users.getUserid(), 1);
	}
	
	
	private void cancelEndCaseIntegralOrder() throws Exception{
		
		appsb.delShipmentBill(billno);
		appsbd.delShipmentProductBillBySbID(billno);
		aso.updIsEndcase(billno, users.getUserid(), 0);
	}

	
}
