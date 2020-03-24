package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIntegralRule;
import com.winsafe.drp.dao.IntegralRule;
import com.winsafe.drp.dao.IntegralRuleForm;

public class ToUpdIntegralRuleAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Integer id = Integer.valueOf(request.getParameter("ID"));

		try {

			AppIntegralRule aw = new AppIntegralRule();
			IntegralRule w = aw.getIntegralRuleByID(id);

			IntegralRuleForm wf = new IntegralRuleForm();
			wf.setId(w.getId());
			wf.setRmode(w.getRmode());
			wf.setRkey(w.getRkey());
			wf.setIrate(w.getIrate());

			request.setAttribute("wf", wf);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return mapping.findForward("updDept");
	}

}
