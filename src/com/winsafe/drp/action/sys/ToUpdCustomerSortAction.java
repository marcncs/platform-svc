package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomerSort;
import com.winsafe.drp.dao.CustomerSort;

public class ToUpdCustomerSortAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Long id = Long.valueOf(request.getParameter("ID"));

		try {

			AppCustomerSort aw = new AppCustomerSort();
			CustomerSort cs = aw.getCustomerSortById(id);

			request.setAttribute("id", cs.getId());
			request.setAttribute("sortname", cs.getSortname());

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			//HibernateUtil.closeSession();
		}
		return mapping.findForward("updDept");
	}

}
