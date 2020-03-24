package com.winsafe.drp.keyretailer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;

public class ToUpdSBonusConfigAction extends BaseAction {
	private AppSBonusConfig appSBonusConfig = new AppSBonusConfig();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		SBonusConfig bnous = appSBonusConfig.getSBonusConfigById(id);
		request.setAttribute("config", bnous);
		request.setAttribute("id", id);
		return mapping.findForward("toupd");
	}
}
