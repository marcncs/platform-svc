package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.AppMemberGradeRule;
import com.winsafe.drp.dao.MemberGradeRule;

public class ToUpdMemberGradeRuleAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Integer id = Integer.valueOf(request.getParameter("ID"));

		try {

			AppMemberGradeRule aw = new AppMemberGradeRule();
			MemberGradeRule mgr = aw.getMemberGradeRuleByID(id);
			
			AppMemberGrade appmg = new AppMemberGrade();
			List mglist = appmg.getAllMemberGrade();

			request.setAttribute("mglist", mglist);
			request.setAttribute("mgr", mgr);

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return mapping.findForward("toupd");
	}

}
