package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Region;
import com.winsafe.drp.dao.Users;

public class ToRelateUserAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String username = null;

		AppUsers appusers = new AppUsers();

		try {
			AppRegion ar = new AppRegion();
			String code = request.getParameter("acode");
			Region ps = ar.getRegionById(code);

			String uid = ps.getUserid();
			if (uid != null && uid.length() > 0) {
				Users u = appusers.getUsersByid(Integer.valueOf(uid));
				if (u != null) {
					username = u.getRealname();
				}
			}
			request.setAttribute("username", username);
			request.setAttribute("ps", ps);
			
			

			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
