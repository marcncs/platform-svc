package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppIntegralOrderDetail;
import com.winsafe.drp.dao.IntegralOrder;

public class IntegralOrderDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);

		try {
			AppIntegralOrder aso = new AppIntegralOrder();
			IntegralOrder so = aso.getIntegralOrderByID(id);

			AppIntegralOrderDetail asld = new AppIntegralOrderDetail();
			List sals = asld.getIntegralOrderDetailByIoid(id);
					
			request.setAttribute("als", sals);
			request.setAttribute("sof", so);

			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
