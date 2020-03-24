package com.winsafe.drp.action.sys;
 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping; 
 
import com.winsafe.drp.dao.AppUsers; 
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean; 
import com.winsafe.drp.util.CheckPsdStrong;
import com.winsafe.drp.util.Constants; 
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt; 

public class CopyOfUserLoginAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		// 用户业务处理模块初始化
		AppUsers appUsers = new AppUsers();
		// 取得用户输入的登录名和密码
		String username = request.getParameter("UserName");
		if(!"".equals(username)){
			request.setAttribute("username", username);
		}
		// 得到验证码
		String checknum = request.getParameter("CheckNum");
		String chknum;
		
		// 当前session的验证码
		chknum = request.getSession().getAttribute(Constants.CHECKIMG_NUMBER)
				.toString();
		// 验证验证码是否正确
		if (!chknum.equals(checknum)) {
			request.setAttribute("result", "对不起，验证码错误！"); 
			return new ActionForward(mapping.getInput()); 
		}
 
		// 查询当前用户的使用期限是否到期
		Users curUser = appUsers.getUsers(username);
		// 如果到期提示错误
		if (curUser != null
				&& curUser.getValidate() != null
				 ) {
			request.setAttribute("result", "对不起，您的有效期已经失效！");
			return new ActionForward(mapping.getInput());
		}
		// 得到页面用户输入的密码
		String password = request.getParameter("Password");
		// 得到加密后的密码
		String securityPassword = Encrypt.getSecret(password, 1);
		// 得到用户名和密码符合的用户
		UsersBean usersBean = appUsers.CheckUsersNamePassword(username,
				securityPassword);
		// 用户名密码有错误的情况,提示错误信息
		if (usersBean == null) {
			request.setAttribute("result", "对不起，您的账号或密码错误！");
			return new ActionForward(mapping.getInput());
			// 密码正确时
		} else {
			// 判断当前用户的密码是否过于简单
			Integer strongLevel = CheckPsdStrong.getPsdStrongLevel(password);
			// 密码强度不够时,提示需要改密码(数字英文混合+大于6位)
			if(strongLevel < 3){
				request.setAttribute("psdWarn", false);
				// 设置用户在线
				appUsers.setOnline(usersBean.getUserid());
				request.getSession().setAttribute("users", usersBean);
				return new ActionForward(mapping.getInput());
			}
			request.setAttribute("psdWarn", true);
			ActionForward actionForward = new ActionForward();
			actionForward.setPath("initMainPageAction.do");
			actionForward.setRedirect(true);
			return actionForward;
		}
	}

	/*
	 * public ActionForward execute(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { ActionErrors errors = new ActionErrors(); try{
	 * 
	 * AppUsers au = new AppUsers(); UsersService us = new UsersService();
	 * OrganService ao = new OrganService(); String username =
	 * request.getParameter("UserName"); String password =
	 * request.getParameter("Password"); password = Encrypt.getSecret(password,
	 * 1); String checknum = request.getParameter("CheckNum");
	 * 
	 * String errormsg; String chknum; chknum =
	 * request.getSession().getAttribute(Constants.CHECKIMG_NUMBER) .toString();
	 * if (!chknum.equals(checknum)) { ActionMessage error = new
	 * ActionMessage("userlogin.error.checknum");
	 * errors.add(ActionMessages.GLOBAL_MESSAGE, error);
	 * request.setAttribute("result", "对不起，验证码错误！"); return new
	 * ActionForward(mapping.getInput()); }
	 * 
	 * Users curUser = us.getUsers(username) ; if ( curUser!= null &&
	 * curUser.getValidate()!=null && curUser.getValidate().getTime()<DateUtil.getCurrentDate().getTime() ){
	 * request.setAttribute("result", "对不起，您的有效期已经失效！"); return new
	 * ActionForward(mapping.getInput()); }
	 * 
	 * UsersBean usersBean = au.CheckUsersNamePassword(username, password);
	 * 
	 * if (usersBean == null) { ActionMessage error = new
	 * ActionMessage("userlogin.error.namepassword");
	 * errors.add(ActionMessages.GLOBAL_MESSAGE, error);
	 * request.setAttribute("result", "对不起，您的用户名或者密码错误！"); return new
	 * ActionForward(mapping.getInput()); }
	 * 
	 * 
	 * 
	 * if (errors.size() == 0) { au.setOnline(usersBean.getUserid());
	 * request.getSession().setAttribute("users", usersBean); // AppUserLeftMenu
	 * aulm = new AppUserLeftMenu(); // List mls =
	 * aulm.getUserLeftMenuByUserid(usersBean.getUserid()); AppRole approle =
	 * new AppRole(); List mls =
	 * approle.getLeftMenuByUserid(usersBean.getUserid());
	 * request.setAttribute("mls", mls); request.setAttribute("realname",
	 * usersBean.getRealname()); request.setAttribute("makeorganidname",
	 * ao.getOrganName( usersBean.getMakeorganid())); AppOrganAwake appoa = new
	 * AppOrganAwake(); request.setAttribute("isOrganAwake",
	 * appoa.getOrganAwakeByOidUserid( usersBean.getMakeorganid(),
	 * usersBean.getUserid())); UsersBean users = UserManager.getUser(request);
	 * int userid = users.getUserid(); DBUserLog.addUserLog(userid, 11,
	 * "用户成功登录.登录IP=" + request.getRemoteAddr()); if (usersBean.getIscall() ==
	 * 1) { request.setAttribute("callcenterip",
	 * Internation.getStringByKeyPositionDB("CallCenter", 0)); return
	 * mapping.findForward("callcent"); } else { return
	 * mapping.findForward("loginsuccess"); }
	 *  } saveErrors(request, errors); }catch( Exception e){
	 * e.printStackTrace(); }
	 * 
	 * return new ActionForward(mapping.getInput()); }
	 */

}
