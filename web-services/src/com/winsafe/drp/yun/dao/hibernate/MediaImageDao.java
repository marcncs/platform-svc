package com.winsafe.drp.yun.dao.hibernate;

import java.util.Date;
import java.util.HashMap; 


import org.springframework.stereotype.Repository;

import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.base.dao.impl.AbstractBaseDaoHibernate;
import com.winsafe.drp.dao.MediaImage;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.yun.dao.IMediaImageDao;
import com.winsafe.sap.metadata.YesOrNo;


@Repository
public class MediaImageDao extends AbstractBaseDaoHibernate<MediaImage> implements
		IMediaImageDao {
	
//	@Resource(name = "jdbcDao4main")
//	private IJdbcDao jdbcDao4main;
	
	public MediaImageDao() {
		super(MediaImage.class);
	}

	@Override
	public Integer save(MediaImage image) {
		if (image == null) return null;
		
		if (image.getCreateTime() == null) {
			image.setCreateTime(new Date());
		}
		if (image.getLastModifyTime() == null) {
			image.setLastModifyTime(new Date());
		}
		if (image.getIsDeleted() == null) {
			image.setIsDeleted(YesOrNo.NO.getValue());
		}
		if (image.getSize() == null) {
			image.setSize(0l);
		}
		
		return (Integer) super.save(image);
	}

	@Override
	public PageBean<MediaImage> queryForPage(int manufacturerId, Integer groupId, int currentPage, int pageSize) {
		StringBuffer sbHql = new StringBuffer("FROM MediaImage m WHERE 1=1 and isDeleted = 0 ");
//		sbHql.append(" AND manufacturerId = :manufacturerId");
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("manufacturerId", manufacturerId);
		/*if (groupId != null) {
			sbHql.append(" AND groupId = :groupId");
			paramMap.put("groupId", groupId);
		}*/
		sbHql.append(" ORDER BY lastModifyTime DESC");
		
		return super.queryForPage(sbHql.toString(), paramMap, currentPage, pageSize);
	}

	@Override
	public void moveAll(int oldGroupId, int targetGroupId) throws Exception {
		String sql = "UPDATE media_image SET group_id = " + targetGroupId + " WHERE group_id = " + oldGroupId;
		EntityManager.executeUpdate(sql);
//		jdbcDao4main.execute(sql);
	}

	@Override
	public int getImageCount(int manufacturerId, int groupId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(1) FROM media_image WHERE manufacturer_id = ").append(manufacturerId)
			.append(" AND group_id = ").append(groupId).append(" AND is_deleted = 0");
		try {
			return EntityManager.getRecordCountBySql(sql.toString());
//			return jdbcDao4main.queryForInt(sql.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public MediaImage getByHash(String hash) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		String hql ="from MediaImage where hash=:hash";
		paramMap.put("hash", hash);
		return super.queryForObject(hql, paramMap);
	}

}
