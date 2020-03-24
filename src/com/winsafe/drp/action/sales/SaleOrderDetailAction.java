package com.winsafe.drp.action.sales;

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
import com.winsafe.drp.dao.SaleOrder;

public class SaleOrderDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			AppSaleOrder aso = new AppSaleOrder();
			AppInvoiceConf aic = new AppInvoiceConf();
			SaleOrder sof = aso.getSaleOrderByID(id);
			if ( sof == null ){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}			
			request.setAttribute("invmsgname", aic.getInvoiceConfById(sof.getInvmsg().intValue()).getIvname());

			AppSaleOrderDetail asld = new AppSaleOrderDetail();
			List sals = asld.getSaleOrderDetailBySoid(id);


					
			request.setAttribute("als", sals);
			request.setAttribute("sof", sof);

			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
