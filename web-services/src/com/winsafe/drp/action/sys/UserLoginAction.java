package com.winsafe.drp.action.sys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties; 
import java.util.Set; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.service.UserService;
import com.winsafe.drp.metadata.UserCategary;
import com.winsafe.drp.util.CheckPsdStrong;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil; 
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.webservice.client.ldap.LDAPServices;

public class UserLoginAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(UserLoginAction.class);
	private AppUsers appUsers = new AppUsers();

	@Override 
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//testOracle();
		initdataNoUser(request);
		try {
			AppOrgan appOrgan = new AppOrgan();
			// 取得用户输入的登录名和密码
			String username = request.getParameter("UserName");
			String password = request.getParameter("Password");
			
			if(!"".equals(username)){
				request.setAttribute("username", username);
			}
			//获取登陆方式
			Properties systemPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			// 检查验证码
			String failMessage = checkNum(request, systemPro);
			if(!StringUtil.isEmpty(failMessage)){
				request.setAttribute("result", failMessage); 
				return mapping.findForward("checkfail");
			}
			Users users = appUsers.getUsersByLoginname(username);
			if (users == null) {
				request.setAttribute("result", "对不起，您的账号或密码错误！");
				return mapping.findForward("checkfail");
				// 密码正确时
			} 
			//检查账号是否已被锁定
			if(users.getIsLocked() != null && users.getIsLocked() == 1) {
				request.setAttribute("result", "对不起，您的账号已被锁定,如需解锁账号,请联系管理员");
				return mapping.findForward("checkfail");
			}
			
			//若是外部用户初次登录
			if(StringUtil.isEmpty(users.getPassword())
					&& (users.getIsCwid()==null
					|| users.getIsCwid()!=1)) {
				request.setAttribute("msg", "请设置您的登录密码");
				request.setAttribute("userName", users.getLoginname());
				return new ActionForward("/users/initselfpwd.jsp");
			}
			
			//获取用户
			UsersBean usersBean = getUsersBean(users, password, systemPro);
			
			// 用户名密码有错误的情况,提示错误信息
			if (usersBean == null) {
				request.setAttribute("result", "对不起，您的账号或密码错误！");
				//检查尝试登陆的次数,若次数达到了7,则锁定账户
				if("1".equals(systemPro.getProperty("loginFailCountCheck"))) {
					if(users.getTryCount() == null) {
						users.setTryCount(1);
					} else {
						users.setTryCount(users.getTryCount()+1);
						if(users.getTryCount() >= 7) {
							users.setIsLocked(1);
							request.setAttribute("result", "对不起，您的账号已被锁定,如需解锁账号,请联系管理员");
						}
					}
					appUsers.updUsers(users);
				}
				return mapping.findForward("checkfail");
				// 密码正确时
			} else {
				//获取用户分类
				Set<Integer> ucSet = appUsers.getUserCategarySet(users.getUserid());
				//检查用户类型是否正确
				if(!ucSet.contains(UserCategary.RTCI.getValue())) {
					request.setAttribute("result", "对不起，用户类型错误！");
					return mapping.findForward("checkfail");
				}
				//判断机器时间是否有效
				/*if(cuv.checkTimeVal(username)){
					request.setAttribute("result", "服务器系统时间异常<BR/>请与系统管理员联系");
					return mapping.findForward("checkfail");
				}*/
				// 判断用户是否可用
				if(usersBean.getStatus() != 1){
					request.setAttribute("result", "对不起，您的账号不可用！");
					return mapping.findForward("checkfail");
				}
				// 判断用户所属机构是否撤销
				Organ organ = appOrgan.getOrganByID_Isrepeal(usersBean.getMakeorganid());
				if(organ == null){
					request.setAttribute("result", "对不起，您的账号不可用！");
					return mapping.findForward("checkfail");
				}
				// 设置用户在线
				appUsers.setOnline(usersBean.getUserid());
				request.getSession().setAttribute("users", usersBean);
				// 判断当前用户的密码是否过于简单
				Integer strongLevel = CheckPsdStrong.getPsdStrongLevel(password);
				// 密码强度不够时,提示需要改密码(数字英文混合+大于6位)
				if(strongLevel < 3){
					request.setAttribute("psdWarn", false);
					return mapping.findForward("checkfail");
				}
				
				//非CWID用户检查密码有效期
				if(users.getIsCwid() == null || users.getIsCwid() != 1) { 
					//判断密码有效期
					UserService us = new UserService();
					int day = us.differentDaysByMillisecond(Dateutil.getCurrentDate(), usersBean.getVad());
					//七天之内即将过期,提示更新
					if(day > 0 && day <=6) { 
						usersBean.setPsdExpireWarn(true);
					} else if(day <= 0) {
						//已到期,强制更新 
						request.setAttribute("isForce", "1");
						request.setAttribute("msg", "密码已过期,请修改密码");
						request.setAttribute("userName", usersBean.getLoginname());
						return new ActionForward("/users/updselfpwd.jsp");
					}
				}
				ActionForward actionForward = new ActionForward();
				actionForward.setPath("initMainPageAction.do");
				actionForward.setRedirect(true);
				appUsers.updateLastLogin(username);
				DBUserLog.addUserLog(usersBean.getUserid(), "系统管理", "登陆[IP:"+request.getRemoteHost()+"]");
				return actionForward;
			}
		} catch (Exception e) {
			WfLogger.error(e.getMessage(),e);
			logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	private UsersBean getUsersBean(Users users, String password,
			Properties systemPro) throws Exception {
		UsersBean usersBean = null;
		// 判断登陆方式
		if(users.getIsCwid() != null && users.getIsCwid() == 1){
			// LDAP方式登陆,调用webservice
			Boolean isSuccess = LDAPServices.authenticateUserByAD(users.getLoginname(), password);
			if(isSuccess){
				usersBean = appUsers.getUsersBeanByLoginname(users.getLoginname());
			}
		} else {
			// 正常方式登陆,使用用户名密码验证 
			// 得到加密后的密码
			String securityPassword = Encrypt.getSecret(password, 1);
			// 得到用户名和密码符合的用户
			usersBean = appUsers.CheckUsersNamePassword(users.getLoginname(),securityPassword);
		}
		return usersBean;
	}
	/**
	 * 检查验证码
	 */
	private String checkNum(HttpServletRequest request,Properties systemPro){
		//判断登陆方式
		String loginMode = systemPro.getProperty("deployEnv");
		if(Constants.ENV_INTERNAL.equals(loginMode)){
			//LDAP不检查验证码
		}else {
			// 得到验证码
			String checknum = request.getParameter("CheckNum");
			String chknumObj = (String) request.getSession().getAttribute(Constants.CHECKIMG_NUMBER);
			if(chknumObj == null){
				return "对不起，登录超时请重新登陆！";
			}
			String chknum;
			// 当前session的验证码
			chknum = chknumObj.toString();
			// 验证验证码是否正确
			if (!chknum.equals(checknum)) {
				return "对不起，验证码错误！";
			}
		}
		return "";
	}
	
	public void testOracle() {
		WfLogger.error("testOracle start...");
		Connection conn = null;// 创建一个数据库连接
		PreparedStatement ps = null;// 创建预编译语句对象，用这个不用Statement
		ResultSet resultSet = null;// 创建一个结果集对象
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序,不用DriverManager
			String url = "jdbc:oracle:thin:@rtci-new-q.cdvhuhr0xpoy.rds.cn-north-1.amazonaws.com.cn:1521:RTCIP";// Oracle地址
			String user = "MYTNM";// 数据库用户名
			String password = "Happy259";// 数据库密码
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			String sql = "select * from users where loginname=?";// 预编译语句，“？”代表参数
			ps = conn.prepareStatement(sql);// 实例化预编译语句
			ps.setString(1, "GHWKV");// 设置参数，前面的1表示第一个问号（第二个问号就用2）
			resultSet = ps.executeQuery();// 执行查询
			while (resultSet.next()) {// 当结果集不为空时
				WfLogger.error("userid:"+resultSet.getString("id"));
				//System.out.println(resultSet.getString("age"));
			}
		} catch (Exception e) {
			WfLogger.error("testOracle:", e);
			//e.printStackTrace();
		} finally {
			// 从小到大释放. resultSet < Statement < Connection
			// 释放时调用close方法即可. 如果其中一个对象的关闭 出现了异常. 也要保证其他的对象关闭方法被调用.
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				WfLogger.error("", e);
			} finally {
				try {
					if (ps != null) {
						ps.close();
					}
				} catch (SQLException e) {
					WfLogger.error("", e);
				} finally {
					try {
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e) {
						WfLogger.error("", e);
					}
				}
			}
		}
	}
	
}
