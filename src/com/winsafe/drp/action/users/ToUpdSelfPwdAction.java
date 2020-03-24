package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToUpdSelfPwdAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		try{
			initdata(request); 
			request.setAttribute("userName", users.getLoginname());
			return mapping.findForward("toupdpwd");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}