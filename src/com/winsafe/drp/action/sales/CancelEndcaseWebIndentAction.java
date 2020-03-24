package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.AppWebIndent;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.WebIndent;

public class CancelEndcaseWebIndentAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {
			String id = request.getParameter("id");
			AppWebIndent apb = new AppWebIndent();
			AppShipmentBill appsb = new AppShipmentBill();
			WebIndent pb = apb.getWebIndentByID(id);

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

			// AppTakeService appts = new AppTakeService();
			// List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(id);
			// //决断检货小票是否复核
			// if ( appts.isAuditTakeTicket(ticketlist) ){
			// String result = "databases.record.taketicket.audit";
			// request.setAttribute("result", "databases.audit.success");
			// return new ActionForward("/sys/lockrecordclose.jsp");
			// }

			ShipmentBill sbill = appsb.getShipmentBillByID(id);
			if (sbill != null && sbill.getIstrans() == 1) {
				String result = "datebases.record.shipmentbillistrans";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			//AppReceivable appr = new AppReceivable();
			//AppReceivableDetail apprd = new AppReceivableDetail();
			//List rlist = appr.getReceivableByBillno(id);
			AppShipmentBillDetail appsbd = new AppShipmentBillDetail();
//			List sblist = appsb.getShipmentBillBySoid(id);

			// 删除应收款结算单
			// Receivable r = null;
			// for ( int i=0; i<rlist.size(); i++ ){
			// r = (Receivable)rlist.get(i);
			// String rid = r.getId();
			// appr.delReceivable(rid);
			// apprd.delReceivableDetailByRid(rid);
			// }

			
			ShipmentBill sb = null;
//			for (int i = 0; i < sblist.size(); i++) {
//				sb = (ShipmentBill) sblist.get(i);
//				String sbid = sb.getId();
//				if (sb.getIsaudit() == 0) {
//					appsb.delShipmentBill(sbid);
//					appsbd.delShipmentProductBillBySbID(sbid);
//				}
//			}

//			apb.updIsEndcase(id, userid, 0);
			
			//apid.updIsSettlement(id, 0);
			
			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "取消结案网站订单,编号：" + id);

			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
