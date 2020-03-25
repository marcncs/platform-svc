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
import com.winsafe.drp.dao.AppProductPriceii;
import com.winsafe.drp.dao.ProductPriceii;
import com.winsafe.drp.dao.ProductPriceiiForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.Internation;

public class ListProductPriceiiAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		try{
			String productid = request.getParameter("PID");
			request.getSession().setAttribute("pid", productid);

	      AppProductPriceii abr = new AppProductPriceii();
	      AppProduct ap = new AppProduct();
	      
	      List apls = abr.getProductPriceiiByProductID(productid);
	      ArrayList alpl = new ArrayList();
	      for(int i=0;i<apls.size();i++){
	    	  ProductPriceiiForm aff = new ProductPriceiiForm();
	    	  ProductPriceii o = (ProductPriceii)apls.get(i);
	        aff.setId(o.getId());
	        aff.setProductid(o.getProductid());
	        aff.setProductidname(ap.getProductByID(o.getProductid()).getProductname());
	        aff.setUnitid(o.getUnitid());
	        aff.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", 
	                o.getUnitid()));
	        aff.setPolicyid(o.getPolicyid());
	        aff.setPolicyidname(Internation.getStringByKeyPositionDB("OrganPricePolicy", 
	                o.getPolicyid().intValue()));
	        aff.setUnitprice(o.getUnitprice());
	        Double frate =o.getFrate();
	        if(frate==null||frate.equals("")){
				frate=0d;
			}
	        aff.setFrate(frate*100);
	        alpl.add(aff);
	      }
	      UsersBean users = UserManager.getUser(request);
	      Integer userid = users.getUserid();
	      DBUserLog.addUserLog(userid, 11,"产品资料>>列表经销商产品价格");
	      request.setAttribute("alpl",alpl);

	      return mapping.findForward("list");
	    }catch(Exception e){
	      e.printStackTrace();
	    }
		return new ActionForward(mapping.getInput());
	}
}
