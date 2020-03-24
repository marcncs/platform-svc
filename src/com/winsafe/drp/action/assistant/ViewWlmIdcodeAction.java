package com.winsafe.drp.action.assistant;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import common.Logger;

public class ViewWlmIdcodeAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ViewWlmIdcodeAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.initdata(request);
		try{
			//获得查询码
			String cartonCode = request.getParameter("cartonCodeSearch").toUpperCase().trim();
			//查询
			WlmIdcodeService wis = new WlmIdcodeService();
			Map<String,Object> map = wis.execute(cartonCode,users);
			
			//用于显示
			//查询码为小码时，显示小码
			if (map.get("viewPc") != null) {
				request.setAttribute("viewPc", map.get("viewPc"));
			}
			//显示产品信息
			if (map.get("p") != null) {
				request.setAttribute("p", map.get("p"));
			}
			//显示printjob信息
			if (map.get("pg") != null) {
				request.setAttribute("pg", map.get("pg"));
			}
			//显示箱码
			if (map.get("existString") != null) {
				request.setAttribute("existString", map.get("existString"));
			}
			//显示箱码信息
			if (map.get("cc") != null) {
				request.setAttribute("cc", map.get("cc"));
			}
			//显示小码信息
			if (map.get("pc") != null) {
				request.setAttribute("pc", map.get("pc"));
			}
			//物流信息
			if (map.get("lttall") != null) {
				request.setAttribute("lttall", map.get("lttall"));
			}
			//显示提示信息
			if (map.get("prompt") != null) {
				request.setAttribute("prompt", map.get("prompt"));
			}
			//显示物流信息
			if (map.get("wlmessage") != null) {
				request.setAttribute("wlmessage", map.get("wlmessage"));
			}
			//显示提交申请按钮
			if (map.get("noRight") != null) {
				request.setAttribute("noRight", map.get("noRight"));
			}
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("",e);
		} 
		return new ActionForward(mapping.getInput());
	}
}
