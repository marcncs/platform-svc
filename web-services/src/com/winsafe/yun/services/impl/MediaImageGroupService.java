package com.winsafe.yun.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List; 

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.control.expection.ServiceException;
import com.winsafe.drp.dao.MediaImageGroup;
import com.winsafe.drp.metadata.ServiceExpType;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.drp.yun.dao.IMediaImageDao;
import com.winsafe.drp.yun.dao.IMediaImageGroupDao;
import com.winsafe.drp.yun.dao.hibernate.MediaImageDao;
import com.winsafe.drp.yun.dao.hibernate.MediaImageGroupDao;
import com.winsafe.drp.yun.service.IMediaImageGroupService;


@Service
public class MediaImageGroupService implements IMediaImageGroupService {
	
	@Autowired
	private IMediaImageGroupDao groupDao = new MediaImageGroupDao();
	@Autowired
	private IMediaImageDao imageDao = new MediaImageDao();
	
	@Override
	public MediaImageGroup addGroup(int manufacturerId, String name) {
		if (StringUtils.isBlank(name)) throw new NullPointerException("分组名称为空");
		
		MediaImageGroup group = groupDao.getByName(name, manufacturerId);
		if (group != null) throw new ServiceException(ServiceExpType.ResourceExisted);
		
		group = new MediaImageGroup(name);
		group.setManufacturerId(manufacturerId);
		groupDao.save(group);
		
		return group;
	}

	@Override
	public MediaImageGroup updateGroup(int id, String name) {
		if (StringUtils.isBlank(name)) throw new NullPointerException("分组名称为空");
		
		MediaImageGroup group = groupDao.get(id);
		if (group == null) throw new ServiceException(ServiceExpType.ResourceNotExisted);
		if (name.equals(group.getName())) return group;
		
		MediaImageGroup group2 = groupDao.getByName(name, group.getManufacturerId());
		if (group2 != null) throw new ServiceException(ServiceExpType.ResourceExisted);
		
		group.setName(name);
		group.setLastModifyTime(new Date());
		groupDao.update(group);
		
		return group;
	}

	@Override
	public void deleteGroup(int id) {
		groupDao.delete(id);
		//删除分组，还要把分组中的图片移到“未分组”
		try {
			imageDao.moveAll(id, 0);
		} catch (Exception e) {
			WfLogger.error("deleteGroup", e);
		}
		
	}

	@Override
	public MediaImageGroup get(int id) {
		MediaImageGroup group = groupDao.get(id);
		if (group == null) {
			group = new MediaImageGroup();
			group.setId(0);
			group.setName("未分组");
		}
		return group;
	}

	@Override
	public List<MediaImageGroup> getGroups(int manufacturerId) {
		List<MediaImageGroup> groups = groupDao.getByManufacturerId(manufacturerId);
		if (groups == null) groups = new ArrayList<MediaImageGroup>();
		MediaImageGroup group = new  MediaImageGroup("未分组");
		group.setManufacturerId(manufacturerId);
		group.setId(0);
		
		groups.add(0, group);
		
		//获取分组中图片数量
		for (MediaImageGroup g : groups) {
			int count = imageDao.getImageCount(manufacturerId, g.getId());
			g.setCount(count);
		}
		
		return groups;
	}

}
