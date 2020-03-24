package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCar;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToAddEquipAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    super.initdata(request);try{

    	String bids=request.getParameter("bids");    	
    	if(bids.endsWith(",")){    		
    		bids=bids.substring(0, bids.length()-1);
    	}

		AppShipmentBill asb=new AppShipmentBill();
		List bls=asb.getShipmentBillNew(bids);
		if ( bls.size() == 0 ){
			request.setAttribute("result", "databases.record.iscomplete");
			return new ActionForward("/sys/lockrecordclose.jsp");
		}
		for ( int i=0; i<bls.size(); i++ ){
			ShipmentBill bill = (ShipmentBill)bls.get(i);
			if ( bill.getIsaudit() == 1 ){
				request.setAttribute("result", "datebases.record.isauditnottrans");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
		}

		ShipmentBill sb=null;
		if(bls.size()>0){
		  sb=(ShipmentBill)bls.get(0);
		}		
		
		AppInvoiceConf aic = new AppInvoiceConf();
		List sblist = new ArrayList();
		for ( int i=0; i<bls.size(); i++ ){
			ShipmentBill bill = (ShipmentBill)bls.get(i);
			ShipmentBillForm sbf = new ShipmentBillForm();
			sbf.setId(bill.getId());
			sbf.setObjectsort(bill.getObjectsort());
			sbf.setTotalsum(bill.getTotalsum());
			sbf.setPaymentmode(bill.getPaymentmode());
			sbf.setPaymentmodename(Internation.getStringByPayPositionDB(bill.getPaymentmode()));
			sbf.setInvmsg(bill.getInvmsg());
			sbf.setInvmsgname(aic.getIvnameById(sb.getInvmsg()));
			sblist.add(sbf);
		}
		
		
		
		AppCar ac=new AppCar();
		UsersBean users = UserManager.getUser(request);
		String makeorganid = users.getMakeorganid();
		String Condition = " c.makeorganid = '" + makeorganid + "' ";
		List cls=ac.getAllCars(Condition);


		request.setAttribute("cls", cls);
		request.setAttribute("bls", sblist);
		request.setAttribute("sb", sb);
    	request.setAttribute("bids", bids);
    	 
      return mapping.findForward("success");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
