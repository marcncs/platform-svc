package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppEquip {
	public List<Equip> getEquip(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String hql = "from Equip as e "+ pWhereClause + " order by e.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void addEquip(Equip c) throws Exception {		
		EntityManager.save(c);		
	}
	public void updEquip(Equip c) throws Exception {		
		EntityManager.update(c);		
	}

	public void delEquip(String id) throws Exception {		
		String sql = "delete from Equip where id='" + id+"'";
		EntityManager.updateOrdelete(sql);		
	}

	public Equip getEquipByID(String id) throws Exception {
		String sql = " from Equip as e where e.id='" + id+"'";
		return (Equip) EntityManager.find(sql);
	}

	public List<Equip> getEquip(String whereSql) {
		String hql = "from Equip as e "+ whereSql + " order by e.id desc";
		return EntityManager.getAllByHql(hql);
	}
	

}
