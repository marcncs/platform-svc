package com.winsafe.drp.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUserGroup;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserGroup;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.keyretailer.service.UserService;
import com.winsafe.drp.util.AuditMailUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.webservice.client.iam.IAMServices;

public class SyncIAMUsersTask {
	private static Logger logger = Logger.getLogger(SyncIAMUsersTask.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private AppUserGroup appUserGroup = new AppUserGroup();
	private List<UserGroup> groups;
	private Organ organ;
	private AppUsers appUsers = new AppUsers();
	private AppOrgan appOrgan = new AppOrgan();
	private UserService us = new UserService();

	/**
	 * 初始化路径
	 */
	public void init() throws Exception{ 
		groups = appUserGroup.getAllUserGroups();
		organ = appOrgan.getOrganByOrganName("拜耳作物科学有限公司");
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.info("IAM用户同步--------开始----------");
					this.init();
					isRunning = true;
					if(groups.size() > 0) {
						execute();
					}
				} catch (Exception e) {
					logger.error("IAM用户同步任务异常", e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info("IAM用户同步任务---------结束--------");
				}
			}
		}

	}
	
	/**
	 * 
	 * @param fileName
	 * @throws Exception 
	 */
	public void execute() throws Exception{
		try {
			//保存所有从IAM同步到的新用户的用户名
			List<String> newUserNames = new ArrayList<String>(); 
			//保存从IAM同步到的所有用户组用户的用户名
			Set<String> iamUsers = new HashSet<String>();
			for(UserGroup group : groups) {
				Set<String> tempUserSet = new HashSet<String>();
				//从IAM获取用户组的所有CWID
				String[] result = IAMServices.getIdsFromGroup2(group.getGroupName());
//				String[] result = {"BNLHO","CHNPI","CHJIW","GHJGT","GBMPX","HBOXY","HGAME"};
				if(result != null && result.length > 0) {
					iamUsers.addAll(Arrays.asList(result));
					//用户组对应的用户分类
					Set<Integer> groupCategary = appUserGroup.getUserGroupAppSetByGroupId(group.getId());
					//获取该组关联角色下的用户
					Map<String, Users> usersMap = appUsers.getUsersMapByRoleId(group.getRoleId());
					
					//RTCI用户角色中没有的人员但在IAM用户组中有
					tempUserSet.addAll(Arrays.asList(result));
					tempUserSet.removeAll(usersMap.keySet());
					for(String loginName : tempUserSet) {
						if(StringUtil.isEmpty(loginName)) {
							continue;
						}
						//检查该用户是否已经在系统中创建
						Users user = appUsers.getUsersByUserName(loginName);
						//若RTCI不存在该用户,则新增CWID用户
						if(user == null) {
							user = us.addCWIDUsers(loginName, organ.getId());
							newUserNames.add(loginName);
						} else {
							//若用户状态为不可用,则更新成可用
							if(user.getStatus() == null || user.getStatus()!= 1) {
								user.setStatus(1);
							}
							//若是非CWID用户,则更新成CWID用户
							if(user.getIsCwid() == null || user.getIsCwid()!= 1) {
								user.setIsCwid(1);
							}
							us.updUsers(user);
						}
						//分配角色
						us.assignUserRole(user.getUserid(), group.getRoleId());
						//检查并更新用户分类
						us.checkAndAddUserCategary(user.getUserid(), groupCategary);
					}
					
					//如果用户角色中有的人员但在IAM用户组中不存在,则从该角色中删除
					tempUserSet.clear();
					tempUserSet.addAll(usersMap.keySet());
					tempUserSet.removeAll(Arrays.asList(result));
					for(String loginName : tempUserSet) {
						//撤销角色
						us.revorkUserRole(usersMap.get(loginName).getUserid(), group.getRoleId());
						//检查并删除用户分类
						Users users = usersMap.get(loginName);
						if(users.getIsCwid() !=null && users.getIsCwid() == 1) {
							us.checkAndDelUserCategary(usersMap.get(loginName).getUserid(), groupCategary);
						}
						
					}
				}
			}
			//获取系统中所有可用的CWID用户名
			Set<String> cwidUserNames = appUsers.getAllCWIDUserNameSet();
			//查看RTCI中有但IAM没有同步到的用户
			cwidUserNames.removeAll(iamUsers);
			//撤销这部分用户的权限
			for(String userName : cwidUserNames) {
				//撤销角色
				us.revorkUserRoles(userName);
				//撤销用户分类
				us.delUserCategaryByUserName(userName);
			}
			//检查所有CWID用户，如果没有角色关联，把可用状态置位否
			us.updCWIDUsersStatus();
			if(newUserNames.size()>0) {
				AuditMailUtil.addIAMNewUserMail(newUserNames);
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("同步IAM数据异常：",e);
		}
		
	}
}
