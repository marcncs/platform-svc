package com.winsafe.sap.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.dao.AppPrintLog;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob; 
import com.winsafe.sap.pojo.PrintLog;

public class ListPrintJobDetailAction extends BaseAction{
	private static Logger logger = Logger.getLogger(ListPrintJobDetailAction.class);
	
	private AppPrintLog appPrintLog = new AppPrintLog();
	private AppPrintJob appPrintJob = new AppPrintJob();
	private AppCartonCode appCartonCode = new AppCartonCode();
	private AppPrimaryCode appPrimaryCode = new AppPrimaryCode();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		String printJobId = request.getParameter("ID");
		int actionType = Integer.parseInt(request.getParameter("actionType"));
		if(actionType == 1) {
			PrintJob detail = appPrintJob.getPrintJobByID(Integer.parseInt(printJobId));
			request.setAttribute("printJobDetail", detail);
			if(detail.getNumberOfCases() != null) {
				request.setAttribute("copys", detail.getTotalNumber()/detail.getNumberOfCases());
			}
			DBUserLog.addUserLog(request, "详细");
			return mapping.findForward("detail");
		} else if(actionType == 2) {
			String whereSql = " where printJobId=" + printJobId;
			List<PrintLog> printLogs = appPrintLog.getPrintLog(request, pagesize, whereSql);
			request.setAttribute("printLogs", printLogs);
			DBUserLog.addUserLog(request, "打印历史");
			return mapping.findForward("printHistory");
		} else if(actionType == 3){
			String whereSql = " where printJobId=" + printJobId;
//			List<CartonCode> cartonCodes = appCartonCode.getCartonCode(request, pagesize, whereSql);
			List<CartonCode> cartonCodes = appCartonCode.getCartonCodeOrder(request, pagesize, whereSql);
			request.setAttribute("cartonCodes", cartonCodes);
			DBUserLog.addUserLog(request, "箱码信息");
			return mapping.findForward("cartonCodeList");
		} else {
			String whereSql = " where printJobId=" + printJobId;
//			List<CartonCode> cartonCodes = appCartonCode.getCartonCode(request, pagesize, whereSql);
			List<PrimaryCode> primaryCodes = appPrimaryCode.getPrimaryCode(request, pagesize, whereSql);
			request.setAttribute("primaryCodes", primaryCodes);
			DBUserLog.addUserLog(request, "暗码信息");
			return mapping.findForward("covertCodeList");
		}
		
	}
}
