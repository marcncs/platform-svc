package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppSaleRepair;
import com.winsafe.drp.dao.AppSaleRepairDetail;
import com.winsafe.drp.dao.AppSaleRepairIdcode;
import com.winsafe.drp.dao.SaleRepair;
import com.winsafe.drp.dao.SaleRepairDetail;
import com.winsafe.drp.dao.SaleRepairIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class CancelAuditSaleRepairAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();		
		try {
			String id = request.getParameter("id");
			AppSaleRepair apb = new AppSaleRepair();
			AppSaleRepairIdcode appwi = new AppSaleRepairIdcode();
			SaleRepair pb = apb.getSaleRepairByID(id);

			if (pb.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (pb.getIsaudit() == 0) {
				String result = "databases.record.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			List idcodelist = appwi.getSaleRepairIdcodeByStid(id);
			AppIdcode appidcode = new AppIdcode();
			SaleRepairIdcode wi = null;
			for (int i = 0; i < idcodelist.size(); i++) {
				wi = (SaleRepairIdcode) idcodelist.get(i);
//				appidcode.updIsUse(wi.getProductid(), wi.getIdcode(), 0);
			}

			AppSaleRepairDetail appwd = new AppSaleRepairDetail();
			List pils = appwd.getSaleRepairDetailBySrid(id);
			
			AppProductStockpile aps = new AppProductStockpile();
			SaleRepairDetail pid = null;
			for (int i = 0; i < pils.size(); i++) {
				pid = (SaleRepairDetail) pils.get(i);
				Double quantity = Double.valueOf(pid.getQuantity().toString());
				
//				aps.returninProductStockpile(pid.getProductid(),
//						pid.getBatch(), quantity, pb.getWarehouseinid(),
//						id, "销售返修-出货");
			}
			
//			AppTakeTicket appticket = new AppTakeTicket();
//			AppTakeTicketDetail appttd = new AppTakeTicketDetail();
//			TakeTicket tt = appticket.getTakeTicketByBillno(id);
			//
//			String ttid = tt.getId();
//			if ( tt.getIsaudit() == 0 ){
//				appticket.delTakeTicketByBillno(id);
//				appttd.delTakeTicketDetailByTtid(ttid);
//			}

//			apb.updIsAudit(id, userid, 0);
			
			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "取消复核销售返修,编号：" + id);

			return mapping.findForward("noaudit");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}

}
