package com.winsafe.drp.yun.service;

import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.dao.PopularProduct;


public interface IProductService { 

	public PopularProduct getById(int productId);

	/** 某个厂家可公开的产品列表，状态为：已上架，未审核或审核通过 */
	public PageBean<PopularProduct> getPublic(int currentPage, int pageSize, Integer manufacturerId, Integer group1Id, Integer group2Id);
	/** 某个厂家的所有产品列表 */
	public PageBean<PopularProduct> getByPage(int currentPage, int pageSize, int manufacturerId);
	
}
