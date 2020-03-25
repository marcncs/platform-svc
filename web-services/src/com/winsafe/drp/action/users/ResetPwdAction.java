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

public class ResetPwdAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		try{
			Integer uid = Integer.valueOf(request.getParameter("uid"));
			String username = request.getParameter("username");
			String password =request.getParameter("password");
			String pwd = Encrypt.getSecret(password, 1);
			
			String msg = PasswordUtil.checkPwdForUpdate(password, username, uid);
			if(!StringUtil.isEmpty(msg)) {
				request.setAttribute("result", msg);
				return new ActionForward("/sys/operatorreturn3.jsp");
			}
			AppUsers au = new AppUsers();
			au.resetUserPWD(uid,pwd,Constants.PWD_VAL_DATE);
			
			
		      request.setAttribute("result", "databases.upd.success");
		      UsersBean users = UserManager.getUser(request);
		      Integer userid = users.getUserid();
		      DBUserLog.addUserLog(userid,"系统管理","用户管理>>重设密码,用户编号:"+uid);
			
			return mapping.findForward("resetpwd");
		}catch(Exception e){
			e.printStackTrace();
		}finally {
		      //
		      //  ConnectionEntityManager.close(conn);
		}
		return new ActionForward(mapping.getInput());
	}

}
