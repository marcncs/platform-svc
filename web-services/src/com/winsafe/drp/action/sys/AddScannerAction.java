package com.winsafe.drp.action.sys;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScanner;
import com.winsafe.drp.dao.Scanner;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddScannerAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		String id = request.getParameter("idSearch");
		String model = request.getParameter("modelSearch");
		String osVersion = request.getParameter("osVersionSearch");
		String status = request.getParameter("statusSearch");
		String installDate = request.getParameter("installDateSearch");
//		String appVersion = request.getParameter("appVersionSearch");
//		String appVerUpDate = request.getParameter("appVerUpDateSearch");
		//String wHCode = request.getParameter("wHCodeSearch");
		String scannerName = request.getParameter("scannerNameSearch");
//		String lastUpDate = request.getParameter("lastUpDateSearch");
		String scannerImeiN = request.getParameter("scannerImeiNSearch");
		super.initdata(request);
	try{
		AppScanner s=new AppScanner();
		Scanner ls=new Scanner();
		String i=Integer.toString(s.getMaxScannerId());
		ls.setId(Long.parseLong(MakeCode.getExcIDByRandomTableName("scanner", 0, "")));
		ls.setModel(model);
		ls.setOsVersion(osVersion);
		ls.setStatus(Long.valueOf(status));
		ls.setInstallDate(Date.valueOf(installDate));
//		ls.setAppVersion(appVersion);
//		ls.setAppVerUpDate(Date.valueOf(appVerUpDate));
		//ls.setwHCode(Long.valueOf(wHCode));
		ls.setScannerName(scannerName);
//		ls.setLastUpDate(Date.valueOf(lastUpDate));
		ls.setScannerImeiN(scannerImeiN);
		s.addNewScanner(ls);
		request.setAttribute("result","databases.add.success");
		DBUserLog.addUserLog(request, "采集器编号："+scannerImeiN);
		return mapping.findForward("success");
	}catch(Exception e){
		e.printStackTrace();
	}finally{
	}
	return null;
  }
}