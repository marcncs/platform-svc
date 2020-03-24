package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.hbm.util.Internation;

public class ToAddSaleIndentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {

			String fundsrcselect = Internation.getSelectTagByKeyAll("FundSrc",
					request, "fundsrc", false, null);
			String fundattachselect = Internation.getSelectTagByKeyAllDB(
					"FundAttach", "fundattach", false);
			String transportmodeselect = Internation.getSelectTagByKeyAllDB(
					"TransportMode", "transportmode", false);

			request.setAttribute("fundsrcselect", fundsrcselect);
			request.setAttribute("fundattachselect", fundattachselect);
			request.setAttribute("transportmodeselect", transportmodeselect);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
