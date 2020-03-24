package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.CashBank;
import com.winsafe.hbm.util.RequestTool;

public class ToUpdCashBankAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);super.initdata(request);try{
			Integer id = RequestTool.getInt(request, "ID");
			AppCashBank apa = new AppCashBank();
			CashBank cb = apa.getCashBankById(id);

			request.setAttribute("cb", cb);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
