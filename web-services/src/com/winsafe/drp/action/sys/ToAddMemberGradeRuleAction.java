package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMemberGrade;

public class ToAddMemberGradeRuleAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			AppMemberGrade aw = new AppMemberGrade();
			List mglist = aw.getAllMemberGrade();

			request.setAttribute("mglist", mglist);

			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mapping.findForward("toadd");
	}

}
