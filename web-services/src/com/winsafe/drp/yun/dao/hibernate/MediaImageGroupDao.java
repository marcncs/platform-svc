package com.winsafe.drp.yun.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository; 

import com.winsafe.drp.base.dao.impl.AbstractBaseDaoHibernate;
import com.winsafe.drp.dao.MediaImageGroup;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.drp.yun.dao.IMediaImageGroupDao;

@Repository
public class MediaImageGroupDao extends AbstractBaseDaoHibernate<MediaImageGroup> implements
		IMediaImageGroupDao {
	
//	@Resource(name = "jdbcDao4main")
//	private IJdbcDao jdbcDao4main;
	
	public MediaImageGroupDao() {
		super(MediaImageGroup.class);
	}

	@Override
	public Integer save(MediaImageGroup group) {
		if (group == null) return null;
		
		if (group.getCreateTime() == null) {
			group.setCreateTime(new Date());
		}
		if (group.getLastModifyTime() == null) {
			group.setLastModifyTime(new Date());
		}
		
		return (Integer) super.save(group);
	}

	@Override
	public MediaImageGroup get(int id) {
		String sql = "SELECT * FROM media_image_group WHERE id =" + id;
//		Map<String, Object> groupMap = jdbcDao4main.queryOneForMultiFields(sql);
		return getMediaImageGroupBySql(sql);
		
//		return map2Object(groupMap);
	}

	@Override
	public List<MediaImageGroup> getByManufacturerId(int manufacturerId) {
		String sql = "SELECT * FROM media_image_group WHERE manufacturer_id =" + manufacturerId;
//		List<Map<String, Object>> groupMaps = jdbcDao4main.queryListForMultiFields(sql);
		List<Map<String, String>> groupMaps;
		try {
			groupMaps = EntityManager.jdbcquery(sql);
		} catch (Exception e) {
			WfLogger.error("", e);
			return null;
		}
		if (groupMaps != null && !groupMaps.isEmpty()) {
			List<MediaImageGroup> groups = new ArrayList<MediaImageGroup>();
			for (Map<String, String> map : groupMaps) {
				groups.add(map2Object(map));
			}
			
			return groups;
		}
		
		return null;
	}

	private MediaImageGroup map2Object(Map<String, String> groupMap) {
		MediaImageGroup group = new MediaImageGroup();
		try {
			MapUtil.mapToObject(groupMap, group);
		} catch (Exception e) {
			WfLogger.error("", e);
			return null;
		}
		return group;
	}

	/*private MediaImageGroup map2Object(Map<String, Object> map) {
		if (map == null || map.isEmpty()) return null;
		
		MediaImageGroup group = new MediaImageGroup();
		group.setId((Integer) map.get("id"));
		group.setCreateTime((Date) map.get("create_time"));
		group.setLastModifyTime((Date) map.get("last_modify_time"));
		group.setManufacturerId((Integer) map.get("manufacturer_id"));
		group.setName((String) map.get("name"));
		
		return group;
	}*/

	@Override
	public MediaImageGroup getByName(String name, int manufacturerId) {
		String sql = "SELECT * FROM media_image_group WHERE name ='" + name + 
				"' AND manufacturer_id = " + manufacturerId + " limit 1";
		return getMediaImageGroupBySql(sql);
	}

	private MediaImageGroup getMediaImageGroupBySql(String sql) {
		try {
			List<Map<String, String>> groupMap =  EntityManager.jdbcquery(sql);
			if(groupMap.size() > 0) {
				return map2Object(groupMap.get(0));
			} else {
				return null;
			}
		} catch (Exception e) {
			WfLogger.error("",e);
			return null;
		}
	}

	@Override
	public void delete(int id) {
		String sql = "delete from media_image_group where id = " + id;
		try {
			EntityManager.executeUpdate(sql);
		} catch (Exception e) {
			WfLogger.error("", e);
		}
//		jdbcDao4main.execute(sql);
	}
}
