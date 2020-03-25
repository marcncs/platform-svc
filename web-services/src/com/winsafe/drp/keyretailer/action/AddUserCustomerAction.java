package com.winsafe.drp.keyretailer.action;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppUserCustomer;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class AddUserCustomerAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddUserCustomerAction.class);
	
	private AppUserCustomer appUserCustomer = new AppUserCustomer();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			
			String organIds = request.getParameter("organIds");
			String userId = request.getParameter("userid");
			
			if(StringUtil.isEmpty(organIds)) {
				request.setAttribute("result", "机构编号参数为空,添加失败");
				return new ActionForward("/sys/operatorclose3.jsp");
			}
			if(StringUtil.isEmpty(userId)) {
				request.setAttribute("result", "用户编号参数为空,添加失败");
				return new ActionForward("/sys/operatorclose3.jsp");
			}
			organIds = organIds.substring(0, organIds.length() - 1);
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			
			String blur = DbUtil.getOrBlur(map, tmpMap, "o.id","o.OECODE","o.ORGANNAME");
			if(!StringUtil.isEmpty(blur)) {
				blur = " and " + blur;
			}
			blur = DbUtil.getWhereSql(blur);
			
			if("true".equals(request.getParameter("addAll"))) {
				appUserCustomer.addAllUserCustomer(request, userId, blur);
			} else {
				appUserCustomer.addUserCustomer(organIds,userId);
			}
			
			request.setAttribute("result", "新增成功");
			DBUserLog.addUserLog(request, "新增,机构编号" + organIds+" 用户编号"+userId); 
			return mapping.findForward("addresult");
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	

}
