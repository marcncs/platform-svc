package com.winsafe.drp.action.aftersale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.PurchaseWithdraw;
import com.winsafe.drp.dao.PurchaseWithdrawDetail;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class EndcasePurchaseWithdrawAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid();

		try{
			String id = request.getParameter("id");
			AppPurchaseWithdraw apb = new AppPurchaseWithdraw(); 
			PurchaseWithdraw pb = apb.getPurchaseWithdrawByID(id);

			if(pb.getIsendcase()==1){
	          	 String result = "databases.record.already";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			if(pb.getIsaudit()==0){
	          	 String result = "databases.record.nooperator";
	               request.setAttribute("result", result);
	               return new ActionForward("/sys/lockrecordclose.jsp");
	           }
			
			AppTakeService appts = new AppTakeService();		
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(id);		
						
			if ( appts.isNotAuditTakeTicket(ticketlist) ){
				String result = "databases.record.taketicket.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppPurchaseWithdrawDetail apid = new AppPurchaseWithdrawDetail();
			AppProduct aproduct = new AppProduct();
			List pils = apid.getPurchaseWithdrawDetailByPWID(id);
			Map<String, Integer> pidwise = new HashMap<String, Integer>();
			for (int i = 0; i < pils.size(); i++) {
				PurchaseWithdrawDetail sod = (PurchaseWithdrawDetail) pils.get(i);
				if (sod.getQuantity().doubleValue() != sod.getTakequantity()
						.doubleValue()) {
					String result = "databases.record.noendcasequantitynosame";
					request.setAttribute("result", result);
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
				Integer wise = aproduct.getProductByID(sod.getProductid())
						.getWise();
				pidwise.put(sod.getProductid(), wise);
			}

			// 生成送货单
			AppShipmentBill appsb = new AppShipmentBill();
			ShipmentBill bill = appsb.getShipmentBillByID(pb.getId());
			if (bill != null) {
				return mapping.findForward("audit");
			}

			ShipmentBill sb = new ShipmentBill();
			String sbid = pb.getId();
			sb.setId(sbid);
			sb.setCid(pb.getPid());
			sb.setCname(pb.getPname());
			sb.setCmobile("");
			sb.setLinkman(pb.getPlinkman());
			sb.setTel(pb.getTel());
			sb.setReceiveaddr("");
			sb.setRequiredate(pb.getMakedate());
			sb.setPaymentmode(0);
			sb.setInvmsg(0);
			sb.setTickettitle("");
			sb.setTransportmode(0);
			sb.setTransportnum("");
			sb.setTotalsum(pb.getTotalsum());
			sb.setRemark(pb.getWithdrawcause());
			sb.setIsaudit(0);
			sb.setMakeorganid(pb.getMakeorganid());
			sb.setMakedeptid(pb.getMakedeptid());
			sb.setMakeid(pb.getMakeid());
			sb.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			sb.setIstrans(0);
			sb.setIsblankout(0);

			StringBuffer keyscontent = new StringBuffer();
		      keyscontent.append(sb.getId()).append(",").append(sb.getCname()).append(",")
		      .append(sb.getCmobile()).append(",").append(sb.getLinkman()).append(",")
		      .append(sb.getTel()).append(",");
			
			PurchaseWithdrawDetail pid = null;
			// ReceivableDetail rvd = null;
			// AppReceivableDetail apprvd = new AppReceivableDetail();

			ShipmentBillDetail sbd = null;
			AppShipmentBillDetail appsbd = new AppShipmentBillDetail();
			boolean hasquantiry = false;
			for (int i = 0; i < pils.size(); i++) {
				pid = (PurchaseWithdrawDetail) pils.get(i);
				// //生成应收款结算单明细
				// rvd = new ReceivableDetail();
				// rvd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				// "receivable_detail",0,"")));
				// rvd.setRid(rid);
				// rvd.setProductid(pid.getProductid());
				// rvd.setProductname(pid.getProductname());
				// rvd.setSpecmode(pid.getSpecmode());
				// rvd.setBatch(pid.getBatch());
				// rvd.setBillno(rv.getBillno());
				// rvd.setPaymentmode(pb.getPaymentmode());
				// rvd.setUnitid(pid.getUnitid());
				// rvd.setQuantity(pid.getQuantity());
				// rvd.setGoodsfund(pid.getUnitprice());
				// rvd.setScot(0d);
				// rvd.setAlreadyquantity(0d);
				// rvd.setAlreadysum(0d);
				// rvd.setIsclose(0);
				// rvd.setRoid(rv.getRoid());
				// rvd.setMakedate(rv.getMakedate());
				// apprvd.addReceivableDetail(rvd);

				// if ( 0 == pidwise.get(pid.getProductid()).intValue() &&
				// pid.getQuantity() > 0 ){
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
					sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"shipment_bill_detail", 0, "")));
					sbd.setSbid(sbid);
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(pb.getWarehouseid());
					sbd.setUnitid(Integer.valueOf(pid.getUnitid()));
					sbd.setBatch(pid.getBatch());
					sbd.setUnitprice(pid.getUnitprice());
					sbd.setQuantity(pid.getQuantity());
					sbd.setDiscount(100d);
					sbd.setTaxrate(0d);
					sbd.setSubsum(pid.getSubsum());
					appsbd.addShipmentBillDetail(sbd);
					hasquantiry = true;
					
					
				}
			}
			if (hasquantiry) {
				sb.setKeyscontent(keyscontent.toString());
				appsb.addShipmentBill(sb);
			}
	    
			apb.updIsEndCase(id, userid,1);
			
		      request.setAttribute("result", "databases.audit.success");
		      //DBUserLog.addUserLog(userid,"结案采购退货,编号："+id); 

			return mapping.findForward("audit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
