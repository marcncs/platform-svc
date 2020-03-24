package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.dao.AppWithdrawDetail;
import com.winsafe.drp.dao.Withdraw;

public class WithdrawDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppWithdraw aso = new AppWithdraw();
			
			Withdraw so = aso.getWithdrawByID(id);
			

			AppWithdrawDetail asld = new AppWithdrawDetail();
			List sals = asld.getWithdrawDetailByWID(id);
			
			request.setAttribute("als", sals);
			request.setAttribute("sof", so);

			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
