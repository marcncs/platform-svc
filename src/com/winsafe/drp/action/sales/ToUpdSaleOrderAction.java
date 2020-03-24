package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.hbm.util.DateUtil;

public class ToUpdSaleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppSaleOrder aso = new AppSaleOrder();

			SaleOrder so = aso.getSaleOrderByID(id);
			if (so.getIsblankout() == 1) {
				request.setAttribute("result",
						"databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			if (so.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.lock");
				return new ActionForward("/sys/lockrecord.jsp");
			}			

			AppSaleOrderDetail asld = new AppSaleOrderDetail();
			List slls = asld.getSaleOrderDetailBySoid(id);

			AppInvoiceConf aic = new AppInvoiceConf();
			List uls = aic.getAllInvoiceConf();
			ArrayList icls = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				InvoiceConf ic = (InvoiceConf) uls.get(u);

				icls.add(ic);
			}

			request.setAttribute("consignmentdate", DateUtil.formatDate(so.getConsignmentdate()));
			request.setAttribute("consignmenttime", DateUtil.formatTime(so.getConsignmentdate()));
			request.setAttribute("sof", so);
			request.setAttribute("als", slls);
			request.setAttribute("icls", icls);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
