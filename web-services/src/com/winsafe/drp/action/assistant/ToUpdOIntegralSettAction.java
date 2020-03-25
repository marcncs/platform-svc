package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOIntegralSett;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.OIntegralSett;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToUpdOIntegralSettAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		UsersBean users = UserManager.getUser(request);
		try {
			AppOIntegralSett appr = new AppOIntegralSett();
			OIntegralSett r = appr.getOIntegralSettByID(id);

			if (r.getIsaudit() == 1) {
				String result = "databases.record.isapprovenooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppOrgan ao = new AppOrgan();
			List ols = ao.getOrganToDown(users.getMakeorganid());
			request.setAttribute("ols", ols);

			request.setAttribute("r", r);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("toupd");
	}

}
