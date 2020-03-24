package com.winsafe.drp.dao;

import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppTrackApply {
	/**
	 * 通过whereSql语句来查询采集器
	 * @author jason.huang
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */
	
	public List<TrackApply> findByWhereSql(String whereSql) throws Exception {
		String sql = " from TrackApply as t " + whereSql + " order by t.id desc ";
		return EntityManager.findByWhereSql(sql);
	}
	
	/**
	 * @author jason.huang
	 * @param param 
	 * @param s whereSql
	 * 按照一定条件获取采集器信息
	 */
	public List selectTrackApply(HttpServletRequest request, int pageSize,
			String whereSql, Map<String, Object> param) throws Exception {
		String hql = " from TrackApply t " + whereSql + " order by t.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize, param);
		}
	
	
	/**
	 * 获取TrackApply表中最大ID+1
	 * 
	 * @author jason.huang
	 * @return int
	 */
	public int getMaxTrackApplyId() {
		String sql = "SELECT MAX(id) + 1 FROM TrackApply";
		return EntityManager.getRecordCount(sql);
	}
	
	/**
	 * 增加TrackApply
	 * 
	 * @author jason.huang
	 * @return 
	 */
	public void addTrackApply(Object s) {
		EntityManager.save(s);
	}
	
	
	
	
	public TrackApply getTrackapplyByIdcodeAndOrg(String idcode,String org) throws Exception {
		String sql = " from TrackApply as t  where  t.idCode='"+idcode+"' and  t.applyOrgId='"+org+"' order by t.id desc ";
		return  (TrackApply)EntityManager.find(sql);
	}

}
