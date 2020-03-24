package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.OrganTradesDetail;
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
public class OrganTradesTBDImpl extends BaseDealTakeBill{
	private AppOrganTrades aso = new AppOrganTrades();
	
	public OrganTradesTBDImpl(UsersBean users, String billno) {
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
		OrganTrades so = aso.getOrganTradesByIDII(billno);
		AppOrganTradesDetail apid = new AppOrganTradesDetail();
		List<OrganTradesDetail> pils = apid.getOrganTradesDetailByotid(so.getId());
		OrganService os = new OrganService();
		
		ShipmentBill sb = null;
		if ( !pils.isEmpty() && pils != null ){
			sb = new ShipmentBill();
			sb.setId(billno);
			sb.setBsort(9);
			sb.setObjectsort(0);
			sb.setCid(so.getMakeorganid());
			sb.setCname(os.getOrganName(so.getMakeorganid()));
			sb.setCmobile(so.getRtel());
			sb.setLinkman(so.getRlinkman());
			sb.setTel(so.getRtel());
			sb.setReceiveaddr(so.getTransportaddr());
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
			sb.setInwarehouseid(so.getOutwarehouseid());
			sb.setIstrans(0);
			sb.setIsblankout(0);
			sb.setKeyscontent(so.getKeyscontent());	

			ShipmentBillDetail sbd = null;			
			for (OrganTradesDetail pid : pils ) {
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
					sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"shipment_bill_detail", 0, "")));
					sbd.setSbid(billno);
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(so.getPoutwarehouseid());
					sbd.setUnitid(pid.getUnitid());
					sbd.setBatch(pid.getBatch());
					sbd.setUnitprice(pid.getUnitprice());
					sbd.setQuantity(pid.getCanquantity());
					sbd.setDiscount(100d);
					sbd.setTaxrate(0d);
					sbd.setSubsum(pid.getSubsum());
					appsbd.addShipmentBillDetail(sbd);
				}
			}
			appsb.addShipmentBill(sb);
		}
		
		String[] param = new String[]{"name","sendtime","billno"};
		String[] values = new String[]{so.getRlinkman(), DateUtil.formatDate(DateUtil.getCurrentDate()), so.getId()};
		MsgService ms = new MsgService(param, values, users, 16);
		ms.addmag(1,so.getRtel());	
	}
	
	
	private void cancelEndCaseSaleOrder() throws Exception{
		
		appsb.delShipmentBill(billno);
		appsbd.delShipmentProductBillBySbID(billno);
	}

	
}
