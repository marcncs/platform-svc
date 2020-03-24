package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDemandPrice;
import com.winsafe.drp.dao.DemandPrice;

public class DelDemandPriceAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			Long dpid = Long.valueOf(request.getParameter("DPID"));
			AppDemandPrice aso = new AppDemandPrice();
			DemandPrice so = aso.getDemandPriceByID(dpid);
			if (so.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			aso.delDemandPrice(dpid);
			request.setAttribute("result", "databases.del.success");
			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "删除销售报价");

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
