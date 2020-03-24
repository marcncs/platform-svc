package com.winsafe.yun.services.impl;

import java.util.Date;
import java.util.List; 

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.control.expection.ServiceException;
import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.dao.ManufacturerNews;
import com.winsafe.drp.metadata.ServiceExpType;
import com.winsafe.drp.yun.dao.IManufacturerNewsDao;
import com.winsafe.drp.yun.dao.hibernate.ManufacturerNewsDao;
import com.winsafe.drp.yun.service.IManufacturerNewsService;

@Service
public class ManufacturerNewsService implements IManufacturerNewsService {
	@Autowired
	private IManufacturerNewsDao manufacturerNewDao = new ManufacturerNewsDao();
//	@Autowired
//	private IIndividualProductRelationDao individualProductRelationDao;
	
	@Override
	public ManufacturerNews getById(int id) {
		return manufacturerNewDao.get(id);
	}

	@Override
	public PageBean<ManufacturerNews> getPage(int manufacturerId,
			Integer currentPage, Integer pageSize) {
		if (currentPage == null) currentPage = 1;
		if (pageSize == null) pageSize = 12;
		
		return manufacturerNewDao.queryForPage(manufacturerId, null, currentPage, pageSize, null);
	}

	@Override
	public ManufacturerNews addNews(int manufacturerId, String title,
			String summary, String content, String picUrl, Integer isShowPic) {
		if (StringUtils.isBlank(title) || StringUtils.isBlank(summary)
				|| StringUtils.isBlank(content) || StringUtils.isBlank(picUrl)) 
			throw new ServiceException(ServiceExpType.InvalidParam); 
		
		ManufacturerNews manuNews = new ManufacturerNews();
		manuNews.setContent(content);
//		manuNews.setContent(TextUtil.filterHtmlElementA(content));
		manuNews.setManufacturerId(manufacturerId);
		manuNews.setPicUrl(picUrl);
		manuNews.setIsShowPic(isShowPic);
		manuNews.setPublishTime(new Date());
		manuNews.setSummary(summary);
		manuNews.setTitle(title);
		manuNews.setIsDeleted(0);
		manuNews.setViewCount(0);

		manuNews.setLastModifyTime(new Date());
		manufacturerNewDao.save(manuNews);
		
		return manuNews;
	}

	@Override
	public ManufacturerNews updateNews(int newsId, String title, String summary,
			String content, String picUrl, Integer isShowPic) {
		if (StringUtils.isBlank(title) || StringUtils.isBlank(summary)
				|| StringUtils.isBlank(content)) 
			throw new ServiceException(ServiceExpType.InvalidParam); 
		
		ManufacturerNews manuNews = manufacturerNewDao.get(newsId);
		if (manuNews == null) 
			throw new ServiceException(ServiceExpType.ResourceNotExisted);
		manuNews.setLastModifyTime(new Date());
		manuNews.setContent(content);
//		manuNews.setContent(TextUtil.filterHtmlElementA(content));
		manuNews.setTitle(title);
		manuNews.setSummary(summary);
		if (StringUtils.isNotBlank(picUrl)) {
			manuNews.setPicUrl(picUrl);
		}
		manuNews.setIsShowPic(isShowPic);

		manufacturerNewDao.update(manuNews);
		
		return manuNews;
	}

	@Override
	public void deleteNews(int newsId) {
		manufacturerNewDao.delete(newsId);
	}

	@Override
	public ManufacturerNews updateNewsPic(int newsId, String picUrl, Integer isShowPic) {
		if (StringUtils.isBlank(picUrl)) throw new ServiceException(ServiceExpType.InvalidParam);
		
		ManufacturerNews news = manufacturerNewDao.get(newsId);
		if (news == null) throw new ServiceException(ServiceExpType.ResourceNotExisted);
		
		news.setPicUrl(picUrl);
		news.setIsShowPic(isShowPic);
		news.setLastModifyTime(new Date());
		manufacturerNewDao.update(news);
		
		return news;
	}

	@Override
	public PageBean<ManufacturerNews> getLatest(int currentPage, int pageSize,
			Integer manufacturerId) {
		String orderBy = " ORDER BY mn.publishTime DESC";
		return manufacturerNewDao.queryForPage(manufacturerId, null, currentPage, pageSize, orderBy);
	}

	@Override
	public PageBean<ManufacturerNews> search(String keyword, int currentPage, int pageSize) {
		String orderBy = " ORDER BY mn.publishTime DESC";
		return manufacturerNewDao.queryForPage(null, keyword, currentPage, pageSize, orderBy);
	}

	@Override
	public List<ManufacturerNews> getHotNews(Integer maxSize) {
		if (maxSize == null || maxSize.intValue() < 0) {
			maxSize = 20;
		}
		return manufacturerNewDao.getHotList(maxSize);
	}

	@Override
	public PageBean<ManufacturerNews> getFavoriteLatestNews(int currentPage, int pageSize, int individualId) {
//		List<Integer> manufacturerIds = individualProductRelationDao.getFavoriteManufacturerIds(individualId);
//		return manufacturerNewDao.queryLatestForPage(currentPage, pageSize, manufacturerIds);
		return null;
	}
	
}
