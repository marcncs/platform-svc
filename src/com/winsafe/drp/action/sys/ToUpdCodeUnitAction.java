package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCodeUnit;
import com.winsafe.drp.dao.CodeUnit;

public class ToUpdCodeUnitAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ucode");

		try {


			AppCodeUnit aw = new AppCodeUnit();
			CodeUnit cu= aw.getCodeUnitByID(id);


			request.setAttribute("cu", cu);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return mapping.findForward("toupd");
	}

}
