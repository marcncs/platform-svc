package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseInvoice;
import com.winsafe.drp.dao.AppPurchaseInvoiceDetail;
import com.winsafe.drp.dao.PurchaseInvoice;
import com.winsafe.drp.dao.PurchaseInvoiceDetail;
import com.winsafe.drp.dao.PurchaseInvoiceDetailForm;
import com.winsafe.drp.dao.PurchaseInvoiceForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdPurchaseInvoiceAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("ID"));

		try {
			AppPurchaseInvoice api = new AppPurchaseInvoice();
			AppProvider apv = new AppProvider();

			PurchaseInvoice pi = api.getPurchaseInvoiceByID(id);

			if (pi.getIsaudit() == 1) { 
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}

			PurchaseInvoiceForm pif = new PurchaseInvoiceForm();
			pif.setId(pi.getId());
			pif.setInvoicecode(pi.getInvoicecode());
			pif.setInvoicetypename(Internation.getSelectTagByKeyAll(
					"InvoiceType", request, "invoicetype", pi.getInvoicetype()
							.toString(), null));
			pif.setMakeinvoicedate(pi.getMakeinvoicedate());
			pif.setInvoicedate(pi.getInvoicedate());
			pif.setProvideid(pi.getProvideid());
			pif.setProvideidname(apv.getProviderByID(pi.getProvideid())
					.getPname());
			pif.setMemo(pi.getMemo());
			pif.setInvoicesum(pi.getInvoicesum());

			AppPurchaseInvoiceDetail apid = new AppPurchaseInvoiceDetail();
			List apils = apid.getPurchaseInvoiceDetailByPbId(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < apils.size(); i++) {
				PurchaseInvoiceDetailForm pidf = new PurchaseInvoiceDetailForm();
				PurchaseInvoiceDetail o = (PurchaseInvoiceDetail) apils.get(i);
				pidf.setId(o.getId());
				pidf.setPiid(o.getPiid());
				pidf.setPoid(o.getPoid());
				pidf.setSubsum(o.getSubsum());
				pidf.setMakedate(o.getMakedate().toString().substring(0,10));
				als.add(pidf);
			}

			request.setAttribute("pif", pif);
			request.setAttribute("als", als);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
