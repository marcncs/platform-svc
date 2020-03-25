package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.Encrypt; 
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOlinkMan {

	public List searchOlinkman(HttpServletRequest request, String pWhereClause)
			throws Exception {
		String hql = " from Olinkman  " + pWhereClause + " order by makedate desc";
		return PageQuery.hbmQuery(request, hql);
	}

	public void addOlinkman(Olinkman Olinkman) throws Exception {
		EntityManager.save(Olinkman);
	}
	
	public void updOlinkman(Olinkman Olinkman) throws Exception {
		EntityManager.update(Olinkman);
	}
	public void updOlinkmanByBeach(List  list) throws Exception {
		EntityManager.updateByBeach(list);
	}
	
	public void updOlinkmanByjdbc(String  sql) throws Exception {
		EntityManager.updByJDBC(sql);
	}

	public void delOlinkman(int id) throws Exception {
		String sql = "delete from Olinkman where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	public void delOlinkmanByCid(String id) throws Exception {
		String sql = "delete from Olinkman where cid='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public Olinkman getOlinkmanByID(int id) throws Exception {
		String hql = "from Olinkman as l where l.id=" + id;
		return (Olinkman) EntityManager.find(hql);
	}

	public List<Olinkman> getOlinkmanByCid(String cid) throws Exception {
		String hql = "from Olinkman as l where l.cid='" + cid + "' order by id ";
		return EntityManager.getAllByHql(hql);
	}

	public Olinkman findOlinkmanByCid(String cid) throws Exception {
		String hql = "from Olinkman as l where l.cid='" + cid + "'";
		return (Olinkman) EntityManager.find(hql);
	}
	
	public Olinkman findOlinkmanByCidAndMobile(String cid, String mobile) throws Exception {
		mobile = Encrypt.getSecret(mobile, 3);
		String hql = "from Olinkman as l where l.cid='" + cid + "' and l.mobile = '"+mobile+"'";
		return (Olinkman) EntityManager.find(hql);
	}
	
	public Olinkman getMainLinkmanByCid(String cid) {
		Olinkman linkman = null;

		String hql = "from Olinkman as l where l.cid='" + cid
				+ "' order by l.ismain desc,l.id ";
		List list = EntityManager.getAllByHql(hql);
		if (list.size() > 0) {
			linkman = (Olinkman) list.get(0);
		}
		return linkman;
	}
	
	/**
	 * 根据联系人名得到机构集合
	 * @param olinkmanName 联系人名
	 * @return 机构集合
	 */
	public List<Olinkman> getOlinkmanByName(String olinkmanName){
		String hql = "from Olinkman as l where l.name like '%" + olinkmanName + "%'";
		return EntityManager.getAllByHql(hql);
	}
	
	/**
	 * 查找确定名字的信息
	 * @param olinkmanName 联系人名
	 * @return 机构集合
	 */
	public List<Olinkman> getTelByName(String olinkmanName){
		String hql = "from Olinkman as l where l.name = '" + olinkmanName + "'";
		return EntityManager.getAllByHql(hql);
	}
	
	/**
	 * 根据联系人名得到机构集合ID
	 * @param olinkmanName 联系人名
	 * @return 机构集合ID字符串(","号分隔)
	 */
	public String getOlinkmanIds(String olinkmanName) {
		String organIds = "";
		List<Olinkman> olinkmans = getOlinkmanByName(olinkmanName);
		if(olinkmans.size() == 0){
			return null;
		}
		for (Olinkman olinkman : olinkmans) {
			organIds += olinkman.getCid() + ",";
		}
		return organIds.substring(0,organIds.length()-1);
	}

	public Olinkman getOlinkmanByMobile(String mobile) {
		mobile = Encrypt.getSecret(mobile, 3);
		String hql = "from Olinkman as l where l.mobile='"+mobile+"'";
		return (Olinkman)EntityManager.find(hql);
	}

}
