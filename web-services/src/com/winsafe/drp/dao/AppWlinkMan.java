package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppWlinkMan {

	public List searchWlinkman(HttpServletRequest request, String pWhereClause)
			throws Exception {
		String hql = " from Wlinkman  " + pWhereClause + " order by makedate desc";
		return PageQuery.hbmQuery(request, hql);
	}

	public void addWlinkman(Wlinkman Wlinkman) throws Exception {
		EntityManager.save(Wlinkman);
	}
	
	public void updWlinkman(Wlinkman Wlinkman) throws Exception {
		EntityManager.update(Wlinkman);
	}
	public void updWlinkmanByBeach(List  list) throws Exception {
		EntityManager.updateByBeach(list);
	}
	
	public void updWlinkmanByjdbc(String  sql) throws Exception {
		EntityManager.updByJDBC(sql);
	}

	public void delWlinkman(int id) throws Exception {
		String sql = "delete from Wlinkman where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	public void delWlinkmanByCid(String id) throws Exception {
		String sql = "delete from Wlinkman where cid='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public Wlinkman getWlinkmanByID(int id) throws Exception {
		String hql = "from Wlinkman as l where l.id=" + id;
		return (Wlinkman) EntityManager.find(hql);
	}

	public List<Wlinkman> getWlinkmanByNccode(String nccode) throws Exception {
		String hql = "from Wlinkman as l where l.warehouseid in (select id from Warehouse where nccode='"+nccode+"')";
		return EntityManager.getAllByHql(hql);
	}

	public Wlinkman findWlinkmanByCid(String cid) throws Exception {
		String hql = "from Wlinkman as l where l.cid='" + cid + "'";
		return (Wlinkman) EntityManager.find(hql);
	}
	public Wlinkman getMainLinkmanByCid(String cid) {
		Wlinkman linkman = null;

		String hql = "from Wlinkman as l where l.cid='" + cid
				+ "' order by l.ismain desc,l.id ";
		List list = EntityManager.getAllByHql(hql);
		if (list.size() > 0) {
			linkman = (Wlinkman) list.get(0);
		}
		return linkman;
	}
	
	/**
	 * 根据联系人名得到机构集合
	 * @param WlinkmanName 联系人名
	 * @return 机构集合
	 */
	public List<Wlinkman> getWlinkmanByName(String WlinkmanName){
		String hql = "from Wlinkman as l where l.name like '%" + WlinkmanName + "%'";
		return EntityManager.getAllByHql(hql);
	}
	
	/**
	 * 查找确定名字的信息
	 * @param WlinkmanName 联系人名
	 * @return 机构集合
	 */
	public List<Wlinkman> getTelByName(String WlinkmanName){
		String hql = "from Wlinkman as l where l.name = '" + WlinkmanName + "'";
		return EntityManager.getAllByHql(hql);
	}
	
	public List<Wlinkman> getWlinkmanByWid(String wid) throws Exception {
		String hql = "from Wlinkman as l where l.warehouseid='" + wid + "'";
		return EntityManager.getAllByHql(hql);
	}

}
