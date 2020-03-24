package com.winsafe.erp.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.erp.dao.AppProductConfig;
import com.winsafe.erp.pojo.ProductConfig;
import com.winsafe.erp.pojo.ProductConfigForm;

public class ToUpdProductConfigAction extends BaseAction {

	private AppProductConfig appProductConfig = new AppProductConfig();
	private AppProduct appProduct = new AppProduct();
	private static Logger logger = Logger.getLogger(ToUpdProductConfigAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		initdata(request);
		try {
			String id = request.getParameter("ID");

			ProductConfig productConfig = appProductConfig.getProductConfigByID(Integer.valueOf(id));
			ProductConfigForm productConfigForm = new ProductConfigForm();
			
			productConfigForm.setId(productConfig.getId());
			productConfigForm.setmCode(productConfig.getmCode());
			productConfigForm.setOrganId(productConfig.getOrganId());
			productConfigForm.setProductId(productConfig.getProductId());
			productConfigForm.setErpProductId(productConfig.getErpProductId());
			Product product = appProduct.getProductByID(productConfigForm.getProductId());
			if (product != null) {
				productConfigForm.setSpecmode(product.getSpecmode());
				productConfigForm.setProductName(product.getProductname());
			}
			
			request.setAttribute("productConfigForm", productConfigForm);
		} catch (Exception e) {
			logger.error("", e);
		}
		return mapping.findForward("success");
	}

}
