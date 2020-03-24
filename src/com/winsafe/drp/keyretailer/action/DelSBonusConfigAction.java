package com.winsafe.drp.keyretailer.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.util.DBUserLog;

public class DelSBonusConfigAction extends BaseAction {
	private static Logger logger = Logger.getLogger(DelSBonusConfigAction.class);
	
	private AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			String id = request.getParameter("id");
			SBonusConfig sbs = appSBonusConfig.getSBonusConfigById(id);
			
			appSBonusConfig.delSBonusConfig(sbs);
			
			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(request, "删除,编号" + sbs.getId());
			return mapping.findForward("del");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	

}
