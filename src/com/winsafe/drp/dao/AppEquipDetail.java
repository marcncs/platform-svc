package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppEquipDetail {
	public List getEquipDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from EquipDetail as c "
				+ pWhereClause + " order by c.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}

	public void addEquipDetail(EquipDetail c) throws Exception {
		
		EntityManager.save(c);
		
	}

	public void delEquipDetail(String id) throws Exception {
		
		String sql = "delete from equip_detail where id='" + id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void delEquipDetailByEID(String id) throws Exception{
		
		String sql = "delete from equip_detail where eid='" + id+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	public EquipDetail getEquipDetailByID(String id) throws Exception {
		EquipDetail p = null;
		String sql = " from EquipDetail as p where p.id='" + id+"'";
		p = (EquipDetail) EntityManager.find(sql);
		return p;
	}
	
	
	public List getEquipDetailByEID(String id) throws Exception {
		List pls = null;
		String sql = " from EquipDetail as p where p.eid='" + id+"'";
		pls = EntityManager.getAllByHql(sql);
		return pls;
	}
	public void updEquipDetail(EquipDetail c) throws Exception {
		
		EntityManager.update(c);
		
	}
}
