package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDrawShipmentBill;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.DrawShipmentBill;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditDrawShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		super.initdata(request);try{
			String osid = request.getParameter("OSID");
			AppDrawShipmentBill assb = new AppDrawShipmentBill();
			DrawShipmentBill ssb = assb.getDrawShipmentBillByID(osid);

			if (ssb.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}	
			if (ssb.getIsendcase() == 1) {
				request.setAttribute("result", "databases.record.alreadycompletenocancel");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (ssb.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppTakeService appts = new AppTakeService();		
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(osid);		
						
			if ( appts.isAuditTakeTicket(ticketlist) ){
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
	

			
			appts.deleteTake(ticketlist);
			assb.updIsAudit(osid, userid, 0);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 7, "库存处理>>取消复核领用出库单,编号:"+osid);
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
