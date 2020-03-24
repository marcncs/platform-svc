package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDrawShipmentBill;
import com.winsafe.drp.dao.AppDrawShipmentBillDetail;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.DrawShipmentBill;
import com.winsafe.drp.dao.DrawShipmentBillDetail;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;

public class EndcaseDrawShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		super.initdata(request);try{
			String id = request.getParameter("DSID");
			AppDrawShipmentBill apb = new AppDrawShipmentBill();
			AppDrawShipmentBillDetail adsbd = new AppDrawShipmentBillDetail();
			DrawShipmentBill ds = apb.getDrawShipmentBillByID(id);

			if (ds.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (ds.getIsaudit() == 0) {
				String result = "databases.record.nooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			List pils = adsbd.getDrawShipmentBillDetailByDsid(id);

			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(id);
			
//			if (appts.isNotAuditTakeTicket(ticketlist)) {
//				String result = "databases.record.taketicket.noaudit";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}
			

			AppShipmentBill appsb = new AppShipmentBill();
			ShipmentBill bill = appsb.getShipmentBillByID(ds.getId());
			if (bill != null) {
				return mapping.findForward("audit");
			}
			AppUsers au = new AppUsers();
			ShipmentBill sb = new ShipmentBill();
			String sbid = ds.getId();
			sb.setId(sbid);
//			sb.setSoid(sbid);
//			sb.setSaledept(ds.getMakedeptid());
//			sb.setSaleid(ds.getMakeid());
			sb.setCid(String.valueOf(ds.getMakeid()));
			sb.setCname(au.getUsersByid(ds.getMakeid()).getRealname());
			sb.setCmobile("");
			sb.setLinkman("");
			sb.setTel("");
			sb.setReceiveaddr("");
			sb.setRequiredate(ds.getDrawdate());
			sb.setPaymentmode(0);
			sb.setInvmsg(0);
			sb.setTickettitle("");
			sb.setTransportmode(0);
			sb.setTransportnum("");
			sb.setTotalsum(0d);
			sb.setRemark(ds.getRemark());
			sb.setIsaudit(0);
			sb.setMakeorganid(ds.getMakeorganid());
			sb.setMakedeptid(ds.getMakedeptid());
			sb.setMakeid(ds.getMakeid());
			sb.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			sb.setIstrans(0);
			sb.setIsblankout(0);


			StringBuffer keyscontent = new StringBuffer();
		      keyscontent.append(sb.getId()).append(",").append(sb.getCname()).append(",")
		      .append(sb.getCmobile()).append(",").append(sb.getLinkman()).append(",")
		      .append(sb.getTel()).append(",");
			DrawShipmentBillDetail pid = null;
			// ReceivableDetail rvd = null;
			// AppReceivableDetail apprvd = new AppReceivableDetail();

			ShipmentBillDetail sbd = null;
			AppShipmentBillDetail appsbd = new AppShipmentBillDetail();
			boolean hasquantiry = false;
			for (int i = 0; i < pils.size(); i++) {
				pid = (DrawShipmentBillDetail) pils.get(i);
				
				if (pid.getQuantity() > 0) {
					
					sbd = new ShipmentBillDetail();
//					sbd.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
//							"shipment_bill_detail", 0, "")));
					sbd.setSbid(sbid);
					sbd.setProductid(pid.getProductid());
					sbd.setProductname(pid.getProductname());
					sbd.setSpecmode(pid.getSpecmode());
					sbd.setWarehouseid(ds.getWarehouseid());
//					sbd.setUnitid(Long.valueOf(pid.getUnitid()));
					sbd.setBatch(pid.getBatch());
					sbd.setUnitprice(0d);
					sbd.setQuantity(pid.getQuantity());
					sbd.setDiscount(100d);
					sbd.setTaxrate(0d);
					sbd.setSubsum(0d);
					appsbd.addShipmentBillDetail(sbd);
					hasquantiry = true;
					
					 keyscontent.append(sbd.getProductid()).append(",")
				        .append(sbd.getProductname()).append(",")
				        .append(sbd.getSpecmode()).append(",");
				}
			}
			if (hasquantiry) {
				sb.setKeyscontent(keyscontent.toString());
				appsb.addShipmentBill(sb);
			}
			
			//asm.updStockAlterMoveIsShipment(id,1,userid);

			apb.updIsEndCase(id, userid, 1);
			
			request.setAttribute("result", "databases.audit.success");
			//DBUserLog.addUserLog(userid, "结案领用出库,编号：" + id);

			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return new ActionForward(mapping.getInput());
	}

}
