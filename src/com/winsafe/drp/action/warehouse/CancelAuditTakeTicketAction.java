package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditTakeTicketAction extends BaseAction {
	private AppIdcode appidcode = new AppIdcode();
	private AppTakeTicketIdcode aptti = new AppTakeTicketIdcode();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);try{
			
			if ( !isTokenValid(request, true) ){
				 request.setAttribute("result", "databases.record.alreadysubmit");
				 saveToken(request);
	             return new ActionForward("/sys/lockrecordclose.jsp");
			}
			String id = request.getParameter("id");
			AppTakeBill atb = new AppTakeBill(); 
			AppTakeTicket apb = new AppTakeTicket();
			TakeTicket tt = apb.getTakeTicketById(id);

			if ( tt.getIsblankout() == 1){
	             request.setAttribute("result", "databases.record.blankoutnooperator");
	             return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (tt.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.noaudit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if ( atb.getTakeBillByID(tt.getBillno()).getIsaudit() == 1 ){
				request.setAttribute("result", "databases.record.close");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppShipmentBill apsb = new AppShipmentBill();	
			ShipmentBill sb = apsb.getShipmentBillByID(tt.getBillno());
			if ( sb != null ){
				if ( sb.getIsaudit() == 1 ){
					request.setAttribute("result", "datebases.record.shipmentbillaudit");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			
			AppTakeTicketDetail apttd= new AppTakeTicketDetail();
			List<TakeTicketDetail> pils = apttd.getTakeTicketDetailByTtid(id);
				
			for (TakeTicketDetail ttd : pils ) {
				
				if ( tt.getBsort().intValue() == 0 ){
					apttd.updSaleOrderTakeQuantity(tt.getBillno(), ttd.getProductid(), 0d, tt.getWarehouseid());
				}else{
					apttd.updTakeQuantity(tt.getBsort(), tt.getBillno(), ttd.getProductid(), 0d);
				}
			}
					
			List<TakeTicketIdcode> ttilist = aptti.getTakeTicketIdcodeByttid(id);
			returnOutProductStockpile(ttilist, tt.getWarehouseid());
			
			List<TakeTicketIdcode> idcodelist = aptti.getTakeTicketIdcodeByttid(id, 1);
			
			setIdcodeUse(idcodelist);
//			for (TakeTicketIdcode tti : idcodelist) {				
//				
//				if (tt.getBillno().startsWith("OM") || tt.getBillno().startsWith("SM")){					
//					appwi.delIdcodeDetailByPidBillid(wi.getProductid(), tt.getBillno());
//				}
//			}			
			
			apb.updIsAudit(id, userid, 0);
			
			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 7, "检货出库>>取消复核检货小票,编号：" + id);
			
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	
	private void returnOutProductStockpile(List<TakeTicketIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		for (TakeTicketIdcode idcode : idlist ) {			
			aps.returnOutProductStockpile(idcode.getProductid(), idcode.getUnitid(),idcode.getBatch(), 
				idcode.getQuantity(), warehouseid, idcode.getWarehousebit(),idcode.getTtid(),"取消检货小票-入货");
		}
	}
	
	private void setIdcodeUse(List<TakeTicketIdcode> idlist) throws Exception{
		for ( TakeTicketIdcode idcode : idlist ){
			idcode.setIssplit(0);
			aptti.updTakeTicketIdcode(idcode);
			appidcode.updIsUseOut(idcode.getIdcode(), 1, 0);			
			
//			if ( idcode.getIssplit() == 1 ){
//				
//				Idcode ic = appidcode.getIdcodeByStartno(idcode.getStartno());
//				ic.setQuantity(ic.getQuantity()+1);
//				if ( ic.getQuantity().doubleValue() == ic.getPackquantity().doubleValue() ){
//					ic.setIsuse(1);	
//					appidcode.delIdcodeByid(idcode.getIdcode());	
//				}else{
//					ic.setIsuse(0);		
//					appidcode.updIsUse(idcode.getIdcode(), 1);
//				}
//				appidcode.updIdcode(ic);
//					
//			}else{
//				appidcode.updIsUse(idcode.getIdcode(), 1);
//			}
		}
	}

}
