package com.winsafe.erp.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;

public class ToAddBillImportConfigAction extends BaseAction{
	private AppOrgan appOrgan = new AppOrgan();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		Organ organ = appOrgan.getOrganByID_Isrepeal(users.getMakeorganid());
		request.setAttribute("organid", users.getMakeorganid());
		if(organ != null) {
			request.setAttribute("oname", organ.getOrganname());
		}
		return mapping.findForward("success");
	}
		
}
