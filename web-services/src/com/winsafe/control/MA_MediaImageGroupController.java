package com.winsafe.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.winsafe.control.pojo.ResponseData;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.yun.services.YunServices;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mfr_admin/media/images/groups")
public class MA_MediaImageGroupController {
//	@Autowired
//	private IMediaImageGroupService groupService;
	private YunServices serv = new YunServices();
	
	@RequestMapping(value="", method = RequestMethod.GET)
	public void list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		List<Map<String,String>> mediaImagesGroups = serv.getMediaImageGroup();
		Map<Object,Object> map = new HashMap<>();
		map.put("list", mediaImagesGroups);
		JSONObject json = ResponseUtil.getJsonMsg(Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, new ResponseData(mediaImagesGroups), "data");
		ResponseUtil.write(response,json.toString(),"utf-8");
//		return new ResponseEntity<ResponseData>(new ResponseData(groups), HttpStatus.OK);
	}
	
	/*@RequestMapping(value="/{group_id}", method = RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable("group_id") int groupId) {
		MediaImageGroup group = groupService.get(groupId);
		return new ResponseEntity<ResponseData>(new ResponseData(group), HttpStatus.OK);
	}
	
	@RequestMapping(value="", method = RequestMethod.POST)
	public ResponseEntity<?> addGroup(HttpServletRequest request) {
		Integer manufacturerId = SessionManager.getManufacturerAccount(request).getManufacturerId();
		String name = ParameterUtil.getString(request, "name");
		
		try {
			MediaImageGroup group = groupService.addGroup(manufacturerId, name);
			return new ResponseEntity<ResponseData>(new ResponseData(group), HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<ResponseData>(new ResponseData(ServiceExpType.InvalidParam.getCode(), 
					e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (ServiceException e) {
			return new ResponseEntity<ResponseData>(new ResponseData(e.getCode(), e.getDesc()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/{group_id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateGroup(@PathVariable("group_id") int groupId, HttpServletRequest request) {
		String name = ParameterUtil.getString(request, "name");
		
		try {
			MediaImageGroup group = groupService.updateGroup(groupId, name);
			return new ResponseEntity<ResponseData>(new ResponseData(group), HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<ResponseData>(new ResponseData(ServiceExpType.InvalidParam.getCode(), 
					e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (ServiceException e) {
			return new ResponseEntity<ResponseData>(new ResponseData(e.getCode(), e.getDesc()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/{group_id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteGroup(@PathVariable("group_id") int groupId) {
		groupService.deleteGroup(groupId);
		
		return new ResponseEntity<ResponseData>(new ResponseData("ok"), HttpStatus.OK);
	}*/
}
