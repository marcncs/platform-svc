package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCodeRule;
import com.winsafe.drp.dao.AppCodeUnit;
import com.winsafe.drp.dao.CodeUnit;

public class ToUpdCodeRuleAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String ucode = request.getParameter("ucode");

		try {

			AppCodeUnit appcu = new AppCodeUnit();
			AppCodeRule aw = new AppCodeRule();
			List culist = aw.getCodeRuleByUcode(ucode);
			
			CodeUnit cu = appcu.getCodeUnitByID(ucode);
			

			request.setAttribute("cunit", cu);
			request.setAttribute("culist", culist);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return mapping.findForward("toupd");
	}

}
