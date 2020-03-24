package com.winsafe.drp.action.yun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.common.YunParameter;
import com.winsafe.drp.dao.AppManufacturer;
import com.winsafe.drp.dao.AppPopularProduct;
import com.winsafe.drp.dao.Manufacturer;
import com.winsafe.drp.dao.PopularProduct;

public class ListPupProductAction extends Action {
	
	Logger logger = Logger.getLogger(ListPupProductAction.class);
	private AppManufacturer appManufacturer = new AppManufacturer();
	private AppPopularProduct app = new AppPopularProduct();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		PopularProduct product = app.getPopularProductByManufacturerID(YunParameter.BayerManufacturerID);
		Manufacturer manufacturer = appManufacturer.getManufacturerById(YunParameter.BayerManufacturerID);
		request.setAttribute("product_info", product); 
		request.setAttribute("manufacturer_info", manufacturer);
			
		return mapping.findForward("list");
	}
}
