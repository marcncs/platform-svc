package com.winsafe.drp.action.machin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.util.DBUserLog;
import com.winsafe.sap.dao.AppQualityInspection;

public class DelQualityInspectionAction extends Action {
	private static Logger logger = Logger.getLogger(DelQualityInspectionAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppQualityInspection aQI = new AppQualityInspection();
		Integer id = Integer.valueOf(request.getParameter("ID"));
		try {
			aQI.delQualityInspection(id);
			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(request, "编号：" + id);

			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("", e);
			request.setAttribute("result", "databases.del.fail");
		}
		return mapping.findForward("success");
	}
}

