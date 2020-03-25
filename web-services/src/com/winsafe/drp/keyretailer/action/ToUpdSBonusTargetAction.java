package com.winsafe.drp.keyretailer.action;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.keyretailer.dao.AppSBonusSetting;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.keyretailer.pojo.SBonusTarget;
import com.winsafe.hbm.util.DateUtil;

public class ToUpdSBonusTargetAction extends BaseAction {
	private AppSBonusTarget app = new AppSBonusTarget();
	private AppBaseResource appBr = new AppBaseResource();
	private AppOrgan ao = new AppOrgan();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		SBonusTarget sbs= app.getSBonusTargetById(id);
		Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
		Organ o = null;
		o = ao.getOrganByID(sbs.getFromOrganId());
		String fromorganname = o==null?"":o.getOrganname();
		o = ao.getOrganByID(sbs.getToOrganId());
		String toorganname = o==null?"":o.getOrganname();
		sbs.setFromOrganName(fromorganname);
		sbs.setToOrganName(toorganname);
		sbs.setCountUnitName(countUnitMap.get(sbs.getCountUnit()));
		
		request.setAttribute("sbs", sbs);
		return mapping.findForward("toupd");
	}

	private String getDate(SBonusSetting bnous) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, bnous.getYear());
		calendar.set(Calendar.MONTH, bnous.getMonth() - 1);
		return DateUtil.formatDate(calendar.getTime());
	}

}
