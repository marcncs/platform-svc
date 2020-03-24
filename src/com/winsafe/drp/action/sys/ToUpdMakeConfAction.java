package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.MakeConf;

public class ToUpdMakeConfAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String tablename = request.getParameter("tablename");

		try {

			MakeConf mc = new MakeConf();
			AppMakeConf amc = new AppMakeConf();
			mc = amc.getMakeConfByID(tablename);

			request.setAttribute("mc", mc);

			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return mapping.findForward("toupd");
	}

}
