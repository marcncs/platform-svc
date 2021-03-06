package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class SaleTotalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		try {

			AppOrgan ao = new AppOrgan();
			List alos = ao.getOrganToDown(users.getMakeorganid());

			request.setAttribute("alos", alos);
			
			return mapping.findForward("show");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
