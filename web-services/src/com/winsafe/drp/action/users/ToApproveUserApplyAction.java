package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers; 
import com.winsafe.drp.dao.UserApply;
import com.winsafe.drp.server.CountryAreaService;

public class ToApproveUserApplyAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			AppUsers appUsers = new AppUsers();
			CountryAreaService cas = new CountryAreaService();
			String id = request.getParameter("ID");
			UserApply ua = appUsers.getUserApplyById(Integer.valueOf(id));
			ua.setProvinceName(cas.getCountryAreaName(ua.getProvince()));
			ua.setCityName(cas.getCountryAreaName(ua.getCity()));
			ua.setAreasName(cas.getCountryAreaName(ua.getAreas()));
			
			request.setAttribute("ua",ua);
			
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
