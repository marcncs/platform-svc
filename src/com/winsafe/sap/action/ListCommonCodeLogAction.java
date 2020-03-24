package com.winsafe.sap.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.sap.dao.AppCommonCodeLog;
import com.winsafe.sap.pojo.CommonCodeLog;

public class ListCommonCodeLogAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListCommonCodeLogAction.class);
	
	AppCommonCodeLog appCommonCodeLog = new AppCommonCodeLog();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		String printJobId = request.getParameter("ID");
		String whereSql = " where printJobId = " + printJobId;
		
		List<CommonCodeLog> commonCodeLogs = appCommonCodeLog.getCommonCodeLog(request, pagesize, whereSql);
		
		request.setAttribute("commonCodeLogs", commonCodeLogs);
		return mapping.findForward("list");
		
	}
}
