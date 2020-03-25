package com.winsafe.drp.action.sys;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScanner;
import com.winsafe.drp.util.DBUserLog;

/**
 * 配置采集器
* @Title: ToConfigScannerAction.java
* @author: jason.huang 
* @CreateTime: sec ,10 2014 13:53:13 
* @version: 
 */
public class ToListScannerAction extends BaseAction {
	 public ActionForward execute(ActionMapping mapping, ActionForm form,
             HttpServletRequest request,
             HttpServletResponse response) throws Exception {
		int pagesize = 10;
	 	super.initdata(request);
		AppScanner s = new AppScanner();
		try {
			String where = " where 1 = 1 ";
			String id = request.getParameter("idSearch");
			String model = request.getParameter("modelSearch");
			String osVersion = request.getParameter("osVersionSearch");
			String status = request.getParameter("statusSearch");
			String installDate = request.getParameter("installDateSearch");
			String appVersion = request.getParameter("appVersionSearch");
			String appVerUpDate = request.getParameter("appVerUpDateSearch");
			String wHCode = request.getParameter("wHCodeSearch");
			String scannerName = request.getParameter("scannerNameSearch");
			String lastUpDate = request.getParameter("lastUpDateSearch");
			String scannerImeiN = request.getParameter("scannerImeiNSearch");
			if(null != id && !"".equals(id)){
				request.setAttribute("id", id);
				where += " and f.id like '%"+id.trim()+"%' ";
			}
			if(null != model && !"".equals(model)){
				request.setAttribute("model", model);
				where += " and f.model like '%"+model.trim()+"%' ";
			}
			if(null != osVersion && !"".equals(osVersion)){
				request.setAttribute("osVersion", osVersion);
				where += " and f.osVersion like '%"+osVersion.trim()+"%' ";
			}
			if(null != status && !"".equals(status)){
				request.setAttribute("status", status);
				where += " and f.status like '%"+status.trim()+"%' ";
			}
			if(null != installDate && !"".equals(installDate)){
				request.setAttribute("installDate", installDate);
				where += " and TO_CHAR(installDate,'YYYY-MM-DD')= '"+installDate+"' ";
			}
			if(null != appVersion && !"".equals(appVersion)){
				request.setAttribute("appVersion", appVersion);
				where += " and f.appVersion like '%"+appVersion.trim()+"%' ";
			}
			if(null != appVerUpDate && !"".equals(appVerUpDate)){
				request.setAttribute("appVerUpDate", appVerUpDate);
				where += " and TO_CHAR(APPVERUPDATE,'YYYY-MM-DD')= '"+appVerUpDate+"' ";
			}
			if(null != wHCode && !"".equals(wHCode)){
				request.setAttribute("wHCode", wHCode);
				where += " and f.wHCode like '%"+wHCode.trim()+"%' ";
			}
			if(null != scannerName && !"".equals(scannerName)){
				request.setAttribute("scannerName", scannerName);
				where += " and f.scannerName like '%"+scannerName.trim()+"%' ";
			}
			if(null != lastUpDate && !"".equals(lastUpDate)){
				request.setAttribute("lastUpDate", lastUpDate);
				where += " and TO_CHAR(LASTUPDATE,'YYYY-MM-DD')= '"+lastUpDate+"' ";
			}
			if(null != scannerImeiN && !"".equals(scannerImeiN)){
				request.setAttribute("scannerImeiN", scannerImeiN);
				where += " and f.scannerImeiN like '%"+scannerImeiN.trim()+"%' ";
			}
			//获取采集器信息
			List list = s.selectScanner(request,pagesize,where);
			request.setAttribute("scanner",list);
			DBUserLog.addUserLog(request, "[列表]");
			return mapping.findForward("success");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
	}
	
}
