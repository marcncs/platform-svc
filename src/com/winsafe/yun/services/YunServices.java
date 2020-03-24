package com.winsafe.yun.services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;

import com.winsafe.drp.dao.MediaImage;
import com.winsafe.drp.dao.MediaImageGroup;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.ServiceExpType;
import com.winsafe.hbm.util.pager2.PageQuery;

public class YunServices {

	public List<Map<String,String>> getProduct(HttpServletRequest request, Integer pageSize, Integer currentPage) throws Exception {
		String sql = "select * from POPULAR_PRODUCT where AUDIT_STATUS = 1";
		return PageQuery.jdbcSqlserverQuery(request, "sort", sql, pageSize);
	}

	public List<Map<String, String>> getContact(HttpServletRequest request) throws Exception {
		String sql = "select * from MANUFACTURER_CONTACT order by id desc";
		return EntityManager.jdbcquery(sql);
	}

	public List<Map<String, String>> getProductDetail(String productId) throws Exception {
		String sql = "select * from POPULAR_PRODUCT where name=(select productname from Product where id='"+productId+"')";
		return EntityManager.jdbcquery(sql);
	}
	
	public List<Map<String, String>> getProductDetailById(String productId) throws Exception {
		String sql = "select * from POPULAR_PRODUCT where id='"+productId+"' order by id desc";
		return EntityManager.jdbcquery(sql);
	}

	public List<Map<String, String>> getAllFeedBacks(HttpServletRequest request, Integer pageSize, Integer currentPage) throws Exception {
		String sql = "select * from PRODUCT_FEEDBACK where AUDITSTATUS = 1";
		return PageQuery.jdbcSqlserverQuery(request, "id DESC", sql, pageSize);
	}

	public List<Map<String, String>> getFeedBack(HttpServletRequest request, Integer pageSize, Integer currentPage,
			String productId) throws Exception {
		String sql = "select * from PRODUCT_FEEDBACK where productid='"+productId+"' and AUDITSTATUS = 1";
		return EntityManager.jdbcquery(sql);
	}

	/** 获取产品系列的所有已审核评论 */
	public List<Map<String, String>> getProductSerialFeedBack(HttpServletRequest request, Integer pageSize, Integer currentPage,
			String productId) throws Exception {
		String sql = "select * from PRODUCT_FEEDBACK where AUDITSTATUS = 1"
				+ " and productname=(select DISTINCT PRODUCTNAME from PRODUCT where id='" + productId + "')";
		return EntityManager.jdbcquery(sql);
	}

	public List<Map<String, String>> getProductVideo(HttpServletRequest request, Integer pageSize,
			Integer currentPage, String productId) throws Exception {
		String sql = "select * from MEDIA_VIDEO where ispublish=1 ";
		return PageQuery.jdbcSqlserverQuery(request, "id desc", sql, pageSize);
	}

	public List<Map<String, String>> getProductVideoById(String videoId) throws Exception {
		String sql = "select * from MEDIA_VIDEO where id="+videoId;
		return EntityManager.jdbcquery(sql);
	}
	
	public void updProductVideoViewCount(String videoId) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("videoId", videoId);
		String sql = "update MEDIA_VIDEO set viewcount=viewcount+1 where id=?";
		EntityManager.executeUpdateBySql(sql, param);
	}
	
	public void updProductVideoCover(String videoId, String url) throws Exception {
		Map<String,Object> param = new LinkedHashMap<>();
		param.put("url", url);
	    param.put("videoId", videoId);
		String sql = "update MEDIA_VIDEO set imgurl=? where id=?";
		EntityManager.executeUpdateBySql(sql, param);
	}

	public List<Map<String, String>> getProductById(String productId) throws Exception {
		String sql = "select * from POPULAR_PRODUCT where id = '"+productId+"'";
		return EntityManager.jdbcquery(sql);
	}

	public List<Map<String, String>> getNews(HttpServletRequest request, Integer pageSize, Integer currentPage) throws Exception {
		String sql = "select * from MANUFACTURER_NEWS where IS_DELETED = 0 "; 
		return PageQuery.jdbcSqlserverQuery(request, "id desc", sql, pageSize);
	}

	public List<Map<String, String>> getNewsById(String newsId) throws Exception {
		String sql = "select * from MANUFACTURER_NEWS where id = "+newsId;
		return EntityManager.jdbcquery(sql);
	}
	
	public void updNewsViewCountById(String newsId) throws Exception {
		Map<String,Object> param = new HashMap<>();
	    param.put("newsId", newsId);
		String sql = "update MANUFACTURER_NEWS set viewcount=viewcount+1 where id=?";
		EntityManager.executeUpdateBySql(sql, param);
	}

	public void addFeedback(String productId, Integer score, String content, String picUrl, String productName) throws Exception {
		if (StringUtils.isBlank(content)) throw new Exception(ServiceExpType.InvalidParam.getDesc());
		if (score == null) score = 3;
		String sql = "INSERT INTO PRODUCT_FEEDBACK(ID,PRODUCTID,STARSCORE,CONTENT,PICURL,CREATETIME,AUDITSTATUS,ISREPLIED,ISDELETED,PRODUCTNAME) "
				+ "select PRODUCT_FEEDBACK_SEQ.nextval ,'"+productId+"',"+score+",'"+content+"','"+picUrl+"',SYSDATE,0,0,0,'"+productName+"' from dual";
		EntityManager.executeUpdate(sql);
		
	}

	public List<Map<String, String>> getMediaImage(HttpServletRequest request, Integer pageSize, Integer currentPage) throws Exception {
		String sql = "select * from MEDIA_IMAGE where is_deleted=0";
		return PageQuery.jdbcSqlserverQuery(request, "create_time desc", sql, pageSize);
	}

	public List<Map<String, String>> getMediaImageGroup() throws Exception {
		String sql = "select * from MEDIA_IMAGE_GROUP ";
		return EntityManager.jdbcquery(sql);
	}

	public void addMediaImage(String title, String url, Integer fsize, Integer groupId) throws Exception {
		String sql = "INSERT INTO MEDIA_IMAGE (ID, MANUFACTURER_ID, TITLE, URL, IMG_SIZE, GROUP_ID, CREATE_TIME, LAST_MODIFY_TIME, IS_DELETED, DELETE_TIME) "
				+ "select MEDIA_IMAGE_SEQ.nextval, '1', '"+title+"', '"+url+"', "+fsize+", "+groupId+", SYSDATE, NULL, '1', NULL from dual ";
		EntityManager.executeUpdate(sql);
	}

	public void updateContent(String productId, String content, String posterUrl) throws Exception {
		String sql = "update POPULAR_PRODUCT set poster_url='"+posterUrl+"',content='"+content+"' where id='"+productId+"'";
		EntityManager.executeUpdate(sql);
	}

	public MediaImageGroup getMediaImageGroup(int id) {
		String hql = "from MediaImageGroup where id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return (MediaImageGroup)EntityManager.find(hql, map);
	}

	public List<MediaImage> getMediaImage(Integer groupId, HttpServletRequest request, Integer pageSize,
			Integer currentPage) throws Exception {
		String hql = "from MediaImage where groupId="+groupId+" order by id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public void updateProductPic(String productId, String picUrl) throws Exception {
		String sql = "update POPULAR_PRODUCT set pic_url='"+picUrl+"' where id='"+productId+"'";
		EntityManager.executeUpdate(sql);
		
	}

	public String getProductName(String productId) throws HibernateException, SQLException {
		String sql = "select PRODUCTNAME from PRODUCT where id ='"+productId+"' UNION select NAME from POPULAR_PRODUCT where id ='"+productId+"'";
		List<Map<String,String>> proName = EntityManager.jdbcquery(sql);
		if(proName.size() > 0) {
			return proName.get(0).get("productname");
		}
		return null;
	}
}
