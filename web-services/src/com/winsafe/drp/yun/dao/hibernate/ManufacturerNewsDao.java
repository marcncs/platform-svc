package com.winsafe.drp.yun.dao.hibernate;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils; 
import org.springframework.stereotype.Repository;

import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.base.dao.impl.AbstractBaseDaoHibernate;
import com.winsafe.drp.dao.ManufacturerNews;
import com.winsafe.drp.metadata.EResourceViewsType;
import com.winsafe.drp.yun.dao.IManufacturerNewsDao;

@Repository
public class ManufacturerNewsDao extends AbstractBaseDaoHibernate<ManufacturerNews> 
		implements IManufacturerNewsDao {
	
	public ManufacturerNewsDao() {
		super(ManufacturerNews.class);
	}

	@Override
	public PageBean<ManufacturerNews> queryForPage(Integer manufacturerId, String keyword,
			int currentPage, int pageSize, String orderBy) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		StringBuffer hqlSb = new StringBuffer("FROM ManufacturerNews mn WHERE 1=1 ");
		/*if (manufacturerId != null) {
			hqlSb.append(" AND mn.manufacturerId=:manufacturerId");
			paramMap.put("manufacturerId", manufacturerId);
		}*/
		if (StringUtils.isNotBlank(keyword)) {
			hqlSb.append(" AND (mn.title LIKE :keyword OR mn.summary LIKE :keyword OR mn.content LIKE :keyword)");
			paramMap.put("keyword", "%" + keyword + "%");
		}
		if (StringUtils.isBlank(orderBy)) {
			hqlSb.append(" ORDER BY mn.lastModifyTime DESC");
		} else {
			hqlSb.append(orderBy);
		}
		return queryForPage(hqlSb.toString(), paramMap, currentPage, pageSize);
	}

	@Override
	public List<ManufacturerNews> getHotList(int maxSize) {
		String sql = "SELECT p.* FROM manufacturer_news AS p, resource_views AS rv WHERE p.is_deleted=0"
				+ " AND p.id=rv.resource_id AND rv.resource_type=" + EResourceViewsType.MANUFACTURER_NEWS.getId()
				+ " ORDER BY rv.views DESC";
		return super.sqlQueryForList(sql, maxSize);
	}
	
	@Override
	public PageBean<ManufacturerNews> queryLatestForPage(int currentPage, int pageSize, List<Integer> manufacturerIds) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer("FROM ManufacturerNews p WHERE 1=1");
		if (manufacturerIds == null || manufacturerIds.size() <= 0) {
			sb.append(" AND 1=0");
		} else {
			sb.append(" AND p.manufacturerId IN (:manufacturerIds)");
			paramMap.put("manufacturerIds", manufacturerIds);
		}
		sb.append(" ORDER BY p.id DESC");
		return queryForPage(sb.toString(), paramMap, currentPage, pageSize);
	}

}
