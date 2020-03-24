package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-9-02 上午10:28:53 www.winsafe.cn
 */
public class CancelAuditSupplySaleMoveAction extends BaseAction {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppSupplySaleMove appAMA = new AppSupplySaleMove();
			SupplySaleMove alterma = appAMA.getSupplySaleMoveByID(id);
			if (alterma.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (alterma.getIsblankout() == 1) {
				request.setAttribute("result",
						"databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (alterma.getIsshipment() == 1) {
				request.setAttribute("result",
						"databases.record.nocancelaudittwo");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(id);

			if (appts.isAuditTakeTicket(ticketlist)) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			appts.deleteTake(ticketlist);

			alterma.setIsaudit(0);
			alterma.setAuditid(null);
			alterma.setAuditdate(null);
			appAMA.update(alterma);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>取消复核渠道代销,编号：" + id);
			request.setAttribute("result", "databases.cancel.success");

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
