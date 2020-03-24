package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.MemberGrade;
import com.winsafe.drp.dao.MemberGradeForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdMemberGradeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Integer id = Integer.valueOf(request.getParameter("ID"));

		try {

			MemberGrade w = new MemberGrade();
			AppMemberGrade aw = new AppMemberGrade();
			w = aw.getMemberGradeByID(id);

			MemberGradeForm wf = new MemberGradeForm();
			wf.setId(w.getId());
			wf.setGradename(w.getGradename());
			wf.setPolicyid(w.getPolicyid());
			wf.setPolicyidname(Internation.getSelectTagByKeyAllDBDef(
					"PricePolicy", "policyid", w.getPolicyid()));
			wf.setIntegralrate(w.getIntegralrate());

			request.setAttribute("wf", wf);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return mapping.findForward("updDept");
	}

}
