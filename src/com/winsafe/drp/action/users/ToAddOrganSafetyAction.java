package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToAddOrganSafetyAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			super.initdata(request);
			return mapping.findForward("toadd");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//HibernateUtil.closeSession();
		}
		return mapping.getInputForward();
	}

}
