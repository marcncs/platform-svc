package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.SaleOrder;

public class DelRetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String soid = request.getParameter("SOID");
			AppSaleOrder aso = new AppSaleOrder();
			AppSaleOrderDetail appsod = new AppSaleOrderDetail();
			SaleOrder so = aso.getSaleOrderByID(soid);
			if (so.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", "databases.del.success");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			String upd = aso.delSaleOrder(soid);
//			String s = appsod.delSaleOrderBySOID(soid);
			String result = "";

//			if (upd.equals("s")) {
//				result = "databases.del.success";
//			} else {
//				result = "databases.del.fail";
//			}
//			request.setAttribute("result", "databases.del.success");
//			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "删除销售订单");

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
