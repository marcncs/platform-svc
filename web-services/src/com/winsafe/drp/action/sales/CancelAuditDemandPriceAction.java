package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDemandPrice;
import com.winsafe.drp.dao.DemandPrice;

public class CancelAuditDemandPriceAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {
			Long dpid = Long.valueOf(request.getParameter("DPID"));
			AppDemandPrice aso = new AppDemandPrice();
			DemandPrice so = aso.getDemandPriceByID(dpid);

			if (so.getIsaudit() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			if (!String.valueOf(so.getAuditid()).contains(userid.toString())) {
//				String result = "databases.record.cancelaudit";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}
//
//			 aso.updIsAudit(dpid, userid, 0);

			request.setAttribute("result", "databases.cancel.success");
//			DBUserLog.addUserLog(userid, "取消复核销售报价");
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
