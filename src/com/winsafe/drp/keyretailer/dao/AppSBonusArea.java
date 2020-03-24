package com.winsafe.drp.keyretailer.dao;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.hibernate.HibernateException;

import com.winsafe.drp.dao.RegionItemInfo;
import com.winsafe.drp.entity.EntityManager;
public class AppSBonusArea {
	
	public Set<String> getAreaIdSet(String areaIds) throws HibernateException, SQLException {
		String sql = "select areaId from S_BONUS_AREA where areaId in ("+areaIds+")";
		List<Map<String,String>> idList = EntityManager.jdbcquery(sql);
		Set<String> idSet = new HashSet<String>();
		for(Map<String,String> map : idList) {
			idSet.add(map.get("areaid"));
		}
		return idSet;
	}

	public List getChild(String parentid) {
		String sql = "from RegionItemInfo where pId =" + parentid+" order by name";
		return EntityManager.getAllByHql(sql);
	}
	
	public boolean hasChild(int parentid) {
		String sql = "select count(id) from S_BONUS_AREA where PARENTID ="+parentid;
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public RegionItemInfo getSBonusAreaById(String regionId) { 
		String sql = "from RegionItemInfo where id =" + regionId;
		return (RegionItemInfo) EntityManager.find(sql);
	}
}

