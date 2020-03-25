package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMsg;
import com.winsafe.drp.dao.Msg;

public class ToUpdMsgAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int id = Integer.valueOf(request.getParameter("ID"));
		try{
			AppMsg asb = new AppMsg();
			Msg msg = asb.getMsgById(id);
			if ( msg.getIsaudit() == 1 ){
				request.setAttribute("result", "databases.record.isapprovenooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			request.setAttribute("msg", msg);
		}catch ( Exception e){
			e.printStackTrace();
		}

		return mapping.findForward("toupd");
		
	}

}
