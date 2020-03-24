package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScanner;
import com.winsafe.drp.dao.Scanner;
import com.winsafe.drp.util.DBUserLog;

public class UpdScannerAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化
		initdata(request);
		String id = request.getParameter("idSearch");
		String model = request.getParameter("modelSearch");
		String osVersion = request.getParameter("osVersionSearch");
		String status = request.getParameter("statusSearch");
		String installDate = request.getParameter("installDateSearch");
		// String appVersion = request.getParameter("appVersionSearch");
		// String appVerUpDate = request.getParameter("appVerUpDateSearch");
		// String wHCode = request.getParameter("wHCodeSearch");
		String scannerName = request.getParameter("scannerNameSearch");
		// String lastUpDate = request.getParameter("lastUpDateSearch");
		String scannerImeiN = request.getParameter("scannerImeiNSearch");
		try {
			AppScanner s = new AppScanner();
			Scanner ls = new Scanner();
			ls.setId(Long.parseLong(id));
			ls.setModel(model);
			ls.setOsVersion(osVersion);
			ls.setStatus(Long.valueOf(status));
			// ls.setInstallDate(DateUtil.StringToDate(installDate));
			// ls.setAppVersion(appVersion);
			// ls.setAppVerUpDate(DateUtil.StringToDate(appVerUpDate));
			// ls.setwHCode(Long.valueOf(wHCode));
			ls.setScannerName(scannerName);
			// ls.setLastUpDate(DateUtil.StringToDate(lastUpDate));
			ls.setScannerImeiN(scannerImeiN);
			s.updScannerByID(ls.getId(), ls.getModel(), ls.getOsVersion(), ls.getStatus(),
					installDate, ls.getScannerName(), ls.getScannerImeiN());
			DBUserLog.addUserLog(request, "采集器编号：" + scannerImeiN);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}

}
