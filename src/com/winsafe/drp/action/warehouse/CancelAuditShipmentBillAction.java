package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);try{
			String sbid = request.getParameter("SBID");
			AppShipmentBill asb = new AppShipmentBill();
			ShipmentBill sb = asb.getShipmentBillByID(sbid);
	
			if (sb.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (sb.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if ( sb.getBsort() == 1 || sb.getBsort() == 2 || sb.getBsort() == 3 || sb.getBsort() == 6){
				
				if ( asb.isComplete(sb.getBsort(), sb.getId()) ){
					request.setAttribute("result", "databases.record.alreadyshipmentnocancel");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}

			
			if ( sb.getBsort() == 0 || sb.getBsort() == 1 ){
				AppReceivable appr = new AppReceivable();
				List<Receivable> rlist = appr.getReceivableByBillno(sb.getId());
				
				for ( Receivable r : rlist ){
					if ( r.getAlreadysum() > 0 ){
						request.setAttribute("result", "对不起，该单据已经收款不能取消！");
						return new ActionForward("/sys/lockrecordclose2.jsp");
					}
				}				
				for ( Receivable r : rlist ){				
					appr.delReceivable(r.getId());
				}
			}
			
			if ( sb.getBsort() == 3 || sb.getBsort() == 7 || sb.getBsort() == 10 ){
				AppPayable app = new AppPayable();
				List<Payable> plist = app.getPayableByBillno(sb.getId());
				for ( Payable p : plist ){
					if ( p.getAlreadysum() > 0 ){
						request.setAttribute("result", "对不起，该单据已经付款不能取消！");
						return new ActionForward("/sys/lockrecordclose2.jsp");
					}
				}
				for ( Payable p : plist ){
					app.delPayable(p.getId());
				}
			}				

			 asb.updIsAudit(sbid, userid, 0);

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 8, "取消复核送货清单,编号:"+sbid);
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
