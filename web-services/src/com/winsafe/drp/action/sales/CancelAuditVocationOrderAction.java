package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.AppVocationOrder;
import com.winsafe.drp.dao.AppVocationOrderDetail;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.VocationOrder;
import com.winsafe.drp.dao.VocationOrderDetail;

public class CancelAuditVocationOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {
			String soid = request.getParameter("SOID");
			AppVocationOrder aso = new AppVocationOrder();
			VocationOrder so = aso.getVocationOrderByID(soid);

			if (so.getIsaudit() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (so.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			if (!String.valueOf(so.getAuditid()).contains(userid.toString())) {
//				String result = "databases.record.cancelaudit";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}

			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(soid);
			
			if (appts.isAuditTakeTicket(ticketlist)) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppVocationOrderDetail appsod = new AppVocationOrderDetail();
			List pils = appsod.getVocationOrderDetailObjectBySOID(soid);

			
			appts.deleteTake(ticketlist);
			VocationOrderDetail pid = null;
			AppProduct ap = new AppProduct();
			boolean isproduct = false;
			for (int i = 0; i < pils.size(); i++) {
				pid = (VocationOrderDetail) pils.get(i);
				
				int wise = ap.getProductByID(pid.getProductid()).getWise();
				if (wise != 0) {
					
					appsod.updTakeQuantity(pid.getSoid(), pid.getProductid(),
							pid.getBatch(), 0d);
				}else{
					isproduct = true;
				}
			}
			
			
			AppCIntegralDeal acid = new AppCIntegralDeal();
			acid.delCIntegralDeal(soid);
			
			
			AppOIntegralDeal aoid = new AppOIntegralDeal();
			aoid.delOIntegralDeal(soid);
			
//			aso.updIsAudit(soid, userid, 0);
			
			
			if ( !isproduct ){
				AppReceivable appr = new AppReceivable();
				List rlist = appr.getReceivableByBillno(so.getId());
				
				Receivable r = null;
				for ( int i=0; i<rlist.size(); i++ ){
					r = (Receivable)rlist.get(i);
					String rid = r.getId();
					appr.delReceivable(rid);
				}				
				
//				aso.updIsEndcase(soid, userid, 0);
			}

			request.setAttribute("result", "databases.cancel.success");
//			DBUserLog.addUserLog(userid, "取消初审行业销售单");

			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
