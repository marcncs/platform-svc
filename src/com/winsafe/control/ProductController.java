package com.winsafe.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winsafe.control.expection.ServiceException;
import com.winsafe.control.pojo.ResponseData;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.metadata.ServiceExpType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.ParameterUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.pager2.Page;
import com.winsafe.yun.services.YunServices;


@Controller
@RequestMapping(value={"/api/product", "/api/manufacturer/{manufacturer_id}/products"})
public class ProductController {

	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	private YunServices serv = new YunServices();
	/**
	 * 获取某一个企业中的产品列表，参数query_all=true则获取所有产品，否则读取公开产品
	 * @param manufacturerId	企业ID
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<?> getList(@PathVariable("manufacturer_id") Integer manufacturerId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String queryAll = ParameterUtil.getString(request, "query_all");
		Integer currentPage = ParameterUtil.getInteger(request, "current_page");
		Integer pageSize = ParameterUtil.getInteger(request, "page_size");
		if (currentPage == null) currentPage = 1;
		if (pageSize == null) pageSize = 20;
		try {
			List<Map<String,String>> list = serv.getProduct(request, pageSize, currentPage);
			/*if ("true".equalsIgnoreCase(queryAll)) {
				pageBean = productService.getByPage(currentPage, pageSize, manufacturerId);
			} else {
				pageBean = productService.getPublic(currentPage, pageSize, manufacturerId, null, null);
			}*/
//			PageBean<PopularProduct> pageBean = productService.getPublic(currentPage, pageSize, null, null, null);
			Map<String,Object> data = new HashMap<>();
			data.put("list", list);
			data.put("totalPage", ((Page)request.getAttribute("page")).getTotalPage());
//			ResponseUtil.writeJsonMsg(response, new ResponseData(pageBean));
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, data);
		} catch (Exception e) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
		}
//		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
		return null;
	}
	
	@RequestMapping(value = "/{product_id}", method = RequestMethod.GET)
	public void getDetail(@PathVariable("product_id") String productId, HttpServletResponse response) throws Exception {
		if (productId == null) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
		}
		List<Map<String,String>> list = serv.getProductById(productId);
		if (list.size() > 0) {
			ResponseUtil.writeJsonMsg(response, new ResponseData(list.get(0)));
//			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list.get(0));
		} else {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
		}
	}
	
	@RequestMapping(value="/{product_id}/content", method=RequestMethod.POST)
	public void updateContent(@PathVariable("product_id") String productId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String posterUrl = ParameterUtil.getString(request, "poster_url");
		String content = ParameterUtil.getString(request, "content");
		if (StringUtils.isBlank(posterUrl)) {
			ResponseUtil.writeJsonMsg(response, new ResponseData(ServiceExpType.InvalidParam.getCode(), 
					"产品海报不能为空"));
		}
		if (StringUtils.isBlank(content)) {
			ResponseUtil.writeJsonMsg(response, new ResponseData(ServiceExpType.InvalidParam.getCode(), 
					"产品图文内容不能为空"));
		}
		try {
			Product product = new Product();
			serv.updateContent(productId, ESAPIUtil.decodeForHTML(content), ESAPIUtil.decodeForHTML(posterUrl));
//			Product product = productService.updateContent(productId, content, posterUrl);
			ResponseUtil.writeJsonMsg(response, new ResponseData(product));
		} catch (ServiceException e) {
			ResponseUtil.writeJsonMsg(response, new ResponseData(e.getCode(), e.getDesc()));
		}
	}
	
	@RequestMapping(value="/{product_id}/pic", method=RequestMethod.POST)
	public void updatePic(@PathVariable("product_id") String productId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String picUrl = ParameterUtil.getString(request, "pic_url");
		if (productId == null) {
			ResponseUtil.writeJsonMsg(response, new ResponseData((ServiceExpType.InvalidParam)));
		}
		serv.updateProductPic(productId, picUrl);
		ResponseUtil.writeJsonMsg(response, new ResponseData());
	}
	
}
