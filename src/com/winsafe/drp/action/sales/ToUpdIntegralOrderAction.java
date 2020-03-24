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
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppIntegralOrderDetail;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppObjIntegral;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.dao.InvoiceConf;

public class ToUpdIntegralOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppIntegralOrder aso = new AppIntegralOrder();

			IntegralOrder so = aso.getIntegralOrderByID(id);
			if (so.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return mapping.findForward("lock");
			}

			if (so.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.lock");
				return mapping.findForward("lock");
			}


			AppIntegralOrderDetail asld = new AppIntegralOrderDetail();
			List slls = asld.getIntegralOrderDetailByIoid(id);


			AppInvoiceConf aic = new AppInvoiceConf();
			List uls = aic.getAllInvoiceConf();
			ArrayList icls = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				InvoiceConf ic = (InvoiceConf) uls.get(u);

				icls.add(ic);
			}


			AppObjIntegral aoi = new AppObjIntegral();
			double integral = aoi.getBalance(so.getCid(), 1);
			
			AppCustomer ac = new AppCustomer();
			int rate = ac.getCustomer(so.getCid()).getRate();

			request.setAttribute("sof", so);
			request.setAttribute("als", slls);
			request.setAttribute("icls", icls);
			request.setAttribute("integral", integral);
			request.setAttribute("rate", rate);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
