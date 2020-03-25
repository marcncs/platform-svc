package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SUserArea;
import com.winsafe.hbm.entity.HibernateUtil;

public class AppUserArea {
	public List<SUserArea> getByUserId(Integer userId) {
		String sql = "select new SUserArea(s.id,s.areaId, u.loginname, u.realname, r.name) from Users u, SUserArea s, RegionItemInfo r " +
				"where u.userid=s.userId and s.areaId=r.areaId and u.userid="+userId;
		return EntityManager.getAllByHql(sql);
	}
	
	public List<SUserArea> query(SUserArea info) {
		String sql = "from SUserArea s where 1=1";
		if (info == null) {
			return EntityManager.getAllByHql(sql);
		} else {
			if (info.getUserId() != null) {
				sql += " and s.userId=" + info.getUserId();
			}
			if (info.getAreaId() != null) {
				sql += " and s.areaId=" + info.getAreaId();
			}
			return EntityManager.getAllByHql(sql);
		}
	}
	
	public void insert(SUserArea info) {
		List<SUserArea> list = this.query(info);
		if (list==null || list.isEmpty()) {			
			EntityManager.save(info);
		}
	}
	
	public void delete(SUserArea info) {
//		EntityManager.delete(info);
		HibernateUtil.currentSession(true).
		createQuery("delete from SUserArea where id=?").
		setParameter(0, info.getId()).executeUpdate();
	}
}
