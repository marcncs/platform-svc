package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWarehouse;

public class ToAffirmSampleBillAction extends BaseAction {

	  public ActionForward execute(ActionMapping mapping, ActionForm form,
	                               HttpServletRequest request,
	                               HttpServletResponse response) throws Exception {
	    String strshipmentid = request.getParameter("ShipmentID");
	    Long shipmentid = Long.valueOf(strshipmentid);
	    super.initdata(request);try{
	      AppWarehouse aw = new AppWarehouse();
	      List wls = aw.getCanUseWarehouse();
	      

	      request.setAttribute("shipmentid",shipmentid);
	      request.setAttribute("alw",wls);

	      return mapping.findForward("toaffirm");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    return new ActionForward(mapping.getInput());
	  }
}
