package com.winsafe.yun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.dao.PopularProduct;
import com.winsafe.drp.metadata.EAuditStatus;
import com.winsafe.drp.metadata.EListedStatus;
import com.winsafe.drp.yun.dao.hibernate.ProductDao;
import com.winsafe.drp.yun.service.IProductService;

@Service
public class ProductService implements IProductService {
	@Autowired
	private ProductDao productDao = new ProductDao();

	@Override
	public PopularProduct getById(int productId) {
		return productDao.get(productId);
	}

	@Override
	public PageBean<PopularProduct> getPublic(int currentPage, int pageSize, Integer manufacturerId, Integer group1Id, Integer group2Id) {
		EListedStatus[] listedStatusArray = { EListedStatus.LISTED };
		EAuditStatus[] auditStatusArray = { EAuditStatus.PASSED };
		String orderBy = " ORDER BY p.rank DESC, p.id ASC";
		return productDao.queryForPage(currentPage, pageSize, manufacturerId, null, group1Id, group2Id, listedStatusArray, auditStatusArray,
				orderBy);
	}

	@Override
	public PageBean<PopularProduct> getByPage(int currentPage, int pageSize, int manufacturerId) {
		String orderBy = " ORDER BY p.rank DESC, p.id ASC";
		return productDao.queryForPage(currentPage, pageSize, manufacturerId, null, null, null, null, null, orderBy);
	}
	
}
