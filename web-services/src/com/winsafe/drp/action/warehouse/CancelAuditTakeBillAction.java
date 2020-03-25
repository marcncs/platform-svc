package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.TakeBillService;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditTakeBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();

		super.initdata(request);try{
			String id = request.getParameter("id");
			AppTakeBill api = new AppTakeBill(); 
			TakeBill tb = api.getTakeBillByID(id);	
			if ( tb.getIsblankout() == 1){
	             request.setAttribute("result", "databases.record.blankoutnooperator");
	             return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if ( tb.getIsaudit() == 0){
	             request.setAttribute("result", "databases.record.noclosenocancel");
	             return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppShipmentBill appsb = new AppShipmentBill();
			ShipmentBill sbill = appsb.getShipmentBillByID(id);
			if ( sbill.getIsaudit() == 1 ){
				request.setAttribute("result", "datebases.record.shipmentbillistrans");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if ( tb.getId().startsWith("OM") ){				
				AppStockAlterMove apasm = new AppStockAlterMove();
				StockAlterMove sam = apasm.getStockAlterMoveByID(tb.getId());
				if ( sam.getIscomplete() == 1 ){
					 request.setAttribute("result", "databases.record.alreadyshipmentnocancel");
		             return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}else if ( tb.getId().startsWith("SM") ){			
				AppStockMove apasm = new AppStockMove();
				StockMove sam = apasm.getStockMoveByID(tb.getId());
				if ( sam.getIscomplete() == 1 ){
					 request.setAttribute("result", "databases.record.alreadyshipmentnocancel");
		             return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}      
			
			TakeBillService tbs = new TakeBillService(tb, users);
			tbs.cancelAuditDeal();
			
//			
//			api.updTakeStatus(tb.getBsort(), tb.getId(), 0);
//			
//			
//			api.updIsAudit(id, userid, 0);			
			
	      request.setAttribute("result", "databases.operator.success");
	      DBUserLog.addUserLog(userid, 7, "取消关闭检货清单,编号:"+id);
	      
//		  if ( tb.getId().startsWith("SO") ){
//			  return new ActionForward("/sales/cancelEndcaseSaleOrderAction.do?id="+tb.getId());			     
//		  }else if ( tb.getId().startsWith("VO") ){
//			  return new ActionForward("/sales/cancelEndcaseVocationOrderAction.do?id="+tb.getId());			     
//		  }else if ( tb.getId().startsWith("WI") ){
//			  return new ActionForward("/sales/cancelEndcaseWebIndentAction.do?id="+tb.getId());			     
//		  }else if ( tb.getId().startsWith("IO") || tb.getId().startsWith("WO")){
//			  return new ActionForward("/sales/cancelEndcaseIntegralOrderAction.do?id="+tb.getId());			     
//		  }else if ( tb.getId().startsWith("OM") ){
//			  return new ActionForward("/warehouse/cancelCompleteStockAlterMoveShipmentAction.do?id="+tb.getId());			     
//		  }else if ( tb.getId().startsWith("SM") ){
//			  return new ActionForward("/warehouse/cancelCompleteStockMoveShipmentAction.do?id="+tb.getId());			     
//		  }
		      
		  return mapping.findForward("audit");
		  
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return new ActionForward(mapping.getInput());
	}

}
