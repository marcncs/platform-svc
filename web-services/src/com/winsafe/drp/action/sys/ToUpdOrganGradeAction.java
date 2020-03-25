package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganGrade;
import com.winsafe.drp.dao.OrganGrade;

public class ToUpdOrganGradeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Integer id = Integer.valueOf(request.getParameter("ID"));

		try {

			AppOrganGrade aw = new AppOrganGrade();
			OrganGrade w = aw.getOrganGradeByID(id);


			request.setAttribute("wf", w);

			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mapping.findForward("toupd");
	}
	
}
