package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;

public class ToResetPwdAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		try{
			//20100419-----Richie Yu -----
			Object uid = request.getParameter("uid");
			if(uid == null){
				uid = Integer.valueOf(((UsersBean)request.getSession().getAttribute("users")).getUserid());
			}else{
				uid = Integer.valueOf(request.getParameter("uid"));
			}
			
			AppUsers au=new AppUsers(); 
			String username = au.getUsersByid(Integer.valueOf(uid.toString())).getLoginname();
			
			request.setAttribute("uid",uid);
			request.setAttribute("username",username);
			
			return mapping.findForward("toresetpwd");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
