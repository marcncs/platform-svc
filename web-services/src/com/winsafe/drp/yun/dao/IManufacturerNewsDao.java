package com.winsafe.drp.yun.dao;

import java.util.List; 

import com.winsafe.drp.base.dao.IBaseDao;
import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.dao.ManufacturerNews;

public interface IManufacturerNewsDao extends IBaseDao<ManufacturerNews> {
	
	public PageBean<ManufacturerNews> queryForPage(Integer manufacturerId, String keyword,
			int currentPage, int pageSize, String orderBy);
	/** 获取热门新闻列表 */
	public List<ManufacturerNews> getHotList(int maxSize);
	/** 获取多个企业的最新新闻 */
	public PageBean<ManufacturerNews> queryLatestForPage(int currentPage, int pageSize, List<Integer> manufacturerIds);
}
