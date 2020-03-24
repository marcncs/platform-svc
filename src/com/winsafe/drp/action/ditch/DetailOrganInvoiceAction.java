package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganInvoice;
import com.winsafe.drp.dao.AppOrganInvoiceDetail;
import com.winsafe.drp.dao.OrganInvoice;
import com.winsafe.drp.dao.OrganInvoiceDetail;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:52:06 www.winsafe.cn
 */
public class DetailOrganInvoiceAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganInvoiceDetail appAMAD = new AppOrganInvoiceDetail();
			List<OrganInvoiceDetail> listAmad = appAMAD
					.getOrganInvoiceDetailByPIID(id);
			AppOrganInvoice appAMA = new AppOrganInvoice();
			OrganInvoice ama = appAMA.getOrganInvoiceByID(Integer.valueOf(id));

			request.setAttribute("list", listAmad);
			request.setAttribute("ama", ama);

			return mapping.findForward("detail");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return super.execute(mapping, form, request, response);
	}
}
