package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.PasswordUtil;
import com.winsafe.hbm.util.Encrypt; 
import com.winsafe.hbm.util.StringUtil;

public class UpdSelfPwdAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	    
		try{
			UsersBean users = UserManager.getUser(request);
		    Integer userid = users.getUserid();
		    String oldpassword=request.getParameter("oldpassword");
			String password =request.getParameter("password");
			String oldpwd=Encrypt.getSecret(oldpassword,1);
			String pwd = Encrypt.getSecret(password, 1);
			AppUsers au = new AppUsers();
			
			request.setAttribute("forward", "../users/toUpdSelfPwdAction.do");
			if(!au.CheckUsersPasswordByUserID(userid, oldpwd)){
				request.setAttribute("result", "databases.upd.fail");
				return mapping.findForward("updpwd");
			}
			
			String msg = PasswordUtil.checkPwdForUpdate(password, users.getLoginname(), userid);
			if(!StringUtil.isEmpty(msg)) {
				request.setAttribute("result", msg);
				return mapping.findForward("updpwd2");
			}
			au.resetUserPWD(userid,pwd,Constants.PWD_VAL_DATE);
			
			if("1".equals(request.getParameter("isForce"))) {
				request.setAttribute("forward", "../sys/index.jsp");
			}
		     
		      request.setAttribute("result", "databases.upd.success");

		      DBUserLog.addUserLog(userid, 11, "修改密码");
			return mapping.findForward("updpwd");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
