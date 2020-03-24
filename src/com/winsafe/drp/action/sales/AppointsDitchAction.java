package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDitch;

public class AppointsDitchAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long id = Long.valueOf(request.getParameter("id"));

		// String[] selectusers = request.getParameterValues("selectusers");
		String strspeed = request.getParameter("speedstr");
		//int count = Integer.parseInt(request.getParameter("uscount"));

		try {
			// AppCustomerUsers acu = new AppCustomerUsers();
			AppDitch ad = new AppDitch();
			 ad.appointsDitch(strspeed, id);// 分配

			request.setAttribute("result", "databases.refer.success");

			return mapping.findForward("appoints");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
