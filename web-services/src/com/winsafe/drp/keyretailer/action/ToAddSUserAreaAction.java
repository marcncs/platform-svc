package com.winsafe.drp.keyretailer.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSUserArea;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ToAddSUserAreaAction extends BaseAction{
	private AppSUserArea appSUserArea = new AppSUserArea();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		int pagesize = 10;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		
		String blur = DbUtil.getOrBlur(map, tmpMap, "USERID","LOGINNAME","REALNAME");
		if(!StringUtil.isEmpty(blur)) {
			blur = " and " + blur;
		}
		blur = DbUtil.getWhereSql(blur);
		
		List<Map<String,String>> users = appSUserArea.getUsersToAddToArea(request, pagesize, blur);
		
		request.setAttribute("userList", users);
		return mapping.findForward("toadd");
	}
		
}
