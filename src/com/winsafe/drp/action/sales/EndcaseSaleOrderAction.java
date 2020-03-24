package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderDetail;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class EndcaseSaleOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		int userid = users.getUserid();
		try {
			String id = request.getParameter("id");
			AppSaleOrder apb = new AppSaleOrder();
			SaleOrder pb = apb.getSaleOrderByID(id);

			if (pb.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (pb.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.nooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(id);
			
			if (appts.isNotAuditTakeTicket(ticketlist)) {
				request.setAttribute("result", "databases.record.taketicket.noaudit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppSaleOrderDetail apid = new AppSaleOrderDetail();
			List<SaleOrderDetail> pils = apid.getSaleOrderDetailBySoidWise(id, 0);
			

			
			AppShipmentBill appsb = new AppShipmentBill();
			if ( !pils.isEmpty() && pils != null ){
				ShipmentBill sb = new ShipmentBill();
				String sbid = pb.getId();
				sb.setId(sbid);
				sb.setBsort(0);
				sb.setObjectsort(1);
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
				sb.setTotalsum(pb.getTotalsum());
				sb.setRemark(pb.getRemark());
				sb.setIsaudit(0);
				sb.setMakeorganid(pb.getEquiporganid());
				sb.setMakedeptid(pb.getMakedeptid());
				sb.setMakeid(pb.getMakeid());
				sb.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				sb.setIstrans(0);
				sb.setIsblankout(0);
				sb.setKeyscontent(pb.getKeyscontent());	
	
				ShipmentBillDetail sbd = null;
				AppShipmentBillDetail appsbd = new AppShipmentBillDetail();
				for (SaleOrderDetail pid : pils ) {
					if (pid.getQuantity() > 0) {

						sbd = new ShipmentBillDetail();
						sbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
								"shipment_bill_detail", 0, "")));
						sbd.setSbid(sbid);
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


			apb.updIsEndcase(id, userid, 1);

			request.setAttribute("result", "databases.endcase.success");
			DBUserLog.addUserLog(userid, "结案销售单,编号：" + id);

			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
