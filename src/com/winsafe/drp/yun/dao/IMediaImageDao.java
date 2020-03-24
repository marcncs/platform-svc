package com.winsafe.drp.yun.dao;

import com.winsafe.drp.base.dao.IBaseDao;
import com.winsafe.drp.base.dao.db.PageBean; 
import com.winsafe.drp.dao.MediaImage;

public interface IMediaImageDao extends IBaseDao<MediaImage>{
	public Integer save(MediaImage image);
	
	public PageBean<MediaImage> queryForPage(int manufacturerId, Integer groupId, int currentPage, int pageSize);
	
	public void moveAll(int groupId, int targetGroupId) throws Exception;
	
	public int getImageCount(int manufacturerId, int groupId);
	
	public MediaImage getByHash(String hash);
}
