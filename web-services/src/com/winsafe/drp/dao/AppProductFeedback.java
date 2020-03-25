package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.mail.util.StringUtils;

public class AppProductFeedback {

	public List getProductFeedBack(HttpServletRequest request , int pagesize , int currentPage, String keyword , String productId, Integer manufacturerId) throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append("from ProductFeedback WHERE 1=1");
//		sql.append("SELECT * FROM product_feedback WHERE 1=1 ");
//		if (productId != null && productId != 0) {
//			sql.append(" AND productId = ").append(productId);
//		} else if (manufacturerId != null) {
//			sql.append(" AND productId IN (SELECT id FROM PopularProduct WHERE manufacturerId = ").append(manufacturerId).append(")");
//		}
		if (!StringUtils.isEmpty(keyword)) {
			sql.append(" AND (content LIKE '%").append(keyword).append("%' OR reply LIKE '%").append(keyword).append("%')");
		}
		sql.append(" AND isdeleted = 0");
		sql.append(" ORDER BY createtime DESC");
		return PageQuery.hbmQuery(request, sql.toString() , pagesize);
	}
	
	public ProductFeedback getProductFeedbackById(Integer id) throws Exception{
		String sql = "from ProductFeedback as pf where pf.id = '" + id + "'";
		return (ProductFeedback) EntityManager.find(sql);
	}
	
	public void updProductFeedBack(ProductFeedback p) throws Exception{
		EntityManager.update(p);
	}
	
	public void delProductFeedBackById(String id) throws Exception{
		String sql="delete from Product_Feedback pf where pf.id = '" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List getProductFeedback(HttpServletRequest request, int pagesize, String whereSql) throws Exception {
		String hql = " from ProductFeedback " + whereSql + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize); 
	}
}
