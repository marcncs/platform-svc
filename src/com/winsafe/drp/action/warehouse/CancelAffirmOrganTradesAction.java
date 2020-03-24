package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAffirmOrganTradesAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		super.initdata(request);try{
			String smid = request.getParameter("ID");
			AppOrganTrades api = new AppOrganTrades();
			OrganTrades pi = api.getOrganTradesByID(smid);

			if (pi.getPisaffirm() == 0) {
				request.setAttribute("result", "databases.record.nocancelaudittwo");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}


			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(pi.getIdii());
			
			if (appts.isAuditTakeTicket(ticketlist)) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			appts.deleteTake(ticketlist);			
			
			pi.setPisaffirm(0);
			pi.setPaffirmid(null);
			pi.setPaffirmdate(null);
			api.update(pi);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 4, "入库>>渠道换货供方签收>>取消确认渠道换货单,编号："+smid);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
