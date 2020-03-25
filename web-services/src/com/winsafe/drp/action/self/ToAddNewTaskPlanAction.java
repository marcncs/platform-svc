package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.hbm.util.Internation;

public class ToAddNewTaskPlanAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String status = Internation.getSelectTagByKeyAll("TaskPlanStatus",
				request, "Status", false, null);
		String sort = Internation.getSelectTagByKeyAll("TaskOrPlan", request,
				"Sort", false, null);
		try {

			request.setAttribute("status", status);
			request.setAttribute("sort", sort);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());

	}
}
