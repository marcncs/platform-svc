package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.SaleTrades;
import com.winsafe.drp.dao.ShipmentBill;

public class ToBlankoutShipmentBillAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		super.initdata(request);try{
			AppShipmentBill apb = new AppShipmentBill();
			ShipmentBill pb = apb.getShipmentBillByID(id);
			
			if (pb.getIsaudit()==1) {
				request.setAttribute("result", "对不起,该单据已经完成配送，不能作废！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			
			if ( pb.getBsort() == 9 ) {
				request.setAttribute("result", "对不起,渠道换货往下级发货不能作废！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			
			if ( pb.getBsort() == 1 || pb.getBsort() == 2 || pb.getBsort() == 3 
					|| pb.getBsort() == 6 || pb.getBsort() == 7){
				
				if ( apb.isComplete(pb.getBsort(), pb.getId()) ){
					request.setAttribute("result", "databases.record.alreadyshipmentnocancel");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			
			if ( pb.getBsort() == 11 ){
				AppSaleTrades appst = new AppSaleTrades();
				SaleTrades st = appst.getSaleTradesByID(id);
				
				if ( st.getIsendcase() == 1  ){
					request.setAttribute("result", "databases.record.alreadyshipmentnocancel");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			request.setAttribute("id", id);
			
	       
		      return mapping.findForward("toblankout");
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
	
}
