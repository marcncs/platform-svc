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
import com.winsafe.drp.dao.AppProductPicture;
import com.winsafe.drp.dao.ProductPicture;
import com.winsafe.drp.dao.ProductPictureForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class ListProductPictureAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		try{
			String productid = request.getParameter("PID");
			request.getSession().setAttribute("pid", productid);

	      AppProductPicture abr = new AppProductPicture();
	      AppProduct ap = new AppProduct();
	      
	      List apls = abr.getProductPictureByProductID(productid);
	      ArrayList alpl = new ArrayList();
	      for(int i=0;i<apls.size();i++){
	    	  ProductPictureForm ppf = new ProductPictureForm();
	    	  ProductPicture o = (ProductPicture)apls.get(i);
	    	  
	    	 ppf.setId(o.getId());
	    	 ppf.setProductid(o.getProductid());
	    	 ppf.setProductidname(ap.getProductByID(o.getProductid()).getProductname());
	    	 ppf.setPictureurl(o.getPictureurl());

	        alpl.add(ppf);
	      }
	      UsersBean users = UserManager.getUser(request);
	      Integer userid = users.getUserid();
	      DBUserLog.addUserLog(userid,11,"产品资料>>列表零售产品图片");
	      request.setAttribute("alpl",alpl);

	      return mapping.findForward("list");
	    }catch(Exception e){
	      e.printStackTrace();
	    }
		return new ActionForward(mapping.getInput());
	}
}
