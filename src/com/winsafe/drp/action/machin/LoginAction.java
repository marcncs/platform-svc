package com.winsafe.drp.action.machin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class LoginAction extends Action {
	private Logger logger = Logger.getLogger(LoginAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 登录名
		String username = request.getParameter("username");
		// 密码
		String password = request.getParameter("password");
		
		List<String> emptyList =  new ArrayList<String>();
		emptyList.add("");

		try {
			// 用户名密码验证
			UsersBean usersBean = new UsersBean();
			UsersService usServices = new UsersService();
			usersBean = usServices.CheckUsersNamePassword(username, password); 
			if (usersBean == null) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL,
						Constants.CODE_LOGIN_FAIL_MSG, emptyList);
				return null;
			}
			if (usersBean.getStatus() != 1) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, "账户不可用!", emptyList);
				return null;
			}
			
			AppRole appRole = new AppRole();
			List<String> roleNames =  appRole.getRoleNames(usersBean.getUserid());
			if(roleNames.size() == 0) {
				roleNames.add("");
			}
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, roleNames); 
		} catch (Exception e) {
			logger.error("",e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_LOGIN_FAIL, "登陆失败,网络异常",emptyList);
		}
		return null;
	}
}
