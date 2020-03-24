package com.winsafe.drp.action.yun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPopularProduct;
import com.winsafe.drp.dao.PopularProduct;

public class ToAddPopularProductDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping , ActionForm form , HttpServletRequest request , HttpServletResponse response) throws Exception{
	
		AppPopularProduct app = new AppPopularProduct();
		String popularProductID = request.getParameter("PPID");
		
		try {
			initdata(request); 
			PopularProduct popularProduct = app.getPopularProductByID(popularProductID);
			
			request.setAttribute("content", popularProduct.getContent());
			request.setAttribute("PPID", popularProductID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("todetail");
	}
}
