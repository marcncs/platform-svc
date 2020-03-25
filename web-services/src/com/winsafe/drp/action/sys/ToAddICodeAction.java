package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.FileConstant;

public class ToAddICodeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String pid = (String) request.getSession().getAttribute("pdid");
			AppProduct ap = new AppProduct();
			Product p = ap.getProductByID(pid);
			request.setAttribute("iclength", FileConstant.icode_length);
			request.setAttribute("p", p);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mapping.findForward("toadd");
	}

}
