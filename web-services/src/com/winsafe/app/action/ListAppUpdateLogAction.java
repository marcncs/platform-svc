package com.winsafe.app.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.app.dao.AppUpdateLogDao;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListAppUpdateLogAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListAppUpdateLogAction.class);
	
	private AppUpdateLogDao appUpdateLogDao = new AppUpdateLogDao();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "AppUpdateLog" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," appVerUpDate");
		whereSql = whereSql + timeCondition;
		whereSql = DbUtil.getWhereSql(whereSql);
		
		List appUpdateLogs = appUpdateLogDao.getAppUpdates(request, pagesize, whereSql);
		
		request.setAttribute("appUpdateLogs", appUpdateLogs);
		DBUserLog.addUserLog(request, "查看列表");
		return mapping.findForward("list");
	}
}
