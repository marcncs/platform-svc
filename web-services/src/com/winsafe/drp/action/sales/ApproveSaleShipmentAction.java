package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillApprove;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ApproveSaleShipmentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();
		String approvecontent = request.getParameter("approvecontent");
		String sbid = request.getParameter("sbid");
		String strapprove = request.getParameter("ApproveStatus");
		/*
		 * int intisshipment =
		 * Integer.parseInt(request.getParameter("IsShipment"));
		 */

		int approve = Integer.parseInt(strapprove);
		String approvedate = DateUtil.getCurrentDateTime();

		try {
			AppShipmentBillApprove asba = new AppShipmentBillApprove();
			AppShipmentBill asb = new AppShipmentBill();
//			asba.addApproveContent(approvecontent, approve,
//					approvedate, sbid, userid);
			String result = "";
			int supperarrove = 1;
			boolean judge = DbUtil.judgeApprove("ShipmentBillApprove", "sbid",
					sbid);
			if (approve == 1 && judge) {
				supperarrove = 2;
			} else if (approve == 0)
				supperarrove = 1;
			else if (approve == 2)
				supperarrove = 3;

			
			/*
			 * AppSaleLogDetail asld= new AppSaleLogDetail(); List ls =
			 * asld.getSaleLogDetailBySLID(saleid);
			 */

			
//			asb.updIsApprove(sbid, supperarrove);

			
			request.setAttribute("result", "databases.add.success");
//			DBUserLog.addUserLog(userid, "审阅销售出库单");

			return mapping.findForward("approve");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
