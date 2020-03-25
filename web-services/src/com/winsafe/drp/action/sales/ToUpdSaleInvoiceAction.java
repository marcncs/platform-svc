package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppSaleInvoice;
import com.winsafe.drp.dao.AppSaleInvoiceDetail;
import com.winsafe.drp.dao.SaleInvoice;
import com.winsafe.drp.dao.SaleInvoiceDetail;
import com.winsafe.drp.dao.SaleInvoiceDetailForm;
import com.winsafe.drp.dao.SaleInvoiceForm;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.RequestTool;

public class ToUpdSaleInvoiceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = RequestTool.getInt(request,"ID");
		super.initdata(request);

		try {

			AppSaleInvoice asi = new AppSaleInvoice();
			AppCustomer ac = new AppCustomer();
			SaleInvoice si = asi.getSaleInvoiceByID(id);

			if (si.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			SaleInvoiceForm sif = new SaleInvoiceForm();
			sif.setId(si.getId());
			sif.setCid(si.getCid());
			sif.setCname(ac.getCustomer(si.getCid()).getCname());
			sif.setInvoicecode(si.getInvoicecode());
			sif.setInvoicecontent(si.getInvoicecontent());
			sif.setInvoicetypename(Internation.getSelectTagByKeyAll(
					"InvoiceType", request, "invoicetype", String.valueOf(si
							.getInvoicetype()), null));
			sif.setMakeinvoicedate(String.valueOf(si.getMakeinvoicedate())
					.substring(0, 10));
			sif.setInvoicedate(String.valueOf(si.getInvoicedate()).substring(0,
					10));
			sif.setInvoicesum(si.getInvoicesum());
			sif.setMemo(si.getMemo());
			sif.setInvoicetitle(si.getInvoicetitle());
			sif.setSendaddr(si.getSendaddr());

			AppSaleInvoiceDetail asld = new AppSaleInvoiceDetail();
			List slls = asld.getSaleInvoiceDetailObjectBySIID(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < slls.size(); i++) {
				SaleInvoiceDetailForm sidf = new SaleInvoiceDetailForm();
				SaleInvoiceDetail o = (SaleInvoiceDetail) slls.get(i);
				sidf.setId(o.getId());
				sidf.setSiid(o.getSiid());
				sidf.setSoid(o.getSoid());
				sidf.setSubsum(o.getSubsum());
				sidf.setMakedate(String.valueOf(o.getMakedate()).substring(0,
						10));
				als.add(sidf);
			}

			request.setAttribute("sif", sif);
			request.setAttribute("als", als);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
