package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;

public class CustomerExpandInitAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			AppUsers au = new AppUsers();
			List us = au.getIDAndLoginName();
			List uls = new ArrayList();
			for (int i = 0; i < us.size(); i++) {
				UsersBean ub = new UsersBean();
				Object[] o = (Object[]) us.get(i);
				ub.setUserid(Integer.valueOf(o[0].toString()));
				ub.setRealname(o[2].toString());
				uls.add(ub);
			}

			request.setAttribute("uls", uls);

			return mapping.findForward("ce");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
