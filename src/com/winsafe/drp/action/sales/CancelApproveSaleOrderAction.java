package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderApprove;
import com.winsafe.hbm.util.DbUtil;

public class CancelApproveSaleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {
			String soid = request.getParameter("SOID");
			Integer actid = Integer.valueOf(request.getParameter("actid"));
			Integer approve = 0;
			AppSaleOrderApprove asla = new AppSaleOrderApprove();
			AppSaleOrder asl = new AppSaleOrder();
//			 asla.cancelApprove(approve, actid, soid, userid);
			String result = "";
			int supperarrove = 1;
			boolean judge = DbUtil.judgeCancelApprove("SaleOrderApprove",
					"soid", soid);

			if (judge) {
				supperarrove = 0;
			} else {
				supperarrove = 1;
			}

			
			/*
			 * AppSaleLogDetail asld= new AppSaleLogDetail(); List ls =
			 * asld.getSaleLogDetailBySLID(saleid);
			 */

			asl.updIsApprove(soid, supperarrove);

	
			request.setAttribute("result", "databases.cancel.success");
//			DBUserLog.addUserLog(userid, "取消审阅销售订单");

			return mapping.findForward("cancelapprove");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
