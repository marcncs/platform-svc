package com.winsafe.control;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winsafe.drp.action.common.YunParameter;
import com.winsafe.drp.dao.AppManufacturer;
import com.winsafe.drp.dao.AppManufacturerContact;
import com.winsafe.drp.dao.AppPopularProduct;
import com.winsafe.drp.dao.Manufacturer;
import com.winsafe.drp.dao.PopularProduct;
import com.winsafe.drp.util.JsonUtil;
import com.winsafe.hbm.util.TextKeywordProcessor;
import com.winsafe.hbm.util.TextUtil;
import com.winsafe.mail.util.StringUtils;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/elabel")
public class PopularProductController {
	
	private AppPopularProduct app = new AppPopularProduct();
	private AppManufacturer amf = new AppManufacturer();
	private AppManufacturerContact amc = new AppManufacturerContact();
	@RequestMapping(value="/{PPID}/content", method=RequestMethod.PUT)
	public String updateContent(@PathVariable("PPID") String productId, HttpServletRequest request) {
//		String posterUrl = request.getParameter("poster_url");
		String content = request.getParameter("content");
//		if (StringUtils.isEmpty(posterUrl)) {
//			return new ResponseEntity<ResponseData>(new ResponseData(ServiceExpType.InvalidParam.getCode(), 
//					"产品海报不能为空"), HttpStatus.BAD_REQUEST);
//		}
		if (StringUtils.isEmpty(content)) {
//			return new ResponseEntity<ResponseData>(new ResponseData(ServiceExpType.InvalidParam.getCode(),"产品图文内容不能为空"), HttpStatus.BAD_REQUEST);
			request.setAttribute("result", "产品图文内容不能为空!");
			return "/sys/operatorclose";
		}
		
		try {
			PopularProduct pp = app.getPopularProductByID(productId);
			pp.setContent(content);
			app.updPopularProduct(pp);
			request.setAttribute("result", "databases.upd.success");
			return "/sys/operatorclose";
//			return new ResponseEntity<ResponseData>(new ResponseData(product), HttpStatus.OK);
		} catch (Exception e) {
			request.setAttribute("result", "修改异常,请联系管理员!");
			return "/sys/operatorclose";
//			return new ResponseEntity<ResponseData>(new ResponseData(e.getCode(), e.getDesc()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/detail/{product_id}", method = RequestMethod.GET)
	public String productDetail(@PathVariable("product_id") String productId, HttpServletRequest request , HttpServletResponse response) throws Exception {
		PopularProduct product = app.getPopularProductByID(productId);
		if (product == null) {
			return "/elabel/error";
		}
		
		//处理内容关键词语，百科中有的添加链接
		String content = TextKeywordProcessor.process(product.getContent(), new String[]{product.getName()});
		content = TextUtil.appendSuffix4ImgSrcAttribute(content, YunParameter.IMG_PREVIEW_SIZE_W_1440);
		product.setContent(content);
		
		Manufacturer manufacturer = amf.getManufacturerById(product.getManufacturerId());
		request.setAttribute("product_info", product);
		request.setAttribute("manufacturer_info", manufacturer);
		
		JSONObject json = new JSONObject();
		json.put("product", product);
		JsonUtil.setJsonObj(response, json);
		
		return "/elabel/index";
	}
	
	@RequestMapping(value = "/{product_id}/cases", method = RequestMethod.GET)
	public String index_cases(@PathVariable("product_id") String productId, HttpServletRequest request) throws Exception {
		PopularProduct product = app.getPopularProductByID(productId);
		if (product == null) {
			return "/elabel/error";
		}
		Manufacturer manufacturer = amf.getManufacturerById(product.getManufacturerId());
		request.setAttribute("product_info", product);
		request.setAttribute("manufacturer_info", manufacturer);
		return "/elabel/product_cases";
	}
}
