package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward; 
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.IdentityCode;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.PasswordUtil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt; 
import com.winsafe.hbm.util.StringUtil;

public class InitSelfPwdAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	    
		try{
		    //验证码
		    String userName = request.getParameter("userName");
		    String idCode=request.getParameter("idCode");
			String password =request.getParameter("password");
			String pwd = Encrypt.getSecret(password, 1);
			AppUsers appUsers = new AppUsers();
			//检查验证码
			IdentityCode ic = (IdentityCode)request.getSession().getAttribute("identifyCode");
			if(ic == null) {
				ResponseUtil.writeJsonMsg(response, "-1", "验证码不正确");
				return null;
			}
			if(ic.isExpired(Dateutil.getCurrentDate())) {
				ResponseUtil.writeJsonMsg(response, "-1", "验证码已过期");
				return null;
			}
			if(!ic.getIdentifyCode().equals(idCode)) {
				ResponseUtil.writeJsonMsg(response, "-1", "验证码不正确");
				return null;
			}
			
			Users users = appUsers.getUsersByLoginname(userName);
			if(users == null) {
				ResponseUtil.writeJsonMsg(response, "-1", "用户名不存在");
				return null;
			}
			if(!ic.getMobile().equals(users.getMobile())) {
				ResponseUtil.writeJsonMsg(response, "-1", "验证码不正确");
				return null;
			}
			//校验密码
			String msg = PasswordUtil.checkPwdForUpdate(password, userName, users.getUserid());
			if(!StringUtil.isEmpty(msg)) {
				ResponseUtil.writeJsonMsg(response, "-1", msg);
				return null;
			}
			//更新密码
			appUsers.resetUserPWDByLoginName(users.getLoginname(),pwd,Constants.PWD_VAL_DATE);
						
			ResponseUtil.writeJsonMsg(response, "1", "密码设置成功");
		    DBUserLog.addUserLog(users.getUserid(), 11, "初始化密码");
			return null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
