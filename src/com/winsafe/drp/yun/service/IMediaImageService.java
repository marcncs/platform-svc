package com.winsafe.drp.yun.service;

import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.dao.MediaImage;

public interface IMediaImageService { 
	public MediaImage addImage(int manufacturerId, String title, String url, Integer fsize, Integer groupId);
	
	public MediaImage updateImage(int id, String title);
	
	public void moveImages(int[] ids, int newGroupId);
	
	public void moveImages(String ids, int newGroupId);
	
	public void deleteImages(int[] ids);
	
	public void deleteImages(String ids);
	
	public MediaImage get(int id);
	
	public PageBean<MediaImage> queryForPage(int manufacturerId, Integer groupId, int currentPage, int pageSize);
	
	public MediaImage addImage(MediaImage image);
	
	public MediaImage getByHash(String hash);
}
