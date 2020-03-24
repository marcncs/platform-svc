package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-9-3 上午10:35:29 www.winsafe.cn
 */
public class CancelAuditOrganTradesAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganTrades appAMA = new AppOrganTrades();
			OrganTrades organWithdraw = appAMA.getOrganTradesByID(id);
			if (organWithdraw.getIsratify() == 1) {
				String result = "databases.operator.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (organWithdraw.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			organWithdraw.setIsaudit(0);
			organWithdraw.setAuditid(null);
			organWithdraw.setAuditdate(null);
			appAMA.update(organWithdraw);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>取消复核渠道退货,编号：" + id);
			request.setAttribute("result", "databases.cancel.success");

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
