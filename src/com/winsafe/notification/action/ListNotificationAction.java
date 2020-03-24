package com.winsafe.notification.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.notification.dao.AppNotification;

public class ListNotificationAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListNotificationAction.class);
	
	private AppNotification appNotification = new AppNotification();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "Notification" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String deliveryDateCondition = DbUtil.getTimeCondition((String)tmpMap.get("BeginDeliveryDate"), (String)tmpMap.get("EndDeliveryDate")," deliveryDate");
		whereSql = whereSql + deliveryDateCondition;
		String estimateDateCondition = DbUtil.getTimeCondition((String)tmpMap.get("BeginEstimateDate"), (String)tmpMap.get("EndEstimateDate")," estimateDate");
		whereSql = whereSql + estimateDateCondition;
		whereSql = DbUtil.getWhereSql(whereSql);
		
		List notifications = appNotification.getNotifications(request, pagesize, whereSql);
		
		request.setAttribute("notifications", notifications);
		DBUserLog.addUserLog(request, "查看列表");
		return mapping.findForward("list");
	}
}
