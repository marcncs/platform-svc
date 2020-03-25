package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseTrades;
import com.winsafe.drp.dao.AppPurchaseTradesDetail;
import com.winsafe.drp.dao.PurchaseTrades;

public class PurchaseTradesDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			AppPurchaseTrades aso = new AppPurchaseTrades();
			PurchaseTrades so = aso.getPurchaseTradesByID(id);

			

			AppPurchaseTradesDetail asld = new AppPurchaseTradesDetail();
			List sals = asld.getPurchaseTradesDetailByPtid(id);


			request.setAttribute("als", sals);
			request.setAttribute("sof", so);
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
