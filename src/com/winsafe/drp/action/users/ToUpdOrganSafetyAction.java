package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganSafetyIntercalate;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.OrganSafetyIntercalate;
import com.winsafe.drp.dao.Product;

public class ToUpdOrganSafetyAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Integer id = Integer.valueOf(request.getParameter("ID"));

		try {
			AppOrganSafetyIntercalate aws = new AppOrganSafetyIntercalate();
			OrganSafetyIntercalate ws = aws.getOrganSafetyIntercalateByID(id);
			
			AppProduct ap = new AppProduct();
			Product p = ap.getProductByID(ws.getProductid());
			request.setAttribute("productname", p==null?"":p.getProductname());
			request.setAttribute("wsf", ws);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}
		return null;
	}

}
