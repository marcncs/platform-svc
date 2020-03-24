package com.winsafe.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winsafe.control.pojo.TraceJson;
import com.winsafe.drp.action.assistant.TraceService;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppQuery;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.QueryResult;
import com.winsafe.drp.metadata.ProductType;
import com.winsafe.drp.util.AjaxUtil;
import com.winsafe.drp.util.BaseResult;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.PlantConfig;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.yun.services.YunServices;

@Controller
public class TraceController { 
	
	private YunServices serv = new YunServices();
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String toIndex(HttpServletRequest request) throws Exception {
		request.setAttribute("traceUrl", PlantConfig.getConfig().getProperty("traceUrl"));
		return "trace/index";
	}
	
	@RequestMapping(value="/{code}",method=RequestMethod.GET)
	public String get(@PathVariable String code,HttpServletRequest request) throws Exception {
		if("qr".equals(code) || StringUtil.isEmpty(code)) {
			return "trace/index";
		}
		QueryResult result = getResult(request, code, false); 
		request.getSession().setAttribute("QueryResult", result);
//		return "trace/unqualified";
		if(result ==null || !result.isQualified()) {
			return "trace/unqualified";
		}
		if(ProductType.ES.getValue().toString().equals(result.getProductType())) {
			return "trace/qualified";
		} else {
			return "elabel/product_security";
		}
		
	}
	
	private void getOnlineShopUrl(HttpServletRequest request) {
		AppBaseResource app = new AppBaseResource();
		BaseResource url;
		try {
			url = app.getBaseResourceValue("OnlineShopUrl", 1);
			if(url != null) {
				request.setAttribute("OnlineShopUrl", url.getTagsubvalue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/getData/getCodeInfo",method=RequestMethod.GET)
	public void getCodeInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String code = request.getParameter("code");
			if("qr".equals(code) || StringUtil.isEmpty(code)) {
				JSONObject jsonObject = JSONObject.fromObject(new BaseResult(false, "条码为空"));
				AjaxUtil.ajaxReturn(jsonObject, response);
				return;
			}
			QueryResult result = getResult(request, code, false); 
			
			TraceJson tj = new TraceJson();
			if(result != null) {
				if(result.isQualified()) {
					TraceService wis = new TraceService();
					tj = wis.getFlowInfo(result);
				}
				tj.setProductId(result.getProductId());
				tj.setProductName(result.getProductName());
				tj.setProductionDate(result.getProductionDate());
				tj.setInspectionInstitution(result.getInspectionInstitution());
				tj.setBatch(result.getBatch());
				tj.setFlow(result.getFlow());
			}
			
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "查询成功", tj));
			AjaxUtil.ajaxReturn(jsonObject, response);
		} catch (Exception e) {
			WfLogger.error("", e);
			JSONObject jsonObject = JSONObject.fromObject(new BaseResult(true, "查询失败，系统异常"));
			AjaxUtil.ajaxReturn(jsonObject, response);
		}
		
	}
	
	private QueryResult getResult(HttpServletRequest request, String code, boolean isMannual) throws IOException {
		boolean isQualified = false;
		request.setAttribute("code", code);
		request.setAttribute("traceUrl", PlantConfig.getConfig().getProperty("traceUrl"));
		//查询 
		TraceService wis = new TraceService();
		AppQuery aq = new AppQuery();
		String ipString = aq.getIpAddr(request);
		QueryResult map = wis.execute(code, ipString, isMannual);
		if(map != null && map.isExist()) {
//			//设置默认的检验单位
//			if(StringUtil.isEmpty(map.getInspectionInstitution())) {
//				map.setInspectionInstitution(Constants.DEFAULT_INSPECT_INST);
//			}
			request.setAttribute("result", map);
			//由于视频播放页会丢失产品ID，因此把产品ID写入session中
			if (StringUtils.isNotBlank(map.getProductId())) {
				request.getSession().setAttribute("ELABEL_PRODUCT_ID", map.getProductId());
			}
			isQualified = true;
			if(StringUtil.isEmpty(map.getFlow())) {
//				map.setFlow(map.getInspectionInstitution());
				map.setFlow("拜耳作物科学（中国）有限公司");
			}
//			map.setPpid(wis.getPopularProductId(map.getProductName()));
//			return "trace/qualified";
			if(map.getProductionDate()!=null && map.getProductionDate().length() == 8) {
				String pdate = map.getProductionDate();
				map.setProductionDate(pdate.substring(0, 4)+"-"+pdate.substring(4,6)+"-"+pdate.substring(6));
			}
			map.setQualified(isQualified);
		}
		
		request.setAttribute("isQualified", isQualified);
		return map;
	}
	
	
	@RequestMapping(value="/getflow/{code}",method=RequestMethod.POST)
	public void getflow(@PathVariable String code,HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryResult result = (QueryResult)request.getSession().getAttribute("QueryResult");
		if(result == null || !result.isQualified()) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_NO_DATA, Constants.CODE_NO_DATA_MSG, null);
			return;
		}
		TraceJson tj = (TraceJson)request.getSession().getAttribute("TraceJson");
		if(tj == null) {
			TraceService wis = new TraceService();
			tj = wis.getFlowInfo(result);
			request.getSession().setAttribute("TraceJson", tj);
		}
		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, tj);
	}
	
	
	public static void main(String[] args) {
		String date = "20180419";
		System.out.println(date.substring(0, 4)+"-"+date.substring(4,6)+"-"+date.substring(6));
	}

	@RequestMapping(value="/result/{code}",method=RequestMethod.GET)
	public String query(@PathVariable String code, HttpServletRequest request) throws Exception {
		if("qr".equals(code) || StringUtil.isEmpty(code)) {
			return "trace/index";
		}
		if(code.length() != 21) {
//			return "trace/unqualifiedResult";
			request.setAttribute("isQualified", false);
			return "trace/qualifiedResult";
		}
		getResult(request, code, true);
//		return "trace/unqualifiedResult";
		return "trace/qualifiedResult";
	}
	
	@RequestMapping(value="/faq",method=RequestMethod.GET)
	public String fag(HttpServletRequest request) throws Exception {
		return "trace/faq";
	}
	
	@RequestMapping(value="/contact",method=RequestMethod.GET)
	public String contact(HttpServletRequest request) throws Exception {
		return "trace/contact";
	}
	
	@RequestMapping(value="/content/{productId}",method=RequestMethod.GET)
	public String contact(@PathVariable String productId, HttpServletRequest request) throws Exception {
		return "trace/productDetail";
	}
	
	@RequestMapping(value="/video/{productId}",method=RequestMethod.GET)
	public String video(@PathVariable String productId, HttpServletRequest request) throws Exception {
		return "trace/productVideo";
	}
	
	/** 企业热门产品引导页*/
	@RequestMapping(value = "/elabel/manufacturer/{manufacturer_id}/products", method = RequestMethod.GET)
	public String manufacturerProduct(@PathVariable("manufacturer_id") Integer manufacturerId, HttpServletRequest request) {
		request.setAttribute("manufacturer_id", manufacturerId);
		request.setAttribute("manufacturer_info", "拜耳");
		return "elabel/manufacturer_products";
	}
	
	/** 企业联系电话引导页
	 * @throws Exception */
	@RequestMapping(value = "/elabel/manufacturer/contacts", method = RequestMethod.GET)
	public String manufacturerContacts(HttpServletRequest request) throws Exception {
		//设置网上商城地址
		getOnlineShopUrl(request);
		List<Map<String,String>> list = serv.getContact(request);
		request.setAttribute("contacts", list);
		return "elabel/contact_us";
	}
	
	@RequestMapping(value = "/elabel/{product_id}", method = RequestMethod.GET)
	public String productDetail(@PathVariable("product_id") String productId, HttpServletRequest request) throws Exception {
		/*Product product = productService.getById(productId);
		if (product == null) {
			return "/elabel/error";
		}
		//判断产品是否通过审核（目前“待审核”和“审核通过”可显示，只有“审核被拒”不显示）
		if (product.getAuditStatus() == EAuditStatus.REJECT) {
			request.setAttribute("product_name", product.getName());
			request.setAttribute("manufacturer_id", product.getManufacturerId());
			return "/elabel/product_delist";
		}
		if ("true".equals(ParameterUtil.getString(request, "forceViewDelistedProduct"))) {
			//强制显示非上架产品，演示及后台手机预览用
			SessionManager.setForceViewDelistedProductId(request, productId);
		} else if (productId.equals(SessionManager.getForceViewDelistedProductId(request))) {
			//强制显示非上架产品，演示及后台手机预览用
		} else {
			//判断产品是否下架
			if (product.getListedStatus() != Product.EListedStatus.LISTED) {
				request.setAttribute("product_name", product.getName());
				request.setAttribute("manufacturer_id", product.getManufacturerId());
				return "/elabel/product_delist";
			}
		}
		
		//处理内容关键词语，百科中有的添加链接
		String content = TextKeywordProcessor.process(product.getContent(), new String[]{product.getName()});
		content = TextUtil.appendSuffix4ImgSrcAttribute(content, GlobalConstant.IMG_PREVIEW_SIZE_W_1440);
		product.setContent(content);
		
		Manufacturer manufacturer = manufacturerInfoService.getManufacturerById(product.getManufacturerId());
		request.setAttribute("product_info", product);
		request.setAttribute("manufacturer_info", manufacturer);*/
		
		List<Map<String,String>> list = serv.getProductDetailById(productId);
		if(list.size() > 0) {
			request.setAttribute("product_info", list.get(0));
		} else {
			request.setAttribute("product_info", null);
		}
		return "elabel/index";
	}
	
	@RequestMapping(value = "/elabel/hot/{product_id}", method = RequestMethod.GET)
	public String htoProductDetail(@PathVariable("product_id") String productId, HttpServletRequest request) throws Exception {
		List<Map<String,String>> list = serv.getProductDetailById(productId);
		if(list.size() > 0) {
			request.setAttribute("product_info", list.get(0));
		} else {
			request.setAttribute("product_info", null);
		}
		return "elabel/index";
	}
	
	@RequestMapping(value = "/elabel/{product_id}/feedbacks", method = RequestMethod.GET)
	public String index_feedbacks(@PathVariable("product_id") String productId, HttpServletRequest request) throws Exception {
		List<Map<String,String>> list = serv.getProductDetailById(productId);
		if(list.size() > 0) {
			request.setAttribute("product_info", list.get(0));
		} else {
			request.setAttribute("product_info", null);
		}
		return "elabel/product_feedbacks";
	}
	
	@RequestMapping(value = "/elabel/allfeedbacks", method = RequestMethod.GET)
	public String index_feedbacks_all(HttpServletRequest request) throws Exception {
		return "elabel/product_feedbacks_all";
	}
	
	@RequestMapping(value = "/elabel/{product_id}/videos", method = RequestMethod.GET)
	public String index_videos(@PathVariable("product_id") String productId, HttpServletRequest request) throws Exception {
		List<Map<String,String>> list = serv.getProductDetailById(productId);
		if(list.size() > 0) {
			request.setAttribute("product_info", list.get(0));
		} else {
			Map<String,String> map = new HashMap<>();
			map.put("id", productId);
			request.setAttribute("product_info", map);
		}
		return "/elabel/product_videos";
	}
	
	@RequestMapping(value = "/elabel/videos/{video_id}", method = RequestMethod.GET)
	public String index_video_detail(@PathVariable("video_id") Integer videoId,HttpServletRequest request) {
		request.setAttribute("video_id", videoId);
		return "/elabel/product_video_player";
	}
	
	/** 企业新闻引导页*/
	@RequestMapping(value = "/elabel/manufacturer/{manufacturer_id}/news", method = RequestMethod.GET)
	public String manufacturerNews(@PathVariable("manufacturer_id") Integer manufacturerId, HttpServletRequest request) {
		return "/elabel/manufacturer_news";
	}
	
	/** 企业新闻详情引导页 */
	@RequestMapping(value = "/elabel/manufacturer/news/{news_id}", method = RequestMethod.GET)
	public String index_news_detail(@PathVariable("news_id") Integer id, HttpServletRequest request) {
		request.setAttribute("news_id", id);
		return "/elabel/manufacturer_news_detail";
	}
	
	@RequestMapping(value = "/elabel/{product_id}/feedbacks/evaluation", method = RequestMethod.GET)
	public String index_user_evaluation(@PathVariable("product_id") String productId, HttpServletRequest request) {
		request.setAttribute("product_id", productId);
		return "/elabel/product_user_feedback";
	}
}
