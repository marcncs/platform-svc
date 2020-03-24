package com.winsafe.drp.action.sys;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRegionArea;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.StringUtil;

public class DelRegionAreaAction extends BaseAction {

	private AppRegionArea appRegionArea = new AppRegionArea();
	private Logger logger = Logger.getLogger(AddRegionAreaAction.class);
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		String pids = request.getParameter("pid");
		if(!StringUtil.isEmpty(pids)) {
			appRegionArea.delAll(pids);
			request.setAttribute("result", "databases.del.success");
		} else {
			logger.error("大区与行政区关联ID为空");
			request.setAttribute("result", "databases.del.fail");
		}
		DBUserLog.addUserLog(request, "关联关系");	
		return mapping.findForward("success");
	}
}
