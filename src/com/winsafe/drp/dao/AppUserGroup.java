package com.winsafe.drp.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author alex
 * 
 */
public class AppUserGroup {

	public UserGroup getUserGroupById(Integer id) throws Exception {
		String sql = "from UserGroup where id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return (UserGroup) EntityManager.find(sql, map);
	}
	
	public List<UserGroup> getAllUserGroups() throws Exception {
		String sql = "from UserGroup ";
		return EntityManager.getAllByHql(sql);
	}

	public void addUserGroup(Object role) throws Exception {
		EntityManager.save(role);
	}

	public List<UserGroup> getUserGroup(HttpServletRequest request,
			int pagesize, String pWhereClause) throws Exception {
		String hql = " from UserGroup as r " + pWhereClause;
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void delUserGroup(int groupId) throws Exception {
		String hql = "delete from UserGroup where id =:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", groupId);
		EntityManager.executeUpdateByHql(hql, map);
	}

	public void updUserGroup(UserGroup group) throws Exception {
		EntityManager.update(group); 
		
	}

	public List<Map<String, String>> getUserGroupAppByGroupId(String groupId) throws Exception {
		/*String hql = "from UserGroupApp where userGroupId = :userGroupId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userGroupId", groupId);
		return EntityManager.getAllByHql(hql, map);*/
		String hql = "select br.TAGSUBKEY appId,BR.TAGSUBVALUE appName,CASE WHEN UGA.APPID is not NULL THEN 1 else 0 end isChecked from BASE_RESOURCE br " +
				"LEFT JOIN USER_GROUP_APP uga on BR.TAGSUBKEY=UGA.APPID and UGA.USERGROUPID = ? " +
				"where BR.TAGNAME = ? order by br.TAGSUBKEY";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("userGroupId", groupId);
		map.put("tagName", "Application");
		return EntityManager.getAllMapListBySql(hql, map);
	}

	public int delUserGroupAppByGroupId(Integer groupId) throws Exception, Exception {
		String hql = "delete from UserGroupApp where userGroupId=:userGroupId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userGroupId", groupId);
		return EntityManager.executeUpdateByHql(hql, map);
	}
	
	public Set<Integer> getUserGroupAppSetByGroupId(Integer groupId) throws Exception {
		String hql = "select APPID from USER_GROUP_APP where USERGROUPID = ? ";
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("groupId", groupId);
		List<BigDecimal> list = EntityManager.getAllListBySql(hql, map);
		Set<Integer> set = new HashSet<Integer>();
		for(BigDecimal obj : list) {
			set.add(obj.intValue());
		}
		return set;
	}

}
