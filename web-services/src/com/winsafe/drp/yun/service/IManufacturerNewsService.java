package com.winsafe.drp.yun.service;

import java.util.List;

import com.winsafe.drp.base.dao.db.PageBean; 
import com.winsafe.drp.dao.ManufacturerNews;

public interface IManufacturerNewsService {
	
	public ManufacturerNews getById(int id);
	
	public PageBean<ManufacturerNews> getPage(int manufacturerId, 
			Integer currentPage, Integer pageSize);
	
	public ManufacturerNews addNews(int manufacturerId, String title, String summary,
			String content, String picUrl, Integer isShowPic);
	
	public ManufacturerNews updateNews(int newsId, String title, String summary,
			String content, String picUrl, Integer isShowPic);
	
	public ManufacturerNews updateNewsPic(int newsId, String picUrl, Integer isShowPic);
	
	public void deleteNews(int newsId);
	
	public PageBean<ManufacturerNews> getLatest(int currentPage, int pageSize, Integer manufacturerId);
	/** 获取热门新闻列表 */
	public List<ManufacturerNews> getHotNews(Integer maxSize);
	/** 关注企业的最新新闻 */
	public PageBean<ManufacturerNews> getFavoriteLatestNews(int currentPage, int pageSize, int individualId);
	
	public PageBean<ManufacturerNews> search(String keyword, int currentPage, int pageSize);
}
