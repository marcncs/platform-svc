package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.AppPeddleOrderDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.PeddleOrder;
import com.winsafe.drp.dao.PeddleOrderDetail;
import com.winsafe.drp.dao.TakeTicket;

public class CancelAuditPeddleOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {
			String soid = request.getParameter("SOID");
			AppPeddleOrder aso = new AppPeddleOrder();
			PeddleOrder so = aso.getPeddleOrderByID(soid);

			if (so.getIsaudit() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (so.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			if (!String.valueOf(so.getAuditid()).contains(userid.toString())) {
//				String result = "databases.record.cancelaudit";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}

			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(soid);
			
			if (appts.isAuditTakeTicket(ticketlist)) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppPeddleOrderDetail appsod = new AppPeddleOrderDetail();
			List pils = appsod.getPeddleOrderDetailObjectByPOID(soid);

			// 删除检货
			appts.deleteTake(ticketlist);
			PeddleOrderDetail pid = null;
			AppProduct ap = new AppProduct();
			for (int i = 0; i < pils.size(); i++) {
				pid = (PeddleOrderDetail) pils.get(i);
				
				int wise = ap.getProductByID(pid.getProductid()).getWise();
				if (wise != 0) {
					// 修改存货为费用/劳务的检货数量
					appsod.updTakeQuantity(pid.getPoid(), pid.getProductid(),
							pid.getBatch(), -pid.getQuantity());
				}
			}
//
//			 aso.updIsAudit(soid, userid, 0);
//
//			request.setAttribute("result", "databases.cancel.success");
//			DBUserLog.addUserLog(userid, "取消初审销售单");

			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
