package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseTrades;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.PurchaseTrades;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditPurchaseTradesAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String id = request.getParameter("id");
			AppPurchaseTrades apb = new AppPurchaseTrades();
			PurchaseTrades pb = apb.getPurchaseTradesByID(id);

			if (pb.getIsreceive() == 1) {
				request.setAttribute("result","databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (pb.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (pb.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppTakeService appts = new AppTakeService();		
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(id);		
						
			if ( appts.isAuditTakeTicket(ticketlist) ){
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}		

			
			
			appts.deleteTake(ticketlist);
			apb.updIsAudit(id, userid, 0);
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 2, "采购换货>>取消复核采购换货单,编号：" + id);

			return mapping.findForward("noaudit");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
	
		}
		return new ActionForward(mapping.getInput());
	}

}
