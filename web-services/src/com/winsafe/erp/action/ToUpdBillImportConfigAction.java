package com.winsafe.erp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.erp.dao.AppBillImportConfig;
import com.winsafe.erp.pojo.BillImportConfig;

public class ToUpdBillImportConfigAction extends BaseAction {

	private AppBillImportConfig abic = new AppBillImportConfig();
	private AppOrgan ao = new AppOrgan();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		Integer bicid = Integer.parseInt(request.getParameter("bicid"));
		BillImportConfig bic = abic.getBillImportConfigByID(bicid);
		request.setAttribute("templateno", bic.getTemplateNo());
		
		Organ organ = ao.getOrganByID(bic.getOrganId());
		if(organ != null) {
			request.setAttribute("organid", organ.getId());
			request.setAttribute("oname", organ.getOrganname());
		}
		List<BillImportConfig> bics = abic.getAllBillImportConfig(bic.getOrganId(), bic.getTemplateNo());
		
		request.setAttribute("bics", bics);

		return mapping.findForward("success");
	}

}
