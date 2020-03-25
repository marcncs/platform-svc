package com.winsafe.drp.action.equip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class SelectBillAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;

    try {
    	 String strcid = request.getParameter("cid");    	
		    if (strcid == null) {
		    	strcid = (String) request.getSession().getAttribute("cid");
		    }
    	    
    	    request.getSession().setAttribute("cid",strcid);
    	    
    	    
        String strCondition = " sb.istrans=0 and sb.cid='" + strcid.trim()+"' ";
        Map map = new HashMap(request.getParameterMap());
        Map tmpMap = EntityManager.scatterMap(map);
        String[] tablename={"ShipmentBill"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename);
        String blur = DbUtil.getOrBlur(map, tmpMap, "sb.linkman","ReceiveAddr");

        whereSql = whereSql  + blur +strCondition;
        whereSql = DbUtil.getWhereSql(whereSql); 
       

        AppShipmentBill aps = new AppShipmentBill();
        AppInvoiceConf aic = new AppInvoiceConf();
        List pls = aps.searchShipmentBill(request, pagesize, whereSql);
        List als = new ArrayList();
        for (int i = 0; i < pls.size(); i++) {
        	ShipmentBillForm sldf = new ShipmentBillForm();
			ShipmentBill o = (ShipmentBill) pls.get(i);
			sldf.setId(o.getId());
			sldf.setPaymentmode(o.getPaymentmode());
			sldf.setPaymentmodename(Internation.getStringByPayPositionDB(
					o.getPaymentmode()));	
			sldf.setInvmsg(o.getInvmsg());
			sldf.setInvmsgname(aic.getInvoiceConfById(o.getInvmsg()).getIvname());
			sldf.setTotalsum(o.getTotalsum());
			als.add(sldf);
		}


        request.setAttribute("cid", strcid);
        request.setAttribute("sls",als);
        return mapping.findForward("selectbill");
      }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
