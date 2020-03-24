package com.winsafe.drp.yun.dao.hibernate;

import java.util.HashMap;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository; 

import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.base.dao.impl.AbstractBaseDaoHibernate;
import com.winsafe.drp.dao.PopularProduct;
import com.winsafe.drp.metadata.EAuditStatus;
import com.winsafe.drp.metadata.EListedStatus;


@Repository
public class ProductDao extends AbstractBaseDaoHibernate<PopularProduct> {

	public ProductDao(Class<PopularProduct> clazz) {
		super(clazz);
	}
	public ProductDao() {
		super(null);
	}

	public PageBean<PopularProduct> queryForPage(int currentPage, int pageSize, Integer manufacturerId, String keyword, 
			Integer group1Id, Integer group2Id, EListedStatus[] listedStatus, EAuditStatus[] auditStatusArray, String orderBy) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer("FROM PopularProduct p WHERE 1=1");
		if (manufacturerId != null) {
			sb.append(" AND p.manufacturerId = :manufacturerId");
			paramMap.put("manufacturerId", manufacturerId);
		}
		if (StringUtils.isNotBlank(keyword)) {
			sb.append(" AND (p.name like :keyword OR p.component like :keyword OR p.certification like :keyword)");
			paramMap.put("keyword", "%" + keyword + "%");
		}
		if (group1Id != null) {
			sb.append(" AND p.group1Id = :group1Id");
			paramMap.put("group1Id", group1Id);
		}
		if (group2Id != null) {
			sb.append(" AND p.group2Id = :group2Id");
			paramMap.put("group2Id", group2Id);
		}
		if (listedStatus != null && listedStatus.length > 0) {
			sb.append(" AND (");
			int i=0;
			for (EListedStatus status : listedStatus) {
				if (i > 0) {
					sb.append(" OR ");
				}
				sb.append(" p.listedStatus = :listedStatus").append(++i);
				paramMap.put("listedStatus" + i, status);
			}
			sb.append(")");
		}
		if (auditStatusArray != null && auditStatusArray.length > 0) {
			sb.append(" AND (");
			int i=0;
			for (EAuditStatus status : auditStatusArray) {
				if (i > 0) {
					sb.append(" OR ");
				}
				sb.append(" p.auditStatus = :auditStatus").append(++i);
				paramMap.put("auditStatus" + i, status);
			}
			sb.append(")");
		}
		if (StringUtils.isBlank(orderBy)) {
			sb.append(" ORDER BY p.id DESC");
		} else {
			sb.append(orderBy);
		}
		return queryForPage(sb.toString(), paramMap, currentPage, pageSize);
	}
	
}
