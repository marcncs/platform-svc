package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;

public class ToAppointsCustomerAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("CID");
		try {

			AppUsers au = new AppUsers();
			List uls = au.getIDAndLoginName();
			ArrayList auls = new ArrayList();
			for (int i = 0; i < uls.size(); i++) {
				UsersBean ub = new UsersBean();
				Object[] o = (Object[]) uls.get(i);
//				ub.setUserid(Long.valueOf(o[0].toString()));
				ub.setLoginname(o[1].toString());
				ub.setRealname(o[2].toString());
				auls.add(ub);
			}

			request.setAttribute("cid", cid);
			request.setAttribute("auls", auls);

			return mapping.findForward("toselect");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
