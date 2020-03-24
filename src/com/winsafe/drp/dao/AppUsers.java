package com.winsafe.drp.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet; 
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.metadata.UserType;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppUsers { 

	public List getUsers(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = " from Users as u  " + pWhereClause
				+ " order by u.userid desc";
		// System.out.println("-----"+hql);
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List<Map<String,String>> getUsersList(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = "select u.*,o.organname from Users u " +
				"join organ o on o.id = u.makeorganid " + pWhereClause;
		if(pagesize>0) {
			return PageQuery.jdbcSqlserverQuery(request, " userid desc ", sql, pagesize);
		} else {
			sql+=" order by u.userid desc ";
			return EntityManager.jdbcquery(sql);
		}
		
	}
	
	public List<Map<String,String>> getUsersList(HttpServletRequest request, int pagesize,
			String pWhereClause, Map<String, Object> param) throws Exception {
		String sql = "select u.*,o.organname from Users u " +
				"join organ o on o.id = u.makeorganid " + pWhereClause;
		if(pagesize>0) {
			return PageQuery.jdbcSqlserverQuery(request, " userid desc ", sql, pagesize, param);
		} else {
			sql+=" order by u.userid desc ";
			return EntityManager.jdbcquery(sql, param);
		}
		
	}

	public Users getUsers(String loginname) throws Exception {
		String mobile = Encrypt.getSecret(loginname, 3);
		String sql = " from Users as u where upper(u.loginname)= upper('" + loginname + "') or upper(u.mobile)=upper('"
				+ mobile + "')";
		return (Users) EntityManager.find(sql);
	}

	public int getCountUsers() throws Exception {
		String sql = " select count(*) from Users ";
		return EntityManager.getRecordCount(sql);
	}

	public void InsertUsers(Users users) throws Exception {
		EntityManager.save(users);
	}

	public void updUsers(Users users) throws Exception {
		EntityManager.update(users);
		// String sql = " update users set
		// realname='"+users.getRealname()+"',nameen='"+users.getNameen()+"',"+
		// "sex="+users.getSex()+",birthday='"+DateUtil.formatDate(users.getBirthday())+"',"
	}

	public UsersBean CheckUsersNamePassword(String username, String password)
			throws Exception {
		UsersBean usersBean = new UsersBean();
		Users users = null;
		String sql = " from Users as u where upper(u.loginname)=upper('" + username
				+ "') and u.password='" + password + "'";
		users = (Users) EntityManager.find(sql);
		if (users != null) {
			OrganService os = new OrganService();
			Organ organ = os.getOrganByID(users.getMakeorganid());
			usersBean.setUserid(users.getUserid());
			usersBean.setLoginname(users.getLoginname());
			usersBean.setRealname(users.getRealname());
			usersBean.setMakeorganid(users.getMakeorganid());
			usersBean.setMakeorganname(organ.getOrganname());
			usersBean.setOrgansysid(organ.getSysid());
			usersBean.setParentorganid(organ.getParentid());
			usersBean.setStatus(users.getStatus());
			usersBean.setMakedeptid(users.getMakedeptid());
			usersBean.setIscall(users.getIscall());
			usersBean.setMobile(users.getMobile());
			usersBean.setValkey(users.getValkey());
			usersBean.setImgurl(users.getImgurl());
			usersBean.setUserType(users.getUserType());
			usersBean.setOrganType(organ.getOrganType());
			usersBean.setOrganModel(organ.getOrganModel());
			usersBean.setVad(users.getValidate());
			usersBean.setIsCwid(users.getIsCwid());
		} else {
			usersBean = null;
		}
		return usersBean;
	}
	
	public UsersBean CheckUsersMobliePassword(String mobile, String password)
			throws Exception {
		mobile = Encrypt.getSecret(mobile, 3);
		UsersBean usersBean = new UsersBean();
		Users users = null;
		String sql = " from Users as u where upper(u.mobile)=upper('"
				+ mobile + "') and u.password='" + password + "'";
		users = (Users) EntityManager.find(sql);
		if (users != null) {
			OrganService os = new OrganService();
			Organ organ = os.getOrganByID(users.getMakeorganid());
			usersBean.setUserid(users.getUserid());
			usersBean.setLoginname(users.getLoginname());
			usersBean.setRealname(users.getRealname());
			usersBean.setMakeorganid(users.getMakeorganid());
			usersBean.setMakeorganname(organ.getOrganname());
			usersBean.setOrgansysid(organ.getSysid());
			usersBean.setParentorganid(organ.getParentid());
			usersBean.setStatus(users.getStatus());
			usersBean.setMakedeptid(users.getMakedeptid());
			usersBean.setIscall(users.getIscall());
			usersBean.setMobile(users.getMobile());
			usersBean.setValkey(users.getValkey());
			usersBean.setImgurl(users.getImgurl());
			usersBean.setUserType(users.getUserType());
			usersBean.setOrganType(organ.getOrganType());
			usersBean.setOrganModel(organ.getOrganModel());
		} else {
			usersBean = null;
		}
		return usersBean;
	}

	public UsersBean getUsersBeanByLoginname(String loginname) throws Exception {
		String mobile = Encrypt.getSecret(loginname, 3);
		UsersBean usersBean = new UsersBean();
		Users users = null;
		String sql = " from Users as u where upper(u.loginname)=upper('" + loginname + "') or upper(u.mobile)=upper('" + mobile + "')";
		users = (Users) EntityManager.find(sql);
		if (users != null) {
			OrganService os = new OrganService();
			Organ organ = os.getOrganByID(users.getMakeorganid());
			usersBean.setUserid(users.getUserid());
			usersBean.setLoginname(users.getLoginname());
			usersBean.setRealname(users.getRealname());
			usersBean.setMakeorganid(users.getMakeorganid());
			usersBean.setMakeorganname(organ.getOrganname());
			usersBean.setOrgansysid(organ.getSysid());
			usersBean.setParentorganid(organ.getParentid());
			usersBean.setStatus(users.getStatus());
			usersBean.setMakedeptid(users.getMakedeptid());
			usersBean.setIscall(users.getIscall());
			usersBean.setMobile(users.getMobile());
			usersBean.setValkey(users.getValkey());
			usersBean.setImgurl(users.getImgurl());
			usersBean.setUserType(users.getUserType());
			usersBean.setOrganType(organ.getOrganType());
			usersBean.setOrganModel(organ.getOrganModel());
			usersBean.setVad(users.getValidate());
			usersBean.setIsCwid(users.getIsCwid());
		} else {
			usersBean = null;
		}
		return usersBean;
	}
	
	public Users getUsersByLoginname(String loginname) throws Exception {
		String mobile = Encrypt.getSecret(loginname, 3);
//		String sql = " from Users as u where upper(u.loginname)=upper('" + loginname + "') or upper(u.mobile)=upper('" + Encrypt.getSecret(loginname, 3) + "')";
		String sql = " from Users as u where upper(u.loginname)=upper('" + loginname + "') or upper(u.mobile)=upper('" + mobile + "')";
		return (Users) EntityManager.find(sql);
	}

	public UsersBean getUsersBeanByNccode(String nccode) throws Exception {
		UsersBean usersBean = new UsersBean();
		Users users = null;
		String sql = " from Users as u where u.nccode='" + nccode + "'";
		users = (Users) EntityManager.find(sql);
		if (users != null) {
			OrganService os = new OrganService();
			Organ organ = os.getOrganByID(users.getMakeorganid());
			usersBean.setUserid(users.getUserid());
			usersBean.setLoginname(users.getLoginname());
			usersBean.setRealname(users.getRealname());
			usersBean.setMakeorganid(users.getMakeorganid());
			usersBean.setMakeorganname(organ.getOrganname());
			usersBean.setOrgansysid(organ.getSysid());
			usersBean.setParentorganid(organ.getParentid());
			usersBean.setStatus(users.getStatus());
			usersBean.setMakedeptid(users.getMakedeptid());
			usersBean.setIscall(users.getIscall());
			usersBean.setOrganType(organ.getOrganType());
			usersBean.setOrganModel(organ.getOrganModel());
			usersBean.setIsCwid(users.getIsCwid());
		} else {
			usersBean = null;
		}
		return usersBean;
	}

	public boolean CheckPassword(String username, String password)
			throws Exception {
		Users users = null;
		String sql = " from Users as u where u.loginname='" + username
				+ "' and u.password='" + password + "' ";
		users = (Users) EntityManager.find(sql);
		return users != null;
	}

	public List getIDAndLoginName() throws Exception {
		String sql = " select u.userid,u.loginname,u.realname from Users as u where u.status=1";
		return EntityManager.getAllByHql(sql);
	}

	public List getIDAndLoginNameByOID2(String oid) throws Exception {
		List auls = new ArrayList();
		String sql = " select u.userid,u.loginname,u.realname from Users as u where u.status=1 and u.makeorganid like '"
				+ oid + "%'";
		List ls = EntityManager.getAllByHql(sql);
		for (int u = 0; u < ls.size(); u++) {
			UsersBean ubs = new UsersBean();
			Object[] ub = (Object[]) ls.get(u);
			ubs.setUserid(Integer.valueOf(ub[0].toString()));
			ubs.setLoginname(String.valueOf(ub[1]));
			ubs.setRealname(ub[2].toString());
			auls.add(ubs);
		}
		return auls;
	}

	public UsersBean getUsersByID(int userid) throws Exception {
		UsersBean usersBean = new UsersBean();
		Users users = null;
		String sql = " from Users as u where u.userid=" + userid;
		users = (Users) EntityManager.find(sql);
		if (users != null) {
			usersBean.setUserid(users.getUserid());
			usersBean.setLoginname(users.getLoginname());
			usersBean.setRealname(users.getRealname());
		} else {
			usersBean = null;
		}

		return usersBean;
	}

	public Users getUsersByid(int userid) throws Exception {
		String sql = " from Users as u where u.userid=" + userid;
		return (Users) EntityManager.find(sql);
	}

	public List getAllUsers() throws Exception {
		return EntityManager.getAllByHql("from Users ");
	}

	public List getUsersByDept(int deptid) throws Exception {
		String sql = " from Users where makedeptid=" + deptid;
		return EntityManager.getAllByHql(sql);
	}

	public List getUsersByOrgan(String MakeOrganID) throws Exception {
		// String sql = " from Users where makeorganid='" + MakeOrganID + "'";
		String sql = " from Users ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getUsersByMakeOrganId(String MakeOrganID) throws Exception {
		String sql = " from Users u where u.makeorganid='" + MakeOrganID + "'";
		return EntityManager.getAllByHql(sql);
	}
	
	public void setOnline(int userid) throws Exception {
		String sql = "update users set isonline=1 where userid=" + userid;
		EntityManager.updateOrdelete(sql);
	}

	public void setOffline(int userid) throws Exception {
		String sql = "update users set isonline=0 where userid=" + userid;
		EntityManager.updateOrdelete(sql);
	}

	public void delUsersById(int userid) throws Exception {
		EntityManager
				.updateOrdelete("delete from users where userid=" + userid);
	}

	public void resetUserPWD(int uid, String pwd) throws Exception {
		String sql = "update users set password='" + pwd + "' where userid="
				+ uid;
		EntityManager.updateOrdelete(sql);

	}
	
	public void resetUserPWD(int uid, String pwd, int days) throws Exception {
		String sql = "update users set password='" + pwd + "', vad=sysdate+"+days+" where userid="
				+ uid;
		EntityManager.executeUpdate(sql);
	}
	
	public void resetUserPWDByLoginName(String loginname, String pwd, int days) throws Exception {
		String sql = "update users set password='" + pwd + "', vad=sysdate+"+days+" where loginname='"
				+ loginname+"'";
		EntityManager.executeUpdate(sql);
	}

	public boolean CheckUsersPasswordByUserID(int userid, String password)
			throws Exception {
		String sql = " from Users as u where u.userid='" + userid
				+ "' and u.password='" + password + "'";
		Users users = (Users) EntityManager.find(sql);
		if (users != null) {
			return true;
		}
		return false;
	}

	public void InsertUserArea(UserArea ua) throws Exception {
		EntityManager.save(ua);
	}

	public List getAllAreas() throws Exception {
		String sql = "from CountryArea";
		return EntityManager.getAllByHql(sql);
	}

	public List getAllUserAreas(int userid) throws Exception {
		return EntityManager.getAllByHql("from UserArea as ua where ua.userid="
				+ userid);
	}

	public void deleteUserAreas(int userid) throws Exception {
		EntityManager.updateOrdelete("delete from user_area where userid="
				+ userid);
	}

	/**
	 * 根据用友的导入的NCCODE查询WINSAFE的标准编号
	 * 
	 * @param nccode
	 *            用友提供的编号
	 * @return Warehouse
	 * @throws Exception
	 */
	public Users findByNCcode(String nccode) throws NotExistException {
		Users temp = new Users();
		String sql = " from Users as w where w.nccode='" + nccode + "'";
		temp = (Users) EntityManager.find(sql);
		if (temp == null) {
			try {
				DBUserLog.addUserLog(1, 13, "基础数据错误，找不到相对应的用户" + nccode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new NotExistException("基础数据错误，找不到相对应的用户");
		}
		return temp;

	}

	/**
	 * 通过登录名找到相对应的有效期字段
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public Date getValByUname(String username) throws Exception {
		Users users = null;
		Date userVal = null;
		String sql = " from Users as u where u.loginname='" + username
				+ "'  and islogin=1";
		users = (Users) EntityManager.find(sql);
		if (users != null) {
			userVal = users.getValidate();
		}
		return userVal;

	}

	/**
	 * 通过登录名找到相对应的有效期字段(key列)
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public String getKeyByUname(String username) throws Exception {
		Users users = null;
		String key = "";
		String sql = " from Users as u where u.loginname='" + username
				+ "'  and islogin=1";
		users = (Users) EntityManager.find(sql);
		if (users != null) {
			key = users.getValkey();
		}
		return key;

	}

	/**
	 * 每次登陆成功后通过配置文件的值改变所有人员的有效期
	 * 
	 * @param userVal
	 * @throws Exception
	 */
	public void updateUserValKey(String valkey) throws Exception {
		String sql = "update users set valkey='" + valkey + "'";
		EntityManager.updateOrdelete(sql);

	}

	/**
	 * 通过登录名找到相对应的最后登录时间
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public Date getLastTimeByUname(String username) throws Exception {
		Date date = null;
		Users users = new Users();
		String sql = " from Users as u where u.loginname='" + username
				+ "'  and islogin=1";
		users = (Users) EntityManager.find(sql);
		if (users != null) {
			date = users.getLastlogin();
		}
		return date;

	}

	public void updateLastLogin(String username) throws Exception {
		String sql = "update users set LastLogin=sysdate,logintimes=nvl(logintimes,0)+1,trycount=0 where upper(loginname)=upper('"
				+ username + "') ";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 
	 */
	public List getUserByInUserID(String userid) throws Exception {
		String hql = "from Users as ua where ua.userid in (" + userid + ")";
		return EntityManager.getAllByHql(hql);
	}

	public List getUsersByRegion(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql = " from Users as u , Region as r, RegionUsers as ru  "
				+ whereSql + " order by u.userid desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public boolean getUserRolebyUserid(Integer userid) {
		String sql = " from UserRole as u where u.userid=" + userid
				+ " and ispopedom=1 and roleid=1";
		UserRole ur = (UserRole) EntityManager.find(sql);
		if (ur != null)
			return true;
		return false;
	}

	public boolean getAllUserRole(Integer userid) {
		String sql = "from UserRole where userid=" + userid
				+ "  and  roleid=1  and ispopedom = 1";
		UserRole ur = (UserRole) EntityManager.find(sql);
		if (ur != null) {
			return true;
		}
		return false;

	}

	public List getUsersByDetailData(String table, String column) {
		String sql = "select distinct u from Users as u, "+table+" as t where u.userid = t."+column;
		return EntityManager.getAllByHql(sql);
	}
	
	public boolean isUsersExists(String mobile) {
		mobile = Encrypt.getSecret(mobile, 3);
		String sql = "select count(*) from Users where mobile = '"+mobile+"'";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public UsersBean getUsersBeanByUserId(String userId) throws Exception {
		UsersBean usersBean = new UsersBean(); 
		Users users = null;
		String sql = " from Users as u where userid=" + userId;
		users = (Users) EntityManager.find(sql);
		if (users != null) {
			OrganService os = new OrganService();
			Organ organ = os.getOrganByID(users.getMakeorganid());
			usersBean.setUserid(users.getUserid());
			usersBean.setLoginname(users.getLoginname());
			usersBean.setRealname(users.getRealname());
			usersBean.setMakeorganid(users.getMakeorganid());
			usersBean.setMakeorganname(organ.getOrganname());
			usersBean.setOrgansysid(organ.getSysid());
			usersBean.setParentorganid(organ.getParentid());
			usersBean.setStatus(users.getStatus());
			usersBean.setMakedeptid(users.getMakedeptid());
			usersBean.setIscall(users.getIscall());
			usersBean.setOrganType(organ.getOrganType());
			usersBean.setOrganModel(organ.getOrganModel());
		} else {
			usersBean = null;
		}
		return usersBean;
	}

	public Users getUsersByMobile(String mobile) {
		mobile = Encrypt.getSecret(mobile, 3);
		String sql = " from Users where mobile='" + mobile+"'";
		return (Users)EntityManager.find(sql);
	}

	public Users getUsersByloginNameAndMobile(String userName, String mobile) { 
		mobile = Encrypt.getSecret(mobile, 3);
		String sql = " from Users where mobile='" + mobile + "' and loginname='"+userName+"'";
		return (Users)EntityManager.find(sql);
	}

	public List<Users> getUsersByType(Integer type) {
		String sql = " from Users where UserType=" + type;
		return EntityManager.getAllByHql(sql);
	}

	public List<Users> getUsersByCustomerId(String organid) {
		String sql = " from Users where userid in (select userId from UserCustomer where organId = '"+organid+"')";
		return EntityManager.getAllByHql(sql);
	}

	public List<Users> getUsersByOperate(String operateName, String moduleName) {
		String sql = "select u from Users u,UserRole ur,RoleMenu rm,Operate op where op.operatename = '"+operateName+"' and op.modulename='"+moduleName+"'" +
				" and rm.operateid = op.id and ur.ispopedom =1 and ur.roleid=rm.roleid and u.userid = ur.userid ";
		return EntityManager.getAllByHql(sql);
	}

	public List<Users> getUsersByRoleName(String roleName) {
		String sql = " from Users where userid in (select userid from UserRole where ispopedom = 1 and roleid = (select id from Role where rolename='"+roleName+"') )";
		return EntityManager.getAllByHql(sql);
	}

	public Set<String> getPwdHsitorySet(int userId) throws Exception {
		String sql = "select PASSWORD from PASSWORD_HISTORY where USERID = "+userId;
		List<Map<String,String>> result =  EntityManager.jdbcquery(sql);
		Set<String> set = new HashSet<String>();
		for(Map<String,String> map : result) {
			set.add(map.get("password"));
		}
		return set; 
	}

	public void removeOldestPwdHistory(int userId) throws Exception {
		String sql = "delete from PASSWORD_HISTORY where userid ="+userId+" and makedate = (select min(MAKEDATE) from PASSWORD_HISTORY where userid = "+userId+")";
		EntityManager.executeUpdate(sql);
	}

	public void addPwdHistory(int userId, String password) throws Exception {
		String sql = "insert into PASSWORD_HISTORY(USERID,PASSWORD,MAKEDATE) VALUES("+userId+",'"+password+"',SYSDATE)";
		EntityManager.executeUpdate(sql); 
	}

	public void addCategary(int userId, int categary) throws Exception {
		String sql = "insert into USER_CATEGARY(USERID,CATEGARYID,MAKEDATE) VALUES(?,?,SYSDATE)";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("userId", userId);
		map.put("categary", categary);
		EntityManager.executeUpdateBySql(sql, map); 
		
	}

	public Set<Integer> getUserCategarySet(Integer userId) throws Exception {
		String hql = "select CATEGARYID from USER_CATEGARY where USERID= ?";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("userId", userId);
		List<BigDecimal> list = EntityManager.getAllListBySql(hql, map);
		Set<Integer> set = new HashSet<Integer>();
		for(BigDecimal obj : list) {
			set.add(obj.intValue());
		}
		return set;
		
		/*
		List<Map<String,String>> result = EntityManager.jdbcquery(sql);
		Set<String> set = new HashSet<String>();
		for(Map<String,String> map : result) {
			set.add(map.get("categaryid"));
		}
		return set;*/
	}

	public void delUserCategary(Integer userId, String cids) throws Exception {
		String sql = "delete from USER_CATEGARY where USERID="+userId+" and CATEGARYID in ("+cids+")";
		EntityManager.executeUpdate(sql); 
	}
	
	public void delUserCategary(String userId) throws Exception {
		String sql = "delete from USER_CATEGARY where USERID="+userId;
		EntityManager.executeUpdate(sql);
	}
	
	public void delUserCategaryByUserName(String userName) throws Exception {
		String sql = "delete from USER_CATEGARY where USERID in (select userid from Users where loginname='"+userName+"')";
		EntityManager.executeUpdate(sql);
	}
	
	public void delUserCategary(Integer userId, Integer categaryId) throws Exception {
		String hql = "delete from USER_CATEGARY where USERID= ? and CATEGARYID = ?";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("userId", userId);
		map.put("categaryId", categaryId);
		EntityManager.executeUpdateBySql(hql, map);
	}

	public List<Users> getCWIDUsersByRoleId(Integer roleId) throws Exception {
		String hql = "select u from Users as u, UserRole as ur where u.userid=ur.userid and u.isCwid=1 and ur.roleid=:roleid and ur.ispopedom=1";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("roleid", roleId);
		return EntityManager.getAllByHql(hql, map);
	}
	
	public List<Users> getUsersByRoleId(Integer roleId) throws Exception {
		String hql = "select u from Users as u, UserRole as ur where u.userid=ur.userid and ur.roleid=:roleid and ur.ispopedom=1";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("roleid", roleId);
		return EntityManager.getAllByHql(hql, map);
	}
	
	public Map<String, Users> getUsersMapByRoleId(Integer roleId) throws Exception {
		Map<String,Users> map = new HashMap<String, Users>();
		List<Users> users = getUsersByRoleId(roleId);
		for(Users user : users ){
			map.put(user.getLoginname(), user);
		}
		return map;
	}
	
	public Map<String, Users> getCWIDUsersMapByRoleId(Integer roleId) throws Exception {
		Map<String,Users> map = new HashMap<String, Users>();
		List<Users> users = getCWIDUsersByRoleId(roleId);
		for(Users user : users ){
			map.put(user.getLoginname(), user);
		}
		return map;
	}
	
	public Users getUsersByUserName(String loginname) throws Exception {
		String hql = " from Users as u where upper(u.loginname)=upper(:loginName) ";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("loginName", loginname);
		return (Users) EntityManager.find(hql, map);
	}

	public void updUsersRole(Integer userId, Integer roleId, int ispopedom) throws Exception {
		String hql = " update UserRole set ispopedom = "+ispopedom+" where userid=:userId and roleid=:roleId";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("roleId", roleId);
		EntityManager.executeUpdateByHql(hql, map);
	}

	public void updCWIDUsersStatus() throws Exception {
		String sql = "update USERS u set status = 0 where iscwid = 1  " +
				"and not EXISTS (select id from USER_ROLE where USERID = u.USERID and ispopedom = 1)";
		EntityManager.executeUpdate(sql);
	}

	public List<Map<String, String>> getUserMobiles() throws Exception {
		String sql = "select USERID, MOBILE from USERS where MOBILE is not NULL ";
		return EntityManager.jdbcquery(sql);
	}

	public void updateUserMobile(String userId, String mobile) throws Exception {
		String sql = "UPDATE USERS set MOBILE='"+mobile+"' where USERID="+userId;
		EntityManager.executeUpdate(sql);
	}
	
	public Set<String> getAllCWIDUserNameSet() throws Exception {
		String sql = "select loginname from users where ISCWID = 1 and STATUS = 1 ";
		List<Map<String, String>> userNameMap = EntityManager.jdbcquery(sql);
		Set<String> set = new HashSet<String>();
		for(Map<String, String> map : userNameMap) {
			set.add(map.get("loginname"));
		}
		return set;
	}

	public void revorkUserRoles(String userName) throws Exception {
		String hql = "update UserRole set ispopedom = 0 where userid in (select userid from Users where loginname=:userName) ";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		EntityManager.executeUpdateByHql(hql, map);
	}

	public void unlockUsers(Integer userId) throws HibernateException, SQLException, Exception {
		String sql = "UPDATE USERS set isLocked=0, tryCount=0 where USERID="+userId;
		EntityManager.executeUpdate(sql);
	}

	public boolean isMobileAlreadyExists(String mobile, String userApplyId) {
		String encryptedMobile = Encrypt.getSecret(mobile, 3);
		StringBuffer sql = new StringBuffer();
		sql.append("select count(userId) from (");
		sql.append("select userid from USERS where mobile='"+encryptedMobile+"' ");
		sql.append("UNION ALL "); 
		sql.append("select userid from USERS where loginname='"+mobile+"' ");
		sql.append("UNION ALL ");
		sql.append("select id from USER_APPLY where mobile='"+encryptedMobile+"' and isApproved <>"+ValidateStatus.NOT_PASSED.getValue());
		if(!StringUtil.isEmpty(userApplyId)) {
			sql.append(" and id <> "+userApplyId);
		}
		sql.append(") temp ");
		return EntityManager.getRecordCountBySql(sql.toString()) > 0;
	}

	public void addUserApply(UserApply userApply) {
		EntityManager.save(userApply);
	}
	
	public List<Object[]> getUserApplyList(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select u,p.areaname,c.areaname,a.areaname from UserApply u,CountryArea p,CountryArea c,CountryArea a " + pWhereClause +" and p.id=u.province and c.id=u.city and a.id=u.areas order by u.id desc ";
		if(pagesize>0) {
			return PageQuery.hbmQuery(request, hql, pagesize);
		} else {
			return EntityManager.getAllByHql(hql);
		}
		
	}

	public List<Map<String, String>> getUserApplyToAudit(String condition,
			String areaId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select u.id userId,mobile,name,organName,userType,p.areaname provinceName,c.areaname cityName,a.areaname areasName from USER_APPLY u ");
		sql.append("join COUNTRY_AREA p on p.id = u.province ");
		sql.append("join COUNTRY_AREA c on c.id = u.city ");
		sql.append("join COUNTRY_AREA a on a.id = u.areas ");
		sql.append(" where ").append(condition);
		sql.append(" and u.isApproved ="+ValidateStatus.NOT_AUDITED.getValue());
		if(!StringUtil.isEmpty(areaId)) {
			sql.append(" and u.areas ="+areaId);
		}
		sql.append(" order by u.id desc ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public UserApply getUserApplyById(Integer userId) {
		String hql = " from UserApply as u where u.id="+userId;
		return (UserApply) EntityManager.find(hql);
	}
	
	public void updUserApply(UserApply users) throws Exception {
		EntityManager.update(users);
	}

	public List<Map<String,String>> getCMUserMailByOrganId(String organId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select email from (select id from S_BONUS_AREA START WITH id in ");
		sql.append("(select sac.salesareaid from ORGAN o join SALES_AREA_COUNTRY sac on o.areas=sac.countryareaid where o.id='").append(organId).append("') ");
		sql.append("connect by prior PARENTID=id ) temp_t ");
		sql.append("join S_USER_AREA sua on temp_t.id=areaid ");
		sql.append("join USERS u on u.userid = sua.userid and u.usertype=").append(UserType.CM.getValue());
		return EntityManager.jdbcquery(sql.toString()); 
	}
}
