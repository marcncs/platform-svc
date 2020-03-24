package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-8-24 下午05:28:53 www.winsafe.cn
 */
public class CancelAffirmOrganWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganWithdraw appAMA = new AppOrganWithdraw();
			OrganWithdraw ow = appAMA.getOrganWithdrawByID(id);

			if (ow.getIsblankout() == 1) {
				request.setAttribute("result",
						"databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (ow.getIscomplete() == 1) {
				request.setAttribute("result", "已签收!不能取消确认");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			// AppOrganWithdrawDetail appowd = new AppOrganWithdrawDetail();
			// List<OrganWithdrawDetail> listowd =
			// appowd.getOrganWithdrawDetailByPWID(id);

			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(id);

			if (appts.isAuditTakeTicket(ticketlist)) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			//			
			appts.deleteTake(ticketlist);

			ow.setIsaffirm(0);
			ow.setAffirmid(null);
			ow.setAffirmdate(null);
			appAMA.update(ow);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>确认渠道退货,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}

}
