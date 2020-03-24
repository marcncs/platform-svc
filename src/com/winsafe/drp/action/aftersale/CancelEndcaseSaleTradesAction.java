package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.SaleTrades;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelEndcaseSaleTradesAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String smid = request.getParameter("ID");
		try {
			AppSaleTrades asm = new AppSaleTrades();
			SaleTrades sm = asm.getSaleTradesByID(smid);

			
			if (sm.getIsendcase() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if ( sm.getIsblankout() == 1 ){
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(smid);
			
			if (appts.isAuditTakeTicket(ticketlist)) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			appts.deleteTake(ticketlist);			
			
			asm.updIsEndCase(smid, userid, 0);

			

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 6,"销售换货>>取消发货销售换货,编号：" + smid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	

}
