package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppObjIntegral;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderDetail;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditSaleOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String soid = request.getParameter("SOID");
			AppSaleOrder aso = new AppSaleOrder();
			SaleOrder so = aso.getSaleOrderByID(soid);

			if (so.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (so.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			if (!String.valueOf(so.getAuditid()).contains(userid.toString())) {
//				request.setAttribute("result", "databases.record.cancelaudit");
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}

			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(soid);
			
			if (appts.isAuditTakeTicket(ticketlist)) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppSaleOrderDetail appsod = new AppSaleOrderDetail();	
			
			List<SaleOrderDetail> plist = appsod.getSaleOrderDetailBySoidWise(soid, 0);
			
			AppReceivable appr = new AppReceivable();
			List<Receivable> rlist = appr.getReceivableByBillno(soid);
			
			if ( plist.isEmpty() ){			

				for ( Receivable r : rlist ){
					if ( r.getAlreadysum() > 0 ){
						request.setAttribute("result", "对不起，该单据已经收款不能取消！");
						return new ActionForward("/sys/lockrecordclose2.jsp");
					}
				}				
			}


//			
			appts.deleteTake(ticketlist);
			

			AppObjIntegral aoi = new AppObjIntegral();
			aoi.delIntegralIByBillNo(soid);
			aoi.delIntegralOByBillNo(soid);
			aoi.delIntegralDetailByBillNo(soid);
			
			
			aso.updIsAudit(soid, userid, 0);
			
			List<SaleOrderDetail> flist = appsod.getSaleOrderDetailBySoidWise(soid, 1);
			
			
			for ( SaleOrderDetail sod : flist ) {
				appsod.updTakeQuantity(sod.getSoid(), sod.getProductid(),
							sod.getBatch(), 0d);
			}			
			
			if ( plist.isEmpty() ){	
				for ( Receivable r : rlist ){
					appr.delReceivable(r.getId());
				}
				
				aso.updIsEndcase(soid, userid, 0);
			}

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 6, "销售单>>取消复核销售单,编号:"+soid);

			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
