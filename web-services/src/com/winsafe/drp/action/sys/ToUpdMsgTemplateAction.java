package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMsgTemplate;
import com.winsafe.drp.dao.MsgTemplate;

public class ToUpdMsgTemplateAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int id = Integer.valueOf(request.getParameter("ID"));
		
		try {

			AppMsgTemplate aw = new AppMsgTemplate();
			MsgTemplate mt = aw.getMsgTemplateById(id);
			
			request.setAttribute("mt", mt);

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return mapping.findForward("toupd");
	}

}
