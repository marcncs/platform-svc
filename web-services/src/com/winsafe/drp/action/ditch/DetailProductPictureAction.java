package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProductPicture;
import com.winsafe.drp.dao.OrganProduct;
import com.winsafe.drp.dao.ProductPicture;

/**
 * @author : jerry
 * @version : 2009-10-15 上午10:50:15 www.winsafe.cn
 */
public class DetailProductPictureAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			Long id = Long.valueOf(request.getParameter("ID"));
			AppOrganProduct aop = new AppOrganProduct();
			OrganProduct op = aop.getOrganProductByID(id);
			AppProductPicture app = new AppProductPicture();
			ProductPicture pp = app.getProductPictureById(Integer.valueOf(op
					.getProductid()));
			request.setAttribute("pp", pp);
			return mapping.findForward("detail");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.execute(mapping, form, request, response);
	}
}
