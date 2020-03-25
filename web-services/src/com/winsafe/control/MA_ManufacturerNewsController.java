package com.winsafe.control;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.winsafe.control.expection.ServiceException;
import com.winsafe.control.pojo.ResponseData;
import com.winsafe.drp.action.common.YunParameter;
import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.dao.ManufacturerNews;
import com.winsafe.drp.metadata.ServiceExpType;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.ParameterUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.drp.yun.service.IManufacturerNewsService;
import com.winsafe.yun.services.impl.ManufacturerNewsService;

@Controller
@RequestMapping(value="/mfr_admin/news")
public class MA_ManufacturerNewsController {
	
	private static final Logger logger = LoggerFactory.getLogger(MA_ManufacturerNewsController.class);
	
//	@Autowired
	private IManufacturerNewsService manufacturerNewSerice = new ManufacturerNewsService();
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(HttpServletRequest request) {
		Integer currentPage = ParameterUtil.getInteger(request, "currentPage");
		Integer pageSize = ParameterUtil.getInteger(request, "pageSize");
		if (currentPage == null) currentPage = 1;
		if (pageSize == null) pageSize = 12;
	
		PageBean<ManufacturerNews> manufacturerNews = manufacturerNewSerice.getPage(YunParameter.BayerManufacturerID, currentPage, pageSize);
		
		request.setAttribute("pageBean", manufacturerNews);
		
		return "/mfr_admin/news";
	}
	
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public ResponseEntity<?> myNews(HttpServletRequest request) {
		Integer currentPage = ParameterUtil.getInteger(request, "current_page");
		Integer pageSize = ParameterUtil.getInteger(request, "page_size");

		PageBean<ManufacturerNews> manufacturerNews = manufacturerNewSerice.getPage(YunParameter.BayerManufacturerID, currentPage, pageSize);
		
		return new ResponseEntity<ResponseData>(new ResponseData(manufacturerNews), HttpStatus.OK); 
	}
	
	@RequestMapping(value="/{news_id}", method=RequestMethod.GET)
	public void get(@PathVariable("news_id") Integer newsId, HttpServletResponse response) throws Exception {
		if (newsId == null) {
			ResponseUtil.writeJsonMsg(response, new ResponseData(ServiceExpType.InvalidParam.getCode(), 
					ServiceExpType.InvalidParam.getDesc()));
			return;
		}
		
		ManufacturerNews manuNew = manufacturerNewSerice.getById(newsId);
		ResponseUtil.writeJsonMsg(response, new ResponseData(manuNew));
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<?> addNews(@RequestParam(value="file", required=false) MultipartFile file, HttpServletRequest request) {
		String title = ParameterUtil.getString(request, "title");
		String summary = ParameterUtil.getString(request, "summary");
		String content = ParameterUtil.getString(request, "content");
		String strIsShowPic = ParameterUtil.getString(request, "is_show_pic");

		if (StringUtils.isNotBlank(title) && title.trim().length() > 20) {
			return new ResponseEntity<ResponseData>(new ResponseData(
					ServiceExpType.InvalidParam.getCode(), "故事标题必须在20个字符以内"), HttpStatus.BAD_REQUEST);
		}
		if (StringUtils.isNotBlank(summary) && summary.trim().length() > 40) {
			return new ResponseEntity<ResponseData>(new ResponseData(
					ServiceExpType.InvalidParam.getCode(), "故事摘要必须在40个字符以内"), HttpStatus.BAD_REQUEST);
		}
		if (file == null || file.getSize() <= 0) {
			return new ResponseEntity<ResponseData>(new ResponseData("3", "代表图不能为空"), HttpStatus.BAD_REQUEST);
		}
		
		boolean isShowPic = true;
		if ("false".equalsIgnoreCase(strIsShowPic)) {
			isShowPic = false;
		}
		
		return null;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String picUrl = ParameterUtil.getString(request, "pic_url");
		String title = ParameterUtil.getString(request, "title");
		String summary = ParameterUtil.getString(request, "summary");
		String content = ParameterUtil.getString(request, "content");
		String strIsShowPic = ParameterUtil.getString(request, "is_show_pic");
		
		if (StringUtils.isNotBlank(title) && title.trim().length() > 35) {
			ResponseUtil.writeJsonMsg(response, new ResponseData(ServiceExpType.InvalidParam.getCode(), "故事标题必须在35个字符以内"));
			return;
		}
		if (StringUtils.isNotBlank(summary) && summary.trim().length() > 40) {
			ResponseUtil.writeJsonMsg(response, new ResponseData(ServiceExpType.InvalidParam.getCode(), "故事摘要必须在40个字符以内"));
			return;
		}
		if (StringUtils.isBlank(picUrl)) {
			ResponseUtil.writeJsonMsg(response, new ResponseData("3", "代表图不能为空"));
			return;
		}
		
		Integer isShowPic = 1;
		if ("false".equalsIgnoreCase(strIsShowPic)) {
			isShowPic = 0;
		}
		
//		ManufacturerAccount account = SessionManager.getManufacturerAccount(request);
//		Integer manufacturerId = account.getManufacturerId();
		
		try {
			ManufacturerNews manuNew = manufacturerNewSerice.addNews(YunParameter.BayerManufacturerID, title, summary, ESAPIUtil.decodeForHTML(content), ESAPIUtil.decodeForHTML(picUrl), isShowPic);
			ResponseUtil.writeJsonMsg(response, new ResponseData(manuNew));
		} catch (ServiceException e) {
			ResponseUtil.writeJsonMsg(response, new ResponseData(e.getCode(), e.getDesc()));
		} catch (Exception e) {
			WfLogger.error("", e);
			ResponseUtil.writeJsonMsg(response, new ResponseData(Constants.CODE_ERROR,"网络异常"));
		}
	}
	
	@RequestMapping(value="/{news_id}", method=RequestMethod.POST)
	public void updateNews(@PathVariable("news_id") Integer newsId,
			HttpServletRequest request , HttpServletResponse response) throws Exception {
		String title = ParameterUtil.getString(request, "title");
		String summary = ParameterUtil.getString(request, "summary");
		String content = ParameterUtil.getString(request, "content");
		String picUrl = ParameterUtil.getString(request, "pic_url");
		String strIsShowPic = ParameterUtil.getString(request, "is_show_pic");
		
		//判断有没有修改的权限
//		ManufacturerAccount account = SessionManager.getManufacturerAccount(request);
//		if (!securityService.permit(account.getId(), newsId, BusinessType.MANUFACTURER_NEWS)) {
//			return new ResponseEntity<ResponseData>(new ResponseData(
//					ServiceExpType.PermissionError.getCode(), "你没有修改该新闻的权限"), HttpStatus.BAD_REQUEST);
//		}

		if (StringUtils.isNotBlank(title) && title.trim().length() > 35) {
//			return new ResponseEntity<ResponseData>(new ResponseData(
//					ServiceExpType.InvalidParam.getCode(), "新闻标题必须在20个字符以内"), HttpStatus.BAD_REQUEST);
			ResponseUtil.writeJsonMsg(response, new ResponseData(ServiceExpType.InvalidParam.getCode(), "故事标题必须在20个字符以内"));
			return;
		}
		if (StringUtils.isNotBlank(summary) && summary.trim().length() > 40) {
//			return new ResponseEntity<ResponseData>(new ResponseData(
//					ServiceExpType.InvalidParam.getCode(), "新闻摘要必须在40个字符以内"), HttpStatus.BAD_REQUEST);
			ResponseUtil.writeJsonMsg(response, new ResponseData(ServiceExpType.InvalidParam.getCode(), "故事摘要必须在40个字符以内"));
			return;
		}
		
		Integer isShowPic = 1;
		if ("false".equalsIgnoreCase(strIsShowPic)) {
			isShowPic = 0;
		}
		
		try {
			ManufacturerNews manuNews = manufacturerNewSerice.updateNews(newsId, title, summary, ESAPIUtil.decodeForHTML(content), ESAPIUtil.decodeForHTML(picUrl), isShowPic);
			ResponseUtil.writeJsonMsg(response, new ResponseData(manuNews));
//			return new ResponseEntity<ResponseData>(new ResponseData(manuNews), HttpStatus.OK);
		} catch (ServiceException e) {
//			return new ResponseEntity<ResponseData>(new ResponseData(e.getCode(), e.getDesc()), 
//					HttpStatus.BAD_REQUEST);
			ResponseUtil.writeJsonMsg(response, new ResponseData(ServiceExpType.InvalidParam.getCode(), "网络异常"));
		}
	}
	
	@RequestMapping(value="/{news_id}/d", method=RequestMethod.POST)
	public void deleteNews(@PathVariable("news_id") Integer newsId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//判断有没有删除的权限
//		ManufacturerAccount account = SessionManager.getManufacturerAccount(request);
//		if (!securityService.permit(account.getId(), newsId, BusinessType.MANUFACTURER_NEWS)) {
//			return new ResponseEntity<ResponseData>(new ResponseData(
//					ServiceExpType.PermissionError.getCode(), "你没有删除该新闻的权限"), HttpStatus.BAD_REQUEST);
//		}
		
		if (newsId == null) {
			ResponseUtil.writeJsonMsg(response, new ResponseData(ServiceExpType.InvalidParam.getCode(), 
					ServiceExpType.InvalidParam.getDesc()));
		}
		
		manufacturerNewSerice.deleteNews(newsId);
		ResponseUtil.writeJsonMsg(response, new ResponseData("ok"));
	}
}
