package com.winsafe.drp.server;

import java.util.List;

import com.winsafe.drp.dao.AppSampleBill;
import com.winsafe.drp.dao.AppSampleBillDetail;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.AppSupplySaleMoveDetail;
import com.winsafe.drp.dao.SampleBill;
import com.winsafe.drp.dao.SampleBillDetail;
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
public class SampleBillBDImpl extends BaseDealTakeBill{
	private AppSampleBill asb = new AppSampleBill();
	
	public SampleBillBDImpl(UsersBean users, String billno) {
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
		SampleBill so = asb.findByID(billno);
		AppSampleBillDetail apid = new AppSampleBillDetail();
		List<SampleBillDetail> pils = apid.findBySbid(billno);
		BaseResourceService brs = new BaseResourceService();
		StringBuffer product = new StringBuffer();
		
		ShipmentBill sb = null;
		if ( !pils.isEmpty() && pils != null ){
			sb = new ShipmentBill();
			sb.setId(so.getId());
			sb.setBsort(14);
			sb.setObjectsort(0);
			sb.setCid(so.getCid());
			sb.setCname(os.getOrganName(so.getCid()));
			sb.setCmobile(so.getTel());
			sb.setLinkman(so.getLinkman());
			sb.setTel(so.getTel());
			sb.setReceiveaddr(so.getReceiveaddr());
			sb.setRequiredate(so.getShipmentdate());
			//支付方式,样品不需要费用
			//sb.setPaymentmode(1);
			//sb.setInvmsg(0);
			//sb.setTickettitle(so.get());
			//sb.setTransportmode(so.getTransportmode());
			//sb.setTransportnum("");
			sb.setTotalsum(so.getTotalsum());
			sb.setRemark(so.getRemark());
			sb.setIsaudit(0);
			sb.setMakeorganid(so.getMakeorganid());
			sb.setMakedeptid(so.getMakedeptid());
			sb.setMakeid(so.getMakeid());
			sb.setMakedate(DateUtil.getCurrentDate());
			sb.setInwarehouseid(so.getCid());
			sb.setIstrans(0);
			sb.setIsblankout(0);
			sb.setKeyscontent(so.getKeyscontent());	

			ShipmentBillDetail sbd = null;			
			for (SampleBillDetail pid : pils ) {
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
					sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"shipment_bill_detail", 0, "")));
					sbd.setSbid(pid.getSbid());
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(so.getWarehouseID());
					sbd.setUnitid(pid.getUnitid());
					//默认库位
					sbd.setBatch("000");
					sbd.setUnitprice(pid.getUnitprice());
					sbd.setQuantity(Double.valueOf(pid.getQuantity()));
					sbd.setDiscount(100d);
					sbd.setTaxrate(0d);
					sbd.setSubsum(pid.getSubsum());
					appsbd.addShipmentBillDetail(sbd);
					
					product.append(sbd.getProductname()).append(" ").append(sbd.getQuantity())
					.append(brs.getBaseResourceName("CountUnit", sbd.getUnitid())).append(" ");
				}
			}
			appsb.addShipmentBill(sb);
		}
		asb.updSampleBillIsShipment(billno, 1, users.getUserid());
		
		String[] param = new String[]{"name","sendtime","billno","product"};
		String[] values = new String[]{so.getLinkman(), DateUtil.formatDate(DateUtil.getCurrentDate()), 
				so.getId(), product.toString()};
		MsgService ms = new MsgService(param, values, users, 10);
		ms.addmag(1,so.getTel());	
	}
	
	private void cancelEndCaseSaleOrder() throws Exception{
		appsb.delShipmentBill(billno);
		appsbd.delShipmentProductBillBySbID(billno);
		asb.updSampleBillIsShipment(billno, 0, users.getUserid());
	}

	
}
