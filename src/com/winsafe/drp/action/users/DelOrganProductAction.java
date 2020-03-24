package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganProduct;

public class DelOrganProductAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	try {
		String[] opid = request.getParameterValues("opid");
		AppOrganProduct aop = new AppOrganProduct();
		StringBuffer opids = new StringBuffer();
		for (int i = 0; i < opid.length; i++) {
			opids.append(","+opid[i]);
		}
		aop.delOrganProduct(opids.substring(1));
		
		request.setAttribute("result", "databases.del.success");
		request.setAttribute("forward", "../users/listOrganProductAlreadyAction.do");
		return mapping.findForward("del");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
}
}
