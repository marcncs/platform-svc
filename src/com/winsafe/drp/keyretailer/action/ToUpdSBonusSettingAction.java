package com.winsafe.drp.keyretailer.action;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.keyretailer.dao.AppSBonusSetting;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.hbm.util.DateUtil;

public class ToUpdSBonusSettingAction extends BaseAction {
	private AppSBonusSetting appSBonusSetting = new AppSBonusSetting();
	private AppBaseResource appBr = new AppBaseResource();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		SBonusSetting bnous = appSBonusSetting.getSBonusSettingById(id);
	    Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
	    request.setAttribute("unitName", countUnitMap.get(bnous.getCountUnit()));
		request.setAttribute("bnous", bnous);
		request.setAttribute("id", id);
		request.setAttribute("endDate", getDate(bnous));
		return mapping.findForward("toupd");
	}

	private String getDate(SBonusSetting bnous) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, bnous.getYear());
		calendar.set(Calendar.MONTH, bnous.getMonth() - 1);
		return DateUtil.formatDate(calendar.getTime());
	}

}
