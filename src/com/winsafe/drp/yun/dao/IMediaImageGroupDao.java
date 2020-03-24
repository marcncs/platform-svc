package com.winsafe.drp.yun.dao;

import java.util.List;

import com.winsafe.drp.base.dao.IBaseDao; 
import com.winsafe.drp.dao.MediaImageGroup;

public interface IMediaImageGroupDao extends IBaseDao<MediaImageGroup> {
	
	public Integer save(MediaImageGroup group);
	
	public MediaImageGroup get(int id);
	
	public MediaImageGroup getByName(String name, int manufacturerId);
	
	public List<MediaImageGroup> getByManufacturerId(int manufacturerId);
	
	public void delete(int id);
}
