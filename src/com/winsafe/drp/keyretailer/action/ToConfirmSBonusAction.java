package com.winsafe.drp.keyretailer.action;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.util.DBUserLog;

public class ToConfirmSBonusAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ToConfirmSBonusAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
			List<Map<String,String>> list = appSBonusConfig.getYearList();
			request.setAttribute("list", list);
			DBUserLog.addUserLog(request, "");
			return mapping.findForward("toupd");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
}
