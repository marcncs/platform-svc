package com.winsafe.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winsafe.drp.metadata.ServiceExpType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ParameterUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.yun.services.YunServices;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = { "/api/product/feedbacks", "/api/product/{product_id}/feedbacks" })
public class FeedbackController {

	private YunServices serv = new YunServices();

	// 查询所有产品的用户评价列表
	@RequestMapping(value = {"/all"}, method = RequestMethod.GET)
	public void getFeedBacks(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String queryAll = ParameterUtil.getString(request, "queryAll");
		Integer currentPage = ParameterUtil.getInteger(request, "current_page");
		Integer pageSize = ParameterUtil.getInteger(request, "page_size");
		if (currentPage == null) currentPage = 1;
		if (pageSize == null) pageSize = 20;
		List<Map<String,String>> list = serv.getAllFeedBacks(request, pageSize, currentPage);
		Map<String,Object> data = new HashMap<>();
		data.put("list", list);
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, data);
	}

	// 查询某一产品下的用户评价列表,参数queryAll=true则获取所有评论，否则读取已审核评论
	@RequestMapping(value = "", method = RequestMethod.GET)
	public void getFeedbacks(@PathVariable("product_id") String productId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (productId == null) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, Constants.CODE_ERROR_MSG, null);
			return;
		}
		String queryAll = ParameterUtil.getString(request, "queryAll");
		Integer currentPage = ParameterUtil.getInteger(request, "current_page");
		Integer pageSize = ParameterUtil.getInteger(request, "page_size");
		if (currentPage == null) currentPage = 1;
		if (pageSize == null) pageSize = 20;
		List<Map<String,String>> list = serv.getProductSerialFeedBack(request, pageSize, currentPage, productId);
		Map<String,Object> data = new HashMap<>();
		data.put("list", list);
		/*PageBean<ProductFeedback> feedbacks;
		if ("true".equalsIgnoreCase(queryAll)) {
			feedbacks = feedbackService.queryForPage(1, 99, productId, null, null, null);
		} else {
			feedbacks = feedbackService.queryForPage(1, 99, productId, null, null, EAuditStatus.PASSED);
		}*/
//		List<Map<String,String>> list = serv.getFeedBack(request, pageSize, currentPage, productId);
//		return new ResponseEntity<ResponseData>(new ResponseData(feedbacks), HttpStatus.OK);
		
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, data);
	}
	

	// 用户对某一个产品进行评价
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void addFeedbacks(@PathVariable("product_id") String productId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer score = ParameterUtil.getInteger(request, "score");
		String content = ParameterUtil.getString(request, "content");
		String picUrl = ParameterUtil.getString(request, "pic_url");
		if (StringUtils.isBlank(content) || content.length() > 500) {
			JSONObject json = ResponseUtil.getJsonMsg(Constants.CODE_ERROR, Constants.CODE_ERROR_MSG, ServiceExpType.InvalidParam.getCode(), "data");
			ResponseUtil.write(response,json.toString(),"utf-8");
			return;
		}

		try {
			String productName = serv.getProductName(productId);
//			ProductFeedback feedback = feedbackService.addFeedback(individual, productId, score, content, picUrl);
			serv.addFeedback(productId, score, content, picUrl, productName);
			
			JSONObject json = ResponseUtil.getJsonMsg(Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, Constants.CODE_SUCCESS_MSG, "data");
			ResponseUtil.write(response,json.toString(),"utf-8");
		} catch (NullPointerException e) {
			JSONObject json = ResponseUtil.getJsonMsg(Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, ServiceExpType.InvalidParam.getCode(), "data");
			ResponseUtil.write(response,json.toString(),"utf-8");
		} catch (Exception e) {
			JSONObject json = ResponseUtil.getJsonMsg(Constants.CODE_ERROR, Constants.CODE_ERROR_MSG, e.getMessage(), "data");
			ResponseUtil.write(response,json.toString(),"utf-8");
		}
	}
	
}