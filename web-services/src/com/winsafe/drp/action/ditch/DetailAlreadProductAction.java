package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.OrganProduct;
import com.winsafe.drp.dao.Product;

/**
 * @author : jerry
 * @version : 2009-10-15 上午10:34:11 www.winsafe.cn
 */
public class DetailAlreadProductAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.initdata(request);
		try {
			Long id = Long.valueOf(request.getParameter("ID"));
			AppOrganProduct aop = new AppOrganProduct();
			OrganProduct op = aop.getOrganProductByID(id);
			AppProduct ap = new AppProduct();
			AppProductStruct aps = new AppProductStruct();
			Product p = ap.getProductByID(op.getProductid());
			request.setAttribute("p", p);
			request.setAttribute("psidnameen", aps.getEnName(p.getPsid()));
			request.setAttribute("psidname", aps.getName(p.getPsid()));
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.execute(mapping, form, request, response);
	}

}
