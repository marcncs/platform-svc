package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//Connection conn = null;
		super.initdata(request);try{

			String sbid = request.getParameter("SBID");
			AppShipmentBill asb = new AppShipmentBill();
			ShipmentBill sb = asb.getShipmentBillByID(sbid);
			//Long warehouseid = sb.getWarehouseid();
			if(sb.getIsaudit()==1){
	          	 String result = "databases.record.nodel";
	               request.setAttribute("result", "databases.del.success");
	               return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
	           }
			

			AppShipmentBillDetail asbd = new AppShipmentBillDetail();
			AppProductStockpile aps = new AppProductStockpile();
		    List ls = asbd.getShipmentBillDetailBySbID(sbid);

			asb.delShipmentBill(sbid);
			asbd.delShipmentProductBillBySbID(sbid);
			
//			String freeproductid;
//			String freebatch;
//			Double freequantity;
//		    for(int i=0;i<ls.size();i++){
//		    	Object[] o=(Object[])ls.get(i);
//		    	freeproductid = o[2].toString();
//		    	freebatch = o[6].toString();
//		    	freequantity = Double.valueOf(o[8].toString());
//				String preupd = aps.freeStockpile(freeproductid, warehouseid, freebatch, freequantity);
//		    }
			
		      request.setAttribute("result", "databases.del.success");
		      UsersBean users = UserManager.getUser(request);
//		      Long userid = users.getUserid();
//		      DBUserLog.addUserLog(userid,"删除出库单"); 
			
			return mapping.findForward("del");
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		return null;
	}

}
