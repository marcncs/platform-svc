package com.winsafe.sap.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrintJob;

public class DelPrintJobAction extends BaseAction{
	private static Logger logger = Logger.getLogger(DelPrintJobAction.class);
	
	private AppPrintJob appPrintJob = new AppPrintJob();
	private AppCartonCode appCartonCode = new AppCartonCode();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		Integer printJobId = Integer.valueOf(request.getParameter("printJobId"));
		
		try {
			appPrintJob.delPrintJob(printJobId);
			appCartonCode.delByPrintJobId(printJobId);
			request.setAttribute("result", "databases.del.success");
		} catch (Exception e) {
			request.setAttribute("result", "databases.del.fail");
			logger.error(e);
			throw e;
		}
		DBUserLog.addUserLog(request, "编号" + printJobId);
		return mapping.findForward("delPrintJob");
	}

}
