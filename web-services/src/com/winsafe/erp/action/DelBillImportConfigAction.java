package com.winsafe.erp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppBillImportConfig;

public class DelBillImportConfigAction extends BaseAction {

	private AppBillImportConfig abic = new AppBillImportConfig();
	private static Logger logger = Logger.getLogger(DelBillImportConfigAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);

		try {
			Integer bicid = Integer.valueOf(request.getParameter("ID"));
			abic.deleteBillImportConfig(bicid);
			DBUserLog.addUserLog(request, "编号："+bicid);
			request.setAttribute("result", "删除成功");
		} catch (Exception e) {
			logger.error("DelBillImportConfigAction  error:", e);
		}
		return mapping.findForward("success");
	}

}
