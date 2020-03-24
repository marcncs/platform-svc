package com.winsafe.notification.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.notification.dao.AppNotification;
import com.winsafe.notification.dao.AppNotificationDetail;
import com.winsafe.notification.pojo.Notification;

public class ListNotificationDetailAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListNotificationDetailAction.class);
	
	private AppNotificationDetail appNotificationDetail = new AppNotificationDetail();
	private AppNotification appNotification = new AppNotification();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		String id = request.getParameter("ID");
		Notification notification = appNotification.getNotificationById(id);
		List nfDetails = appNotificationDetail.getNDetailsByDeliveryNo(id);
		
		request.setAttribute("nf", notification);
		request.setAttribute("nfd", nfDetails);
		DBUserLog.addUserLog(request, "查看详细");
		return mapping.findForward("list");
	}
}
