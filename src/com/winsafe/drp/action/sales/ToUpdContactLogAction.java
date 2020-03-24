package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppContactLog;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.ContactLog;

public class ToUpdContactLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("id"));
		AppContactLog appContactLog = new AppContactLog();
		ContactLog cl = new ContactLog();
		try {
			cl = appContactLog.getContactLog(id);
			AppBaseResource abr = new AppBaseResource();
			List<BaseResource> uls = abr.getBaseResource("SelectContact");
			request.setAttribute("als", uls);
			request.setAttribute("clf", cl);
			return mapping.findForward("edit");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return mapping.getInputForward();
	}

}
