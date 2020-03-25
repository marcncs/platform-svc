package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.SaleOrder;

public class BlankoutSaleOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		try {
			AppSaleOrder apb = new AppSaleOrder();
			SaleOrder pb = apb.getSaleOrderByID(id);
			if (pb.getIsaudit() == 1) {
				String result = "databases.record.approvestatus";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
//			Long userid = users.getUserid();
//			apb.BlankoutSaleOrder(id, userid);
			
			request.setAttribute("result", "databases.del.success");

//			DBUserLog.addUserLog(userid, "作废销售单");
			return mapping.findForward("delresult");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
