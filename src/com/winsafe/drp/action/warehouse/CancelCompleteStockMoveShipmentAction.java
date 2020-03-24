package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class CancelCompleteStockMoveShipmentAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//	    Long userid = users.getUserid();
		String id = request.getParameter("id");

		super.initdata(request);try{
			AppStockMove asm = new AppStockMove();
			StockMove sm = asm.getStockMoveByID(id);
			if (sm.getIsshipment() ==0) { 
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			
			if(sm.getIscomplete() ==1){
				String result = "databases.record.alreadyshipmentnocancel";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			
//			if(!String.valueOf(sm.getShipmentid()).contains(userid.toString())){
//	          	 String result = "databases.record.cancelaudit";
//	               request.setAttribute("result",result);
//	               return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
//	           }
	
			
			AppShipmentBill appsb = new AppShipmentBill();
			AppShipmentBillDetail appsbd = new AppShipmentBillDetail();
//			List sblist = appsb.getShipmentBillBySoid(id);
			
			ShipmentBill sb = null;
//			for (int i = 0; i < sblist.size(); i++) {
//				sb = (ShipmentBill) sblist.get(i);
//				String sbid = sb.getId();
//				if (sb.getIsaudit() == 0) {
//					appsb.delShipmentBill(sbid);
//					appsbd.delShipmentProductBillBySbID(sbid);
//				}
//			}
//			
//			asm.updStockMoveIsShipment(id,0,userid);
			
		      request.setAttribute("result", "databases.add.success");
//		      DBUserLog.addUserLog(userid,"取消转仓单发货");
			return mapping.findForward("move");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
