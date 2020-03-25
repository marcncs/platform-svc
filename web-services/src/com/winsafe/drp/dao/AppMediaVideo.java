package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.actions.DownloadAction.StreamInfo;

import com.winsafe.drp.action.warehouse.excPutPickTakeBillAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppMediaVideo {

	public List getMediaVideo(HttpServletRequest request , int pagesize , int currentPage) throws Exception{
		String sql = "from MediaVideo m order by m.createTime desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public void addMediaVideo(MediaVideo mv) throws Exception{
		EntityManager.save(mv);
	}
	
	public MediaVideo getMediaVideoById(String mvid) throws Exception{
		String sql = "from MediaVideo m where m.id='"+mvid+"'";
		return (MediaVideo) EntityManager.find(sql);
	}
	
	public void updMediaVideoById(MediaVideo mv) throws Exception{
		EntityManager.update(mv);
	}
	
	public void delMediaVideoById(String mvid) throws Exception{
		String sql = "delete from media_video mv where mv.id = '" +mvid+ "'";
		EntityManager.updateOrdelete(sql);
	}

	public MediaVideo getVideoByHash(String hash) { 
		String sql = "from MediaVideo m where m.hash='"+hash+"'";
		return (MediaVideo) EntityManager.find(sql);
	}

	public MediaVideo getVideoByImageHash(String hash) {
		String sql = "from MediaVideo m where m.imageHash='"+hash+"'";
		return (MediaVideo) EntityManager.find(sql);
	}
}
