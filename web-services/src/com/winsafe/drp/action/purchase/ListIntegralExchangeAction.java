package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIntegralExchange;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.IntegralExchange;
import com.winsafe.drp.dao.IntegralExchangeForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ListIntegralExchangeAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		try{
			String productid = request.getParameter("PID");
			request.getSession().setAttribute("pid", productid);

	      AppIntegralExchange abr = new AppIntegralExchange();
	      AppProduct ap = new AppProduct();
	      
	      List apls = abr.getIntegralExchangeByProductID(productid);
	      ArrayList alpl = new ArrayList();
	      for(int i=0;i<apls.size();i++){
	    	  IntegralExchangeForm aff = new IntegralExchangeForm();
	    	  IntegralExchange o = (IntegralExchange)apls.get(i);
	        aff.setId(o.getId());
	        aff.setProductid(o.getProductid());
	        aff.setProductidname(ap.getProductByID(o.getProductid()).getProductname());
	        aff.setUnitid(o.getUnitid());
	        aff.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", 
	                o.getUnitid()));
	        
	        aff.setUnitintegral(o.getUnitintegral());
	        
	        alpl.add(aff);
	      }
	      UsersBean users = UserManager.getUser(request);
	      Integer userid = users.getUserid();
	      //DBUserLog.addUserLog(userid,"列表积分兑换");
	      request.setAttribute("alpl",alpl);

	      return mapping.findForward("list");
	    }catch(Exception e){
	      e.printStackTrace();
	    }
		return new ActionForward(mapping.getInput());
	}
}
