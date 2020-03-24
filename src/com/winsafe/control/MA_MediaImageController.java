package com.winsafe.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winsafe.control.pojo.ResponseData;
import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.dao.MediaImage;
import com.winsafe.drp.dao.MediaImageGroup;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ParameterUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.yun.service.IMediaImageGroupService;
import com.winsafe.drp.yun.service.IMediaImageService;
import com.winsafe.yun.services.impl.MediaImageGroupService;
import com.winsafe.yun.services.impl.MediaImageService;

@Controller
@RequestMapping("/mfr_admin/media/images")
public class MA_MediaImageController {
//	@Autowired
	private IMediaImageService imageService = new MediaImageService();
//	@Autowired
	private IMediaImageGroupService groupService = new MediaImageGroupService();
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(HttpServletRequest request) throws Exception {
		Integer groupId = ParameterUtil.getInteger(request, "group_id");
		Integer currentPage = ParameterUtil.getInteger(request, "currentPage");
		Integer pageSize = ParameterUtil.getInteger(request, "pageSize");
		if (currentPage == null) currentPage = 1;
		if (pageSize == null) pageSize = 12;
		if(groupId == null) {
			groupId = 0;
		}
		
		PageBean<MediaImage> images = imageService.queryForPage(1, groupId, currentPage, pageSize);
//		List<MediaImage> mediaImages = serv.getMediaImage(groupId, request, pageSize, currentPage);
//		MediaImageGroup mediaImagesGroups = serv.getMediaImageGroup(groupId);
		MediaImageGroup group = groupService.get(groupId != null ? groupId : 0);

		request.setAttribute("pageBean", images);
		request.setAttribute("group", group);
		return "/mfr_admin/media_image";
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void addImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResponseUtil.writeJsonMsg(response, new ResponseData(Constants.CODE_SUCCESS));
		/*String title = ParameterUtil.getString(request, "title");
		String url = ParameterUtil.getString(request, "url");
		Integer fsize = ParameterUtil.getInteger(request, "fsize");
		Integer groupId = ParameterUtil.getInteger(request, "group_id");
		
//		Integer manufacturerId = SessionManager.getManufacturerAccount(request).getManufacturerId();
		
		try {
//			MediaImage image = imageService.addImage(manufacturerId, title, url, fsize, groupId);
			serv.addMediaImage(title, url, fsize, groupId);
			JSONObject json = ResponseUtil.getJsonMsg(Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, Constants.CODE_SUCCESS_MSG, "data");
			ResponseUtil.write(response,json.toString(),"utf-8");
		} catch (Exception e) {
			JSONObject json = ResponseUtil.getJsonMsg(Constants.CODE_ERROR, Constants.CODE_ERROR_MSG, Constants.CODE_ERROR_MSG, "data");
			ResponseUtil.write(response,json.toString(),"utf-8");
		}*/
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public void getlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer groupId = ParameterUtil.getInteger(request, "group_id");
		Integer currentPage = ParameterUtil.getInteger(request, "current_page");
		Integer pageSize = ParameterUtil.getInteger(request, "page_size");
		if (currentPage == null) currentPage = 1;
		if (pageSize == null) pageSize = 12;
		
//		Integer manufacturerId = SessionManager.getManufacturerAccount(request).getManufacturerId();
//		PageBean<MediaImage> images = imageService.queryForPage(manufacturerId, groupId, currentPage, pageSize);
		PageBean<MediaImage> images = imageService.queryForPage(1, groupId, currentPage, pageSize);
		images.setCurrent_page(images.getCurrentPage());
		images.setPage_size(images.getPageSize());
		images.setTotal_page(images.getTotalPage());
		images.setTotal_rows(images.getTotalRows());;
		
//		List<Map<String,String>> mediaImages = serv.getMediaImage(request, pageSize, currentPage);
//		Map<Object,Object> map = new HashMap<>();
//		map.put("list", mediaImages);
//		JSONObject json = ResponseUtil.getJsonMsg(Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, map, "data");
		ResponseUtil.writeJsonMsg(response, new ResponseData(images));
//		return new ResponseEntity<ResponseData>(new ResponseData(mediaImages), HttpStatus.OK);
	}
	
}
