package com.winsafe.drp.yun.service;

import java.util.List;

import com.winsafe.drp.dao.MediaImageGroup; 

public interface IMediaImageGroupService {
	public MediaImageGroup addGroup(int manufacturerId, String name);
	
	public MediaImageGroup updateGroup(int id, String name);
	
	public void deleteGroup(int id);
	
	public MediaImageGroup get(int id);
	
	public List<MediaImageGroup> getGroups(int manufacturerId);
}
