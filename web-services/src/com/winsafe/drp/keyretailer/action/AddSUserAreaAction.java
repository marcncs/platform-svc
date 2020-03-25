package com.winsafe.drp.keyretailer.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSUserArea;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.StringUtil;

public class AddSUserAreaAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddSUserAreaAction.class);
	
	private AppSUserArea appSUserArea = new AppSUserArea();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			
			String userIds = request.getParameter("userIds");
			String areaid = request.getParameter("pid");
			
			if(StringUtil.isEmpty(userIds)) {
				request.setAttribute("result", "用户参数为空,添加失败");
				return new ActionForward("/sys/operatorclose3.jsp");
			}
			if(StringUtil.isEmpty(areaid)) {
				request.setAttribute("result", "区域编号参数为空,添加失败");
				return new ActionForward("/sys/operatorclose3.jsp");
			}
			userIds = userIds.substring(0, userIds.length() - 1);
			if("true".equals(request.getParameter("addAll"))) {
				appSUserArea.addAllSUserAreas(areaid);
			} else {
				appSUserArea.addSUserAreas(userIds,areaid);
			}
			
			request.setAttribute("result", "新增成功");
			DBUserLog.addUserLog(request, "新增,用户编号" + userIds+" 区域编号"+areaid);
			return mapping.findForward("addresult");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	

}
