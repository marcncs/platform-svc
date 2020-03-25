package com.winsafe.drp.action.scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScanner;
import com.winsafe.drp.util.DBUserLog;

public class DelScannerAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String strid = request.getParameter("ID");
		super.initdata(request);
		try {
			AppScanner as = new AppScanner();
			String scannerImeiN = as.getImeiNById(strid);
			as.delScanner(strid);
			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(request, "编号：" + scannerImeiN);
			return mapping.findForward("success");
		} catch (Exception e) {

		}
		return new ActionForward(mapping.getInput());
	}

}
