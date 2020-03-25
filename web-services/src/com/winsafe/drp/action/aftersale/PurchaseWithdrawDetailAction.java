package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.dao.PurchaseWithdraw;

public class PurchaseWithdrawDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			AppPurchaseWithdraw aso = new AppPurchaseWithdraw();
			PurchaseWithdraw so = aso.getPurchaseWithdrawByID(id);


			AppPurchaseWithdrawDetail asld = new AppPurchaseWithdrawDetail();
			List sals = asld.getPurchaseWithdrawDetailByPWID(id);
			
			request.setAttribute("als", sals);
			request.setAttribute("sof", so);
			
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
