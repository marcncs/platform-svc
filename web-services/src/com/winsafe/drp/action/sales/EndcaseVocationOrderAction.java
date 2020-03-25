package com.winsafe.drp.action.sales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.AppVocationOrder;
import com.winsafe.drp.dao.AppVocationOrderDetail;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.VocationOrder;
import com.winsafe.drp.dao.VocationOrderDetail;
import com.winsafe.hbm.util.DateUtil;

public class EndcaseVocationOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();
		try {
			String id = request.getParameter("id");
			AppVocationOrder apb = new AppVocationOrder();
			VocationOrder pb = apb.getVocationOrderByID(id);

			if (pb.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (pb.getIsaudit() == 0) {
				String result = "databases.record.nooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(id);
			
			if (appts.isNotAuditTakeTicket(ticketlist)) {
				String result = "databases.record.taketicket.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppVocationOrderDetail apid = new AppVocationOrderDetail();
			AppProduct aproduct = new AppProduct();
			List pils = apid.getVocationOrderDetailObjectBySOID(id);
			Map<String, Integer> pidwise = new HashMap<String, Integer>();
			for (int i = 0; i < pils.size(); i++) {
				VocationOrderDetail sod = (VocationOrderDetail) pils.get(i);
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

			
			AppShipmentBill appsb = new AppShipmentBill();
			ShipmentBill bill = appsb.getShipmentBillByID(pb.getId());
			if (bill != null) {
				return mapping.findForward("audit");
			}

			ShipmentBill sb = new ShipmentBill();
			String sbid = pb.getId();
			sb.setId(sbid);
//			sb.setSoid(pb.getId());
//			sb.setSaledept(pb.getSaledept());
//			sb.setSaleid(pb.getSaleid());
			sb.setCid(pb.getCid());
			sb.setCname(pb.getCname());
			sb.setCmobile(pb.getCmobile());
			sb.setLinkman(pb.getReceiveman());
			sb.setTel(pb.getReceivemobile()+"/"+pb.getReceivetel());
			sb.setReceiveaddr(pb.getTransportaddr());
			sb.setRequiredate(pb.getConsignmentdate());
			sb.setPaymentmode(pb.getPaymentmode());
			sb.setInvmsg(pb.getInvmsg());
			sb.setTickettitle(pb.getTickettitle());
			sb.setTransportmode(pb.getTransportmode());
			sb.setTransportnum("");
			sb.setTotalsum(0.00);
			sb.setRemark(pb.getRemark());
			sb.setIsaudit(0);
			sb.setMakeorganid(pb.getEquiporganid());
//			sb.setMakedeptid(pb.getMakedeptid());
//			sb.setMakeid(pb.getMakeid());
			sb.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			sb.setIstrans(0);
			sb.setIsblankout(0);
			sb.setKeyscontent(pb.getKeyscontent());

			VocationOrderDetail pid = null;
			// ReceivableDetail rvd = null;
			// AppReceivableDetail apprvd = new AppReceivableDetail();

			ShipmentBillDetail sbd = null;
			AppShipmentBillDetail appsbd = new AppShipmentBillDetail();
			boolean hasquantiry = false;
			for (int i = 0; i < pils.size(); i++) {
				pid = (VocationOrderDetail) pils.get(i);
				// 单明细
				// rvd = new ReceivableDetail();
				// rvd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
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
//					sbd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//							"shipment_bill_detail", 0, "")));
					sbd.setSbid(sbid);
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
//					sbd.setWarehouseid(pid.getWarehouseid());
//					sbd.setUnitid(pid.getUnitid());
					sbd.setBatch(pid.getBatch());
					sbd.setUnitprice(0.00);
					sbd.setQuantity(pid.getQuantity());
					sbd.setDiscount(pid.getDiscount());
					sbd.setTaxrate(pid.getTaxrate());
					sbd.setSubsum(0.00);
					appsbd.addShipmentBillDetail(sbd);
					hasquantiry = true;
				}
			}
			if (hasquantiry) {
				appsb.addShipmentBill(sb);
			}

//			apb.updIsEndcase(id, userid, 1);
			
			//apid.updIsSettlement(id, 1);

			request.setAttribute("result", "databases.endcase.success");
//			DBUserLog.addUserLog(userid, "结案行业销售单,编号：" + id);

			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
