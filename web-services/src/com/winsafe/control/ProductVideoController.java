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

import com.winsafe.control.expection.ServiceException;
import com.winsafe.control.pojo.ResponseData;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ParameterUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.pager2.Page;
import com.winsafe.yun.services.YunServices;

@Controller
@RequestMapping(value={"/api/product/videos", "/api/product/{product_id}/videos"})
public class ProductVideoController {
	
	/*@Autowired
	private IProductVideoService videoService;*/
	private YunServices serv = new YunServices();
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public void getVideos(@PathVariable("product_id") String productId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (productId == null) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
			return;
		}
		Integer currentPage = ParameterUtil.getInteger(request, "current_page");
		Integer pageSize = ParameterUtil.getInteger(request, "page_size");
		if (currentPage == null) currentPage = 1;
		if (pageSize == null) pageSize = 20;
		
		try {
			List<Map<String,String>> list = serv.getProductVideo(request, pageSize, currentPage, productId);
			/*if ("true".equalsIgnoreCase(queryAll)) {
				pageBean = productService.getByPage(currentPage, pageSize, manufacturerId);
			} else {
				pageBean = productService.getPublic(currentPage, pageSize, manufacturerId, null, null);
			}*/
			Map<String,Object> data = new HashMap<>();
			data.put("list", list);
			data.put("totalPage", ((Page)request.getAttribute("page")).getTotalPage());
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, data);
		} catch (Exception e) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
		}
	}
	
	@RequestMapping(value="/{video_id}", method=RequestMethod.GET)
	public void get(@PathVariable("video_id") String videoId, HttpServletResponse response) throws Exception {
		if (videoId == null) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
		}
		serv.updProductVideoViewCount(videoId);
		List<Map<String,String>> list = serv.getProductVideoById(videoId);
		if(list.size() > 0) {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, list.get(0));
		} else {
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, null);
		}
	}
	
	@RequestMapping(value="/updcover/{video_id}")
	public String updCover(@PathVariable("video_id") String videoId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Map<String, String>> video = serv.getProductVideoById(videoId);
		request.setAttribute("MVID", videoId);
		if(video.size()>0) {
			Map<String, String> map = video.get(0);
			request.setAttribute("picUrl", map.get("imgurl"));
		}
		return "/mfr_admin/change_cover";
	}
	
	@RequestMapping(value="/changecover", method=RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String picUrl = ParameterUtil.getString(request, "pic_url");
		String videoId = ParameterUtil.getString(request, "videoId");
		
		if (StringUtils.isBlank(picUrl)) {
			ResponseUtil.writeJsonMsg(response, new ResponseData("3", "代表图不能为空"));
			return;
		}
		if (StringUtils.isBlank(picUrl)) {
			ResponseUtil.writeJsonMsg(response, new ResponseData("3", "视频编号为空"));
			return;
		}
		
		try {
			serv.updProductVideoCover(videoId, picUrl);
			ResponseUtil.writeJsonMsg(response, new ResponseData());
		} catch (ServiceException e) {
			ResponseUtil.writeJsonMsg(response, new ResponseData(e.getCode(), e.getDesc()));
		} catch (Exception e) {
			WfLogger.error("", e);
			ResponseUtil.writeJsonMsg(response, new ResponseData(Constants.CODE_ERROR,"网络异常"));
		}
	}
}
