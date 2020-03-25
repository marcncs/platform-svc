package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.AppPeddleOrderDetail;
import com.winsafe.drp.dao.PeddleOrder;

public class DelPeddleOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String soid = request.getParameter("SOID");
			AppPeddleOrder aso = new AppPeddleOrder();
			AppPeddleOrderDetail appsod = new AppPeddleOrderDetail();
			PeddleOrder so = aso.getPeddleOrderByID(soid);
			if (so.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			aso.delPeddleOrder(soid);
			appsod.delPeddleOrderBySOID(soid);
			
			request.setAttribute("result", "databases.del.success");
			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "删除销售订单");

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
