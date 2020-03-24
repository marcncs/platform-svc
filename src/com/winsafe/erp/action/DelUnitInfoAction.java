package com.winsafe.erp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppUnitInfo;

public class DelUnitInfoAction extends BaseAction {

	private AppUnitInfo appUnitInfo = new AppUnitInfo();
	private static Logger logger = Logger.getLogger(DelUnitInfoAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);

		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			appUnitInfo.deleteUnitInfo(id);
			DBUserLog.addUserLog(request, "编号：" + id);
			request.setAttribute("result", "删除成功");
		} catch (Exception e) {
			logger.error("DelUnitInfoAction  error:", e);
		}
		return mapping.findForward("success");
	}

}
