package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppHap;
import com.winsafe.drp.dao.Hap;

public class ToUpdHapAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("id"));
		AppHap ah = new AppHap();
		try {
			Hap h = ah.getHapByID(id);
			request.setAttribute("hf", h);
			return mapping.findForward("edit");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
