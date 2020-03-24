package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.TakeBillService;
import com.winsafe.drp.util.DBUserLog;

public class AuditTakeBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);try{
			String id = request.getParameter("id");
			AppTakeBill api = new AppTakeBill();
			AppTakeTicket apptt = new AppTakeTicket();
			TakeBill tb = api.getTakeBillByID(id);
			if (tb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.close");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			List<TakeTicket> ttlist = apptt.getTakeTicketByBillno(tb.getId());
			for (TakeTicket tt : ttlist) {
				if (tt.getIsaudit() == 0) {
					request.setAttribute("result", "databases.record.taketicket.noaudit");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			
			TakeBillService tbs = new TakeBillService(tb, users);
			tbs.auditDeal();
			
			

//			HibernateUtil.currentSession(true);
//			
//			api.updTakeStatus(tb.getBsort(), tb.getId(), 1);
//			
//			
//			api.updIsAudit(id, userid, 1);

			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(userid, 7, "关闭检货清单,编号:"+id);

//			HibernateUtil.commitTransaction();
//
//			if (tb.getId().startsWith("SO")) {
//				return new ActionForward("/sales/endcaseSaleOrderAction.do?id="
//						+ tb.getId());
//			} else if (tb.getId().startsWith("VO")) {
//				return new ActionForward("/sales/endcaseVocationOrderAction.do?id="
//						+ tb.getId());
//			} else if (tb.getId().startsWith("OM")) {
//				return new ActionForward(
//						"/warehouse/completeStockAlterMoveShipmentAction.do?OMID="
//								+ tb.getId());
//			} else if (tb.getId().startsWith("PW")) {
//				return new ActionForward(
//						"/aftersale/endcasePurchaseWithdrawAction.do?id="
//								+ tb.getId());
//			} else if (tb.getId().startsWith("SM")) {
//				return new ActionForward(
//						"/warehouse/completeStockMoveShipmentAction.do?SMID="
//								+ tb.getId());
//			}else if(tb.getId().startsWith("DS")){
//				return new ActionForward(
//						"/warehouse/endcaseDrawShipmentBillAction.do?DSID="
//								+ tb.getId());
//			}else if (tb.getId().startsWith("WI")) {
//				return new ActionForward("/sales/endcaseWebIndentAction.do?id="
//						+ tb.getId());
//			}else if (tb.getId().startsWith("IO") || tb.getId().startsWith("WO")) {
//				return new ActionForward("/sales/endcaseIntegralOrderAction.do?id="
//						+ tb.getId());
//			}

			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return new ActionForward(mapping.getInput());
	}

}
