package com.winsafe.drp.keyretailer.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganVisit;
import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganVisit;
import com.winsafe.drp.dao.Role;
import com.winsafe.drp.dao.UserApply;
import com.winsafe.drp.dao.UserRole;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.metadata.ApplyUserType;
import com.winsafe.drp.metadata.UserCategary;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.PasswordUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil; 
import com.winsafe.sap.metadata.YesOrNo;

public class UserService {
	
	private static Logger logger = Logger.getLogger(UserService.class);
	private AppOrgan appOrgan = new AppOrgan();
	private AppMakeConf appmc = new AppMakeConf();
	private AppUserVisit auv = new AppUserVisit();
	private AppRuleUserWH appRuWH = new AppRuleUserWH();
	private AppOrganVisit aov = new AppOrganVisit();
	private AppWarehouseVisit appWV = new AppWarehouseVisit();
	private AppRole ar = new AppRole();
	private AppUsers appUsers = new AppUsers();
	
	public void addUsers(Users users) throws Exception {
		appUsers.InsertUsers(users);
	}
	
	/**
	 * 为所有角色增加记录,方便角色勾选用户
	 */
	private void addRole(Integer userid) throws Exception{
		List list = ar.getRoleList();
		Iterator it = list.iterator();
		UserRole ur = null;
		while (it.hasNext()) {
			ur = new UserRole();
			Role r = (Role) it.next();
			ur.setUserid(userid);
			ur.setIspopedom(0);
			ur.setRoleid(r.getId());
			ar.addUserRole(ur);
		}
	}
	/**
	 * 增加自己机构的管辖权限
	 * @throws Exception 
	 */
	private void addSelfRule(String organId,Integer userid) throws  Exception{
	 	UserVisit uv = new UserVisit();
	    uv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("user_visit", 0, "")));
	    uv.setUserid(userid);
	    uv.setVisitorgan(organId);
	    uv.setVisitdept("");
	    uv.setVisitusers(String.valueOf(userid));
	    auv.SaveUserVisit(uv);
	      
		//增加管辖仓库
		appRuWH.addRuleWhByOid(organId, userid+"");
		//更新make_conf
		appmc.updMakeConf("RULE_USER_WH","RULE_USER_WH");
		logger.debug("userid[" +  userid + "],增加自己机构[" + organId + "]的管辖权限");
	}
	/**
	 * 增加用户所属机构下的子机构业务往来权限
	 * @throws Exception 
	 */
	private void addWarehouseVist(String parentOrganId,String userid) throws Exception{
		/*List<Organ> childOrganList = appOrgan.getListByParentId(parentOrganId);
		for(Organ childOrgan : childOrganList){
			OrganVisit newov = new OrganVisit();
			Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_visit", 0, ""));
			newov.setId(id);
			newov.setUserid(Integer.valueOf(userid));
			newov.setVisitorgan(childOrgan.getId());
			aov.SaveOrganVisit(newov);
			//增加业务往来仓库
			appWV.addVWByOid(childOrgan.getId(), userid);
			//更新make_conf
			appmc.updMakeConf("warehouse_visit","warehouse_visit");
		} */
		
		aov.addOrganVisitByParentOrganId(userid, parentOrganId);
		//增加业务往来仓库
		appWV.addVWByPOid(parentOrganId, userid);
		//更新make_conf
		appmc.updMakeConf("organ_visit","organ_visit");
		appmc.updMakeConf("warehouse_visit","warehouse_visit");
	}
	
	public int differentDaysByMillisecond(Date fromDate,Date toDate)
    {
        int days = (int) ((toDate.getTime() - fromDate.getTime()) / (1000*3600*24));
        return days;
    }

	public void addCategary(int userId, String[] categaries) throws Exception {
		for(String categary : categaries) { 
			appUsers.addCategary(userId, Integer.valueOf(categary));
		}
		
	}

	public void updUsers(Users us) throws Exception {
		appUsers.updUsers(us);
	}

	public void updUserCategary(Integer userId, String[] categaries) throws Exception {
		//已有角色类型
		Set<Integer> existsSet = appUsers.getUserCategarySet(userId);
		Set<Integer> result = new HashSet<Integer>();
		Set<Integer> updSet = new HashSet<Integer>();
		for(String categary : categaries) {
			updSet.add(Integer.valueOf(categary));
		}
		//需删除的
		result.addAll(existsSet);
		result.removeAll(updSet);
		if(result.size() > 0) {
			StringBuffer cids= new StringBuffer();
	    	for(Integer cid : result) {
	    		cids.append(","+cid);
	    	}
	    	appUsers.delUserCategary(userId, cids.substring(1));
		}
		//需新增的
		result.clear();
		result.addAll(updSet);
		result.removeAll(existsSet);
		if(result.size()> 0) {
			for(Integer categaryId : result) {
				appUsers.addCategary(userId, categaryId);
	    	}
		}
	}

	public void delUserCategary(String userId) throws Exception {
		appUsers.delUserCategary(userId);
		
	}
	
	public void delUserCategaryByUserName(String userName) throws Exception {
		appUsers.delUserCategaryByUserName(userName);
		
	}
	
	/**
	 * 添加从IAM服务同步过来的CWID用户
	 * @param loginName
	 * @param makeOrganId
	 * @return
	 * @throws Exception
	 */
	public Users addCWIDUsers(String loginName, String makeOrganId) throws Exception {
		Integer userid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("users", 0, ""));
		String syspassword = Encrypt.getSecret("a123456A", 1);
		Users us = new Users();
		us.setUserid(userid);
		us.setLoginname(loginName);
		us.setPassword(syspassword);
		us.setApprovepwd(syspassword);
		us.setProvince(1);
		us.setCity(1);
		us.setAreas(1);
		us.setCreatedate(DateUtil.getCurrentDate());
		us.setLogintimes(0);
		us.setMakeorganid(makeOrganId);
		us.setMakedeptid(1);
		us.setStatus(1);
		us.setIsonline(0);
		us.setIsCwid(1);
		appUsers.InsertUsers(us);
		addRole(userid);
		return us;
	}

	public void assignUserRole(Integer userId, Integer roleId) throws Exception {
		appUsers.updUsersRole(userId, roleId, 1);
	}

	public void checkAndAddUserCategary(Integer userId,
			Set<Integer> groupCategary) throws Exception {
		Set<Integer> ucSet = appUsers.getUserCategarySet(userId);
		groupCategary.removeAll(ucSet);
		if(groupCategary.size() > 0) {
			for(Integer categaryId : groupCategary) {
				appUsers.addCategary(userId, categaryId);
			}
		}
	}

	public void updCWIDUsersStatus() throws Exception {
		appUsers.updCWIDUsersStatus();
	}

	public void revorkUserRole(Integer userId, Integer roleId) throws Exception {
		appUsers.updUsersRole(userId, roleId, 0);
	}
	
	public void revorkUserRoles(String userName) throws Exception {
		appUsers.revorkUserRoles(userName);
	}

	public void checkAndDelUserCategary(Integer userId,
			Set<Integer> groupCategary) throws Exception { 
		for(Integer categaryId : groupCategary) {
			appUsers.delUserCategary(userId, categaryId);
		}
	}

	public Users addUsers(UserApply ua, Organ organ, String loginName) throws Exception {
		//新增用户
		Integer userid = Integer.valueOf(MakeCode.getExcIDByRandomTableName("users", 0, ""));
		Users us = new Users();
		us.setUserid(userid);
		if(!StringUtil.isEmpty(loginName)) {
			us.setLoginname(loginName);
		} else {
			us.setLoginname(ua.getMobile());
		}
		us.setRealname(ua.getName());
		us.setPassword(ua.getPassword());
		us.setApprovepwd(ua.getPassword());
		us.setMobile(ua.getMobile());
		us.setProvince(ua.getProvince());
		us.setCity(ua.getCity());
		us.setAreas(ua.getAreas());
		us.setCreatedate(DateUtil.getCurrentDate());
		us.setLogintimes(0);
		us.setMakeorganid(organ.getId());
		us.setMakedeptid(1);
		us.setStatus(1);
		us.setIsonline(0);
		us.setIsCwid(YesOrNo.NO.getValue());
		//设置有效期
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, Constants.PWD_VAL_DATE);
		us.setValidate(now.getTime());
		appUsers.InsertUsers(us);
		//新增密码历史记录
		PasswordUtil.addPwdHistory(ua.getPassword(), userid);
		//初始化用户角色
		addRole(userid);
		
		//分配角色
		Properties systemPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
		String defaultRoles = null;
		if(ApplyUserType.TD.getValue().equals(ua.getUserType())){
			defaultRoles=systemPro.getProperty("defaultRoleForTDUser");
		} else if(ApplyUserType.WH.getValue().equals(ua.getUserType())){
			defaultRoles=systemPro.getProperty("defaultRoleForWHUser");
		} else if(ApplyUserType.BKD.getValue().equals(ua.getUserType())) {
			defaultRoles=systemPro.getProperty("defaultRoleForBKDUser");
		} else {
			defaultRoles=systemPro.getProperty("defaultRoleForBKRUser");
		}
		if(!StringUtil.isEmpty(defaultRoles)) {
			for(String roleId : defaultRoles.split(",")) {
				assignUserRole(userid, Integer.valueOf(roleId));
			}
		}
		//设置管辖权限
		addSelfRule(organ.getId(), userid);
		//设置往来权限
		addWarehouseVist(organ.getId(), userid.toString());
		
		if(ApplyUserType.TD.getValue().equals(ua.getUserType())
				|| ApplyUserType.WH.getValue().equals(ua.getUserType())) {
			//设置用户类型
			addCategary(userid, new String[]{UserCategary.RTCI.getValue().toString()});
		} else {
			addCategary(userid, new String[]{UserCategary.RI.getValue().toString()});
		}
		return us;
		
	}
}
