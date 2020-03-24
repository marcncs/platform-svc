package com.winsafe.drp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class ExitAction extends Action
{
        public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
        {
        	
        	UsersBean users = UserManager.getUser(request);
            int userid = users.getUserid();
            AppUsers au = new AppUsers();
            au.setOffline(userid);           
            request.removeAttribute("users");           
            request.getSession().invalidate();
  
            DBUserLog.addUserLog(users.getUserid(), "系统管理", "用户退出系统");//日志 
            ActionForward forward = new ActionForward("/sys/index.jsp");
            forward.setRedirect(true);
            return forward;
        }
}
