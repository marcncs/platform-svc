package com.winsafe.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ParameterUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.yun.services.YunServices;


@Controller
@RequestMapping(value={"/api/manufacturer/news", "/api/manufacturer/{manufacturer_id}/news"})
public class ManufacturerNewsController {
//	@Autowired
//	private IManufacturerNewsService manufacturerNewSerice;
	private YunServices serv = new YunServices();
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public void list(@PathVariable("manufacturer_id") Integer manufacturerId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (manufacturerId == null) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
		}
		
		Integer currentPage = ParameterUtil.getInteger(request, "current_page");
		Integer pageSize = ParameterUtil.getInteger(request, "page_size");
		if (currentPage == null) currentPage = 1;
		if (pageSize == null) pageSize = 20;
		
		
		try {
			List<Map<String,String>> list = serv.getNews(request, pageSize, currentPage);
			/*if ("true".equalsIgnoreCase(queryAll)) {
				pageBean = productService.getByPage(currentPage, pageSize, manufacturerId);
			} else {
				pageBean = productService.getPublic(currentPage, pageSize, manufacturerId, null, null);
			}*/
			Map<String,Object> data = new HashMap<>();
			data.put("list", list);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, data);
		} catch (Exception e) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
		}
	}
	
	@RequestMapping(value="/{news_id}", method=RequestMethod.GET)
	public void get(@PathVariable("news_id") String newsId, HttpServletResponse response) throws Exception {
		if (newsId == null) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
		}
		serv.updNewsViewCountById(newsId);
		List<Map<String,String>> list = serv.getNewsById(newsId);
		if(list.size() > 0) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list.get(0));
		} else {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
		}
//		ManufacturerNews manuNew = manufacturerNewSerice.getById(newsId);
//		
//		//处理内容关键词语，百科中有的添加链接
//		String content = TextKeywordProcessor.process(manuNew.getContent(), null);
//		content = TextUtil.appendSuffix4ImgSrcAttribute(content, GlobalConstant.IMG_PREVIEW_SIZE_W_1440);
//		manuNew.setContent(content);
		
//		return new ResponseEntity<ResponseData>(new ResponseData(manuNew), HttpStatus.OK);
	}
	
	/*@RequestMapping(value="/{news_id}/pic", method=RequestMethod.PUT)
	public ResponseEntity<?> updatePic(@PathVariable("news_id") Integer newsId, HttpServletRequest request) {
		if (newsId == null) {
			return new ResponseEntity<ResponseData>(new ResponseData(ServiceExpType.InvalidParam), HttpStatus.BAD_REQUEST);
		}

		String picUrl = ParameterUtil.getString(request, "pic_url");
		String strIsShowPic = ParameterUtil.getString(request, "is_show_pic");
		if (StringUtils.isBlank(picUrl)) {
			return new ResponseEntity<ResponseData>(new ResponseData(ServiceExpType.InvalidParam), HttpStatus.BAD_REQUEST);
		}
		boolean isShowPic = true;
		if ("false".equalsIgnoreCase(strIsShowPic)) {
			isShowPic = false;
		}
		try {
			ManufacturerNews manuNew = manufacturerNewSerice.updateNewsPic(newsId, picUrl, isShowPic);
			return new ResponseEntity<ResponseData>(new ResponseData(manuNew), HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<ResponseData>(new ResponseData(e.getCode(), e.getDesc()), HttpStatus.BAD_REQUEST);
		}
	}*/
}
