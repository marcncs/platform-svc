package com.winsafe.drp.dao;

import java.util.List;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest; 

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppMediaImg {
	
	public List getMeidaImg(HttpServletRequest request , int pagesize ) throws Exception{
		String hql = "from MediaImage order by createTime desc";
		return PageQuery.hbmQuery(request, hql , pagesize);
	}
	
	public MediaImage getMediaImageByHash(String hash) throws Exception{
		String hql = "from MediaImage mi where mi.hash ='"+hash+"'";
		return (MediaImage) EntityManager.find(hql);
	}
	
	public void addMediaImage(MediaImage mi) throws Exception{
		EntityManager.save(mi);
	}
	
	public void updMeidaImage(MediaImage mi) throws Exception{
		EntityManager.update(mi);
	}
	
	public void delMediaImageById(String miid)throws Exception{
		String sql = "delete from media_image mi where mi.id='" + miid + "'";
		EntityManager.updateOrdelete(sql);
	}
}
