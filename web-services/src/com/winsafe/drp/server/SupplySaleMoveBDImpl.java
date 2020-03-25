package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.AppSupplySaleMoveDetail;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.dao.SupplySaleMoveDetail;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/** 
 * @author jerry
 * @version 2009-8-25 下午06:05:36 
 * www.winsafe.cn 
 */
public class SupplySaleMoveBDImpl extends BaseDealTakeBill{
	private AppSupplySaleMove aso = new AppSupplySaleMove();
	
	public SupplySaleMoveBDImpl(UsersBean users, String billno) {
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
		OrganService os = new OrganService();
		SupplySaleMove so = aso.getSupplySaleMoveByID(billno);
		AppSupplySaleMoveDetail apid = new AppSupplySaleMoveDetail();
		List<SupplySaleMoveDetail> pils = apid.getSupplySaleMoveBySSMID(billno);
		BaseResourceService brs = new BaseResourceService();
		StringBuffer product = new StringBuffer();
		
		ShipmentBill sb = null;
		if ( !pils.isEmpty() && pils != null ){
			sb = new ShipmentBill();
			sb.setId(so.getId());
			sb.setBsort(3);
			sb.setObjectsort(0);
			sb.setCid(so.getInorganid());
			sb.setCname(os.getOrganName(so.getInorganid()));
			sb.setCmobile(so.getOtel());
			sb.setLinkman(so.getOlinkman());
			sb.setTel(so.getOtel());
			sb.setReceiveaddr(so.getTransportaddr());
			sb.setRequiredate(so.getMovedate());
			sb.setPaymentmode(so.getPaymentmode());
			sb.setInvmsg(so.getInvmsg());
			sb.setTickettitle(so.getTickettitle());
			sb.setTransportmode(so.getTransportmode());
			sb.setTransportnum("");
			sb.setTotalsum(so.getStotalsum());
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
			for (SupplySaleMoveDetail pid : pils ) {
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
					sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"shipment_bill_detail", 0, "")));
					sbd.setSbid(pid.getSsmid());
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(so.getOutwarehouseid());
					sbd.setUnitid(pid.getUnitid());
					sbd.setBatch(pid.getBatch());
					sbd.setUnitprice(pid.getSunitprice());
					sbd.setQuantity(pid.getQuantity());
					sbd.setDiscount(100d);
					sbd.setTaxrate(0d);
					sbd.setSubsum(pid.getSsubsum());
					appsbd.addShipmentBillDetail(sbd);
					
					product.append(sbd.getProductname()).append(" ").append(sbd.getQuantity())
					.append(brs.getBaseResourceName("CountUnit", sbd.getUnitid())).append(" ");
				}
			}
			appsb.addShipmentBill(sb);
		}
		aso.updSupplySaleMoveIsShipment(billno, 1, users.getUserid());
		
		String[] param = new String[]{"name","sendtime","billno","product"};
		String[] values = new String[]{so.getOlinkman(), DateUtil.formatDate(DateUtil.getCurrentDate()), 
				so.getId(), product.toString()};
		MsgService ms = new MsgService(param, values, users, 10);
		ms.addmag(1,so.getOtel());	
	}
	
	
	private void cancelEndCaseSaleOrder() throws Exception{
		
		appsb.delShipmentBill(billno);
		appsbd.delShipmentProductBillBySbID(billno);
		aso.updSupplySaleMoveIsShipment(billno, 0, users.getUserid());
	}

	
}
