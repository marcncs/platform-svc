package com.winsafe.erp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppProductPlan;

public class AjaxIsUploadProductPlanAction extends BaseAction {

	private AppProductPlan app = new AppProductPlan();
	private static Logger logger = Logger.getLogger(DelProductPlanAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String ids = request.getParameter("productPlanId");
			ids = ids.substring(0, ids.length()-1);
		try {
			app.updIsUpload(ids);
			
			DBUserLog.addUserLog(request, "编号：" + ids);
			request.setAttribute("result", "标记成功!");
		} catch (Exception e) {
			logger.error("UpdProductPlanAction  error:", e);
		}
		return mapping.findForward("success");
	}
}