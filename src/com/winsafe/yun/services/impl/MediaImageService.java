package com.winsafe.yun.services.impl;

import java.util.Date; 

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.control.expection.ServiceException;
import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.dao.MediaImage;
import com.winsafe.drp.metadata.ServiceExpType;
import com.winsafe.drp.yun.dao.IMediaImageDao;
import com.winsafe.drp.yun.dao.hibernate.MediaImageDao;
import com.winsafe.drp.yun.service.IMediaImageService;

@Service
public class MediaImageService implements IMediaImageService {
	
	@Autowired
	private IMediaImageDao imageDao = new MediaImageDao();

	@Override
	public MediaImage addImage(int manufacturerId, String title, String url, Integer fsize, Integer groupId) {
		if (StringUtils.isBlank(title)) throw new NullPointerException("图片标题为空");
		if (StringUtils.isBlank(url)) throw new NullPointerException("图片地址为空");
		
		MediaImage image = new MediaImage();
		image.setCreateTime(new Date());
		image.setGroupId(groupId == null ? 0 : groupId);
		image.setLastModifyTime(new Date());
		image.setManufacturerId(manufacturerId);
		image.setTitle(title);
		image.setUrl(url);
		image.setSize(Long.valueOf(fsize));
		imageDao.save(image);
		
		return image;
	}

	@Override
	public MediaImage updateImage(int id, String title) {
		if (StringUtils.isBlank(title)) throw new NullPointerException("图片标题为空");
		
		MediaImage image = imageDao.get(id);
		if (image == null) throw new ServiceException(ServiceExpType.ResourceNotExisted);
		
		image.setLastModifyTime(new Date());
		image.setTitle(title);
		imageDao.update(image);
		
		return image;
	}

	@Override
	public void moveImages(int[] ids, int newGroupId) {
		if (ids == null) throw new NullPointerException("没有选择图片");
		
		for (int id : ids) {
			MediaImage image = imageDao.get(id);
			if (image != null) {
				image.setGroupId(newGroupId);
				image.setLastModifyTime(new Date());
				imageDao.update(image);
			}
		}
	}
	
	@Override
	public void moveImages(String ids, int newGroupId) {
		if (StringUtils.isBlank(ids)) throw new NullPointerException("没有选择图片");
		
		String[] idsStrArray = ids.split(";");
		int[] idsIntArray = new int[idsStrArray.length];
		for (int i=0; i<idsStrArray.length; i++) {
			idsIntArray[i] = Integer.valueOf(idsStrArray[i]);
		}
		
		moveImages(idsIntArray, newGroupId);
	}

	@Override
	public void deleteImages(int[] ids) {
		if (ids == null) throw new NullPointerException("没有选择图片");
		
		for (int id : ids) {
			MediaImage image = imageDao.get(id);
			if (image != null) {
				imageDao.delete(id);
			}
		}
	}
	
	@Override
	public void deleteImages(String ids) {
		if (StringUtils.isBlank(ids)) throw new NullPointerException("没有选择图片");
		
		String[] idsStrArray = ids.split(";");
		int[] idsIntArray = new int[idsStrArray.length];
		for (int i=0; i<idsStrArray.length; i++) {
			idsIntArray[i] = Integer.valueOf(idsStrArray[i]);
		}
		
		deleteImages(idsIntArray);
	}

	@Override
	public PageBean<MediaImage> queryForPage(int manufacturerId, Integer groupId, int currentPage, int pageSize) {
		if (groupId == null || groupId.intValue() <  0) groupId = 0;
		
		return imageDao.queryForPage(manufacturerId, groupId, currentPage, pageSize);
	}

	@Override
	public MediaImage get(int id) {
		return imageDao.get(id);
	}

	@Override
	public MediaImage addImage(MediaImage image) {
		imageDao.save(image);
		return image;
	}

	@Override
	public MediaImage getByHash(String hash) {
		return imageDao.getByHash(hash);
	}
}
