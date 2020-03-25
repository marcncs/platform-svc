package com.winsafe.drp.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserArea;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.cache.UsersCache;
import com.winsafe.webservice.client.ldap.LDAPServices;

public class UsersService {
	private UsersCache cache = new UsersCache();
	private AppUsers appo = new AppUsers();

	public List getUsers(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		return appo.getUsers(request, pagesize, pWhereClause);
	}

	public Users getUsers(String loginname) throws Exception {
		Users u = cache.getUsersByLoginname(loginname);
		if (u == null) {
			u = appo.getUsers(loginname);
			modifyCache();
		}
		return u;
	}

	public int getCountUsers() throws Exception {
		List list = getAllUsers();
		return list.size();
	}

	public void InsertUsers(Users users) throws Exception {
		appo.InsertUsers(users);
		modifyCache();
	}

	public void updUsers(Users users) throws Exception {
		appo.updUsers(users);
		modifyCache();
	}

	public UsersBean CheckUsersNamePassword(String username, String password)
			throws Exception {
		Users users = appo.getUsersByLoginname(username); 
		if(users == null) { 
			return null;
		}
		UsersBean usersBean = CheckUsersNamePassword(users, password);
		return usersBean;
	}

	public UsersBean CheckUsersNamePassword(Users users, String password)
			throws Exception {
		UsersBean usersBean = null;
		// 判断登陆方式
		if (users.getIsCwid() != null && users.getIsCwid() == 1) {
			// LDAP方式登陆,调用webservice
			Boolean isSuccess = LDAPServices.authenticateUserByAD(users
					.getLoginname(), password);
			if (isSuccess) {
				usersBean = appo.getUsersBeanByLoginname(users.getLoginname());
			}
		} else {
			// 正常方式登陆,使用用户名密码验证
			// 得到加密后的密码
			String securityPassword = Encrypt.getSecret(password, 1);
			// 得到用户名和密码符合的用户
			usersBean = appo.CheckUsersNamePassword(users.getLoginname(),
					securityPassword);
		}
		return usersBean;
	}

	public List getIDAndLoginNameByOID2(String oid) throws Exception {
		List list = cache.getIDAndLoginNameByOID2(oid);
		if (list == null) {
			list = appo.getIDAndLoginNameByOID2(oid);
			modifyCache();
		}
		return list;
	}

	public UsersBean getUsersByID(int userid) throws Exception {
		UsersBean ub = cache.getUsersByID(userid);
		if (ub == null) {
			ub = appo.getUsersByID(userid);
			modifyCache();
		}
		return ub;
	}

	public Users getUsersByid(int userid) throws Exception {
		if (userid == 0) {
			return null;
		}
		Users u = cache.getUsers(userid);
		if (u == null) {
			u = appo.getUsersByid(userid);
			modifyCache();
		}
		return u;
	}

	public void delUsersById(int userid) throws Exception {
		appo.delUsersById(userid);
		modifyCache();
	}

	public List getAllUsers() throws Exception {
		List list = cache.getAllUsers();
		if (list == null) {
			list = appo.getAllUsers();
			cache.putUsersList(list);
		}
		return list;
	}

	public List getUsersByDept(int deptid) throws Exception {
		List list = new ArrayList();
		if (deptid == 0) {
			return list;
		}
		list = cache.getUsersByDept(deptid);
		if (list == null) {
			list = appo.getUsersByDept(deptid);
			modifyCache();
		}
		return list;
	}

	public List getUsersByOrgan(String organid) throws Exception {
		List list = cache.getUsersByOrgan(organid);
		if (list == null) {
			list = appo.getUsersByOrgan(organid);
			modifyCache();
		}
		return list;
	}

	public void setOnline(int userid) throws Exception {
		appo.setOnline(userid);
	}

	public void setOffline(int userid) throws Exception {
		appo.setOffline(userid);
	}

	public void resetUserPWD(int uid, String pwd) throws Exception {
		appo.resetUserPWD(uid, pwd);
		modifyCache();
	}

	public boolean CheckUsersPasswordByUserID(int userid, String password)
			throws Exception {
		return appo.CheckUsersPasswordByUserID(userid, password);
	}

	public void InsertUserArea(UserArea ua) throws Exception {
		appo.InsertUserArea(ua);
	}

	public List getAllAreas() throws Exception {
		return appo.getAllAreas();
	}

	public List getAllUserAreas(int userid) throws Exception {
		return appo.getAllUserAreas(userid);
	}

	public void deleteUserAreas(int userid) throws Exception {
		appo.deleteUserAreas(userid);
	}

	public String getUsersName(int id) throws Exception {
		if (id == 0) {
			return "";
		}
		Users o = getUsersByid(id);
		if (o != null) {
			return o.getRealname();
		}
		return "";
	}

	private void modifyCache() throws Exception {
		// cache.modifyUsers(appo.getAllUsers());
	}

	public List getUsersByDetailData(String table, String column)
			throws Exception {
		return appo.getUsersByDetailData(table, column);
	}

	public void addCategary(String[] categaries) {
		// TODO Auto-generated method stub

	}

	public void unlockUsers(Integer userId) throws HibernateException, SQLException, Exception {
		appo.unlockUsers(userId);
		
	}

}
