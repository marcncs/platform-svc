package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.PurchaseIncome;

public class ToUpdPurchaseIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);try{
			AppPurchaseIncome api = new AppPurchaseIncome();
			PurchaseIncome pi = api.getPurchaseIncomeByID(id);

			if (pi.getIsaudit() == 1) { 
				request.setAttribute("result", "databases.record.approvestatus");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}


			AppPurchaseIncomeDetail apid = new AppPurchaseIncomeDetail();
			List apils = apid.getPurchaseIncomeDetailByPbId2(id);



			

			request.setAttribute("pif", pi);
			request.setAttribute("als", apils);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
