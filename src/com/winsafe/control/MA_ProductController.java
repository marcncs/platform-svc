package com.winsafe.control;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winsafe.drp.dao.AppPopularProduct;
import com.winsafe.drp.dao.PopularProduct;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.ParameterUtil;
import com.winsafe.drp.util.WfLogger;

@Controller
@RequestMapping("/mfr_admin/product")
public class MA_ProductController {
	
	private static Logger logger = LoggerFactory.getLogger(MA_ProductController.class);

	private AppPopularProduct appPp = new AppPopularProduct();

	@RequestMapping(value = "/{product_id}", method = RequestMethod.GET)
	public String productDetail(@PathVariable("product_id") String productId, HttpServletRequest request) throws Exception {
		//判断有没有修改的权限
		/*ManufacturerAccount account = SessionManager.getManufacturerAccount(request);
		if (!securityService.permit(account.getId(), productId, BusinessType.PRODUCT)) {
			request.setAttribute("error_code", ServiceExpType.PermissionError);
			request.setAttribute("error_msg", "你没有访问该产品信息的权限");
			return "/mfr_admin/error";
		}*/
				
		PopularProduct product = appPp.getPopularProductByID(productId);
		if (product == null) {
			logger.warn("产品未找到,productid:{}", productId);
			return "/mfr_admin/error";
		}
		
		request.setAttribute("product", product);
		
		return "/mfr_admin/product_detail";
	}

	@RequestMapping(value = "/update/{product_id}", method = RequestMethod.POST)
	public String updateBasicInfo(@PathVariable("product_id") String productId, HttpServletRequest request) {
		//判断有没有修改的权限
		/*ManufacturerAccount account = SessionManager.getManufacturerAccount(request);
		if (!securityService.permit(account.getId(), productId, BusinessType.PRODUCT)) {
			request.setAttribute("error_code", ServiceExpType.PermissionError);
			request.setAttribute("error_msg", "你没有修改该产品信息的权限");
			return "/mfr_admin/error";
		}*/

		String slogan = ParameterUtil.getString(request, "slogan");
		String component = ParameterUtil.getString(request, "component");
		String certification = ParameterUtil.getString(request, "certification");
		
		try {
			appPp.updPopularProduct(productId, ESAPIUtil.decodeForHTML(slogan), ESAPIUtil.decodeForHTML(component), certification);
		} catch (Exception e) { //产品未找到
			WfLogger.error("", e);
			return "/mfr_admin/error";
		}

		return "redirect:/qr/mfr_admin/product/" + productId;
	}
	
	
}
