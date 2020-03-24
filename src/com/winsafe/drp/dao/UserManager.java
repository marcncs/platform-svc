package com.winsafe.drp.dao;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;

public class UserManager extends ActionForm {

	private static Logger logger = Logger.getLogger(UserManager.class);
	public final static String userBeanNameOfSession = "user";

	/*
	 * public static boolean checkSessionUserIsLogin(HttpServletRequest request)
	 * throws Exception { if(request == null) { logger.error("method
	 * checkSessionUserIsLogin(HttpServletRequest request) of class
	 * UserManager's parameter request is null"); // throw new
	 * EpsException("method checkSessionUserIsLogin(HttpServletRequest request)
	 * of class UserManager's parameter request is null"); } HttpSession session =
	 * request.getSession(); UserBean userBean =
	 * (UserBean)session.getAttribute(userBeanNameOfSession); if(userBean ==
	 * null) { logger.info("not Login"); return false; }
	 * 
	 * logger.info(userBean.getUserId() + " have been Login"); return true; }
	 */
	public static UsersBean getUser(HttpServletRequest request)
			throws Exception {
		UsersBean usersBean = (UsersBean) request.getSession().getAttribute(
				"users");
		if (usersBean == null) {
			return null;
		}
		return usersBean;
	}

	public static boolean isPermit(String tmpPath, int userid) {
		boolean ispermit = false;

		String hql="select r.id from RoleMenu r, Menu m, UserRole u where r.operateid = m.operateid and r.roleid=u.roleid "+
		 " and u.userid="+userid+"  and m.url='"+ tmpPath+"' and u.ispopedom=1";
		
		Object aa = new Object();
		try {
			aa = (Object) EntityManager.find(hql);
			if (aa != null) {
				ispermit = true;
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}

		return ispermit;
	}
	
	public static UsersBean getBeanFromUsers(Users users){
		UsersBean usersBean=new UsersBean();
		usersBean.setUserid(users.getUserid());
		usersBean.setLoginname(users.getLoginname());
		usersBean.setMakedeptid(users.getMakedeptid());
		usersBean.setMakeorganid(users.getMakeorganid());
	//	usersBean.setMakeorganname(users.getMakeorganname());
	//	usersBean.setMultipartRequestHandler(users.getMultipartRequestHandler());
	//	usersBean.setOrgansysid(users.getOrgansysid());
		usersBean.setRealname(users.getRealname());
	//	usersBean.setParentorganid(users.getParentorganid());
	//	usersBean.setVisitorgan(users.getVisitorgan());
		usersBean.setIscall(users.getIscall());
		return usersBean;
	}

}
