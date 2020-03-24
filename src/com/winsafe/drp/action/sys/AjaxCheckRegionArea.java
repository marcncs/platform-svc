package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegionArea;
import com.winsafe.drp.util.ResponseUtil;

public class AjaxCheckRegionArea extends BaseAction {
	
	private static Logger logger = Logger.getLogger(AjaxCheckRegionArea.class);
	
	private AppRegionArea appRegionArea = new AppRegionArea();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			logger.debug("regionArea check use ajax!");
			StringBuffer errorMsg = new StringBuffer();
			
			String regioncodeid = request.getParameter("regioncodeid");
			String pids[] = request.getParameterValues("pid");
			if(pids != null) {
				StringBuffer ids = new StringBuffer();
				for(String pid : pids) {
					ids.append(pid).append(",");
				}
				//获取已存在的关联关系行政区域名
				List areaNames = appRegionArea.getAlreadyExistRegionArea(regioncodeid, ids.substring(0, ids.length() - 1));
				if(areaNames != null && areaNames.size() > 0) {
					errorMsg.append("行政区域  \r\n\r\n");
					for(Object obj : areaNames){
						errorMsg.append(obj).append(" ");
					}
					errorMsg.append(" \r\n\r\n已关联到其他区域 ,");
				}
			}
			
			// 以json数据返回
			JSONObject jsonObject = new JSONObject();
			if(errorMsg.length() > 0){
				jsonObject.put("returnCode", "-1");
				jsonObject.put("returnMsg", errorMsg.toString());
			}else {
				jsonObject.put("returnCode", "0");
				jsonObject.put("returnMsg", "");
			}
			ResponseUtil.write(response, jsonObject.toString());
			
			return null;
		
	}
}
