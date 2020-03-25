package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductPrice;
import com.winsafe.drp.dao.ProductPrice;
import com.winsafe.drp.dao.ProductPriceForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ListProductPriceAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		try{
			String productid = request.getParameter("PID");
			request.getSession().setAttribute("pid", productid);

	      AppProductPrice abr = new AppProductPrice();
	      AppProduct ap = new AppProduct();
	      
	      List apls = abr.getProductPriceByProductID(productid);
	      ArrayList alpl = new ArrayList();
	      for(int i=0;i<apls.size();i++){
	    	  ProductPriceForm aff = new ProductPriceForm();
	    	  ProductPrice o = (ProductPrice)apls.get(i);
	        aff.setId(o.getId());
	        aff.setProductid(o.getProductid());
	        aff.setProductidname(ap.getProductByID(o.getProductid()).getProductname());
	        aff.setUnitid(o.getUnitid());
	        aff.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", 
	                o.getUnitid()));
	        aff.setPolicyid(o.getPolicyid());
	        aff.setPolicyidname(Internation.getStringByKeyPositionDB("PricePolicy", 
	                o.getPolicyid().intValue()));
	        aff.setUnitprice(o.getUnitprice());
	        
	        alpl.add(aff);
	      }
	      UsersBean users = UserManager.getUser(request);
	      Integer userid = users.getUserid();
	      //DBUserLog.addUserLog(userid,"列表产品价格");
	      request.setAttribute("alpl",alpl);

	      return mapping.findForward("list");
	    }catch(Exception e){
	      e.printStackTrace();
	    }
		return new ActionForward(mapping.getInput());
	}
}
