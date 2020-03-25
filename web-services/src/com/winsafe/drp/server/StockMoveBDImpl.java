package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author jerry
 * @version 2009-8-25 下午06:05:36 
 * www.winsafe.cn 
 */
public class StockMoveBDImpl extends BaseDealTakeBill{
	private AppStockMove aso = new AppStockMove();
	
	public StockMoveBDImpl(UsersBean users, String billno) {
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
		StockMove so = aso.getStockMoveByID(billno);
		OrganService ao = new OrganService();
		AppStockMoveDetail apid = new AppStockMoveDetail();
		List<StockMoveDetail> pils = apid.getStockMoveDetailBySmIDNew(billno);
		
		ShipmentBill sb = null;
		if ( !pils.isEmpty() && pils != null ){
			sb = new ShipmentBill();
			sb.setId(so.getId());
			sb.setBsort(2);
			sb.setObjectsort(0);
			sb.setCid(so.getInorganid());
			sb.setCname(ao.getOrganName(so.getInorganid()));
			sb.setCmobile(so.getOtel());
			sb.setLinkman(so.getOlinkman());
			sb.setTel(so.getOtel());
			sb.setReceiveaddr("");
			sb.setRequiredate(so.getMovedate());
			sb.setPaymentmode(0);
			sb.setInvmsg(0);
			sb.setTickettitle("");
			sb.setTransportmode(so.getTransportmode());
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
			for (StockMoveDetail pid : pils ) {
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
					sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"shipment_bill_detail", 0, "")));
					sbd.setSbid(pid.getSmid());
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(so.getOutwarehouseid());
					sbd.setUnitid(pid.getUnitid());
					sbd.setBatch(pid.getBatch());
					sbd.setUnitprice(0.00);
					sbd.setQuantity(pid.getQuantity());
					sbd.setDiscount(100d);
					sbd.setTaxrate(0d);
					sbd.setSubsum(0d);
					appsbd.addShipmentBillDetail(sbd);
				}
			}
			appsb.addShipmentBill(sb);
		}
		aso.updStockMoveIsShipment(billno, 1, users.getUserid());
		
		String[] param = new String[]{"name","sendtime","billno"};
		String[] values = new String[]{so.getOlinkman(), DateUtil.formatDate(DateUtil.getCurrentDate()), so.getId()};
		MsgService ms = new MsgService(param, values, users, 12);
		ms.addmag(1,so.getOtel());	
	}
	
	
	private void cancelEndCaseSaleOrder() throws Exception{
		
		appsb.delShipmentBill(billno);
		appsbd.delShipmentProductBillBySbID(billno);
		aso.updStockMoveIsShipment(billno, 0, users.getUserid());
	}

	
}
