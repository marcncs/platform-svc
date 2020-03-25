package com.winsafe.notification.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.sms.dao.AppSms;
import com.winsafe.sms.pojo.Sms;

public class ListNotificationSmsAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListNotificationSmsAction.class);
	
	private AppSms appSms = new AppSms();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		String id = request.getParameter("ID");
		Sms sms = appSms.getSmsById(id);
		
		request.setAttribute("sms", sms);
		DBUserLog.addUserLog(request, "查看短信详细");
		return mapping.findForward("list");
	}
}
