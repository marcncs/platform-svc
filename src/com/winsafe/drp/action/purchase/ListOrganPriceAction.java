package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganPrice;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.OrganPrice;
import com.winsafe.drp.dao.OrganPriceForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ListOrganPriceAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
	      Integer userid = users.getUserid();
		try{
			String productid = request.getParameter("PID");
			request.getSession().setAttribute("pid", productid);
			String oid = users.getMakeorganid();

	      AppOrganPrice abr = new AppOrganPrice();
	      AppProduct ap = new AppProduct();
	      AppOrgan ao = new AppOrgan();
	      
	      String Condition =" where organid='"+oid+"' and productid='"+productid+"'";
	      
	      List apls = abr.getOrganPriceByProductID(Condition);
	      ArrayList alpl = new ArrayList();
	      for(int i=0;i<apls.size();i++){
	    	  OrganPriceForm opf = new OrganPriceForm();
	    	  OrganPrice o = (OrganPrice)apls.get(i);
	    	  opf.setId(o.getId());
	    	  opf.setOrganid(o.getOrganid());
	    	  opf.setOrganidname(ao.getOrganByID(o.getOrganid()).getOrganname());
	    	  opf.setProductid(o.getProductid());
	    	  opf.setProductidname(ap.getProductByID(o.getProductid()).getProductname());
	    	  opf.setUnitid(o.getUnitid());
	    	  opf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", 
	                o.getUnitid()));
	    	  opf.setPolicyid(o.getPolicyid());
	    	  opf.setPolicyidname(Internation.getStringByKeyPositionDB("PricePolicy", 
	                o.getPolicyid().intValue()));
	    	  opf.setUnitprice(o.getUnitprice());
	        
	        alpl.add(opf);
	      }
	      
	      //DBUserLog.addUserLog(userid,"列表产品价格");
	      request.setAttribute("alpl",alpl);

	      return mapping.findForward("list");
	    }catch(Exception e){
	      e.printStackTrace();
	    }
		return new ActionForward(mapping.getInput());
	}
}
