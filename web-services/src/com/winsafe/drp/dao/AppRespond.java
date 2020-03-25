package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-8-8 上午11:51:07
 * www.winsafe.cn
 */
public class AppRespond {
	@SuppressWarnings("unchecked")
	public List<Respond> findAll(HttpServletRequest request, String whereStr,
			Integer pageSize) throws Exception {
		String hql = "from Respond as r " + whereStr
				+ " order by r.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public Respond findByID(Integer id) throws Exception {
		String hql = " from Respond as r where r.id = " + id;
		return (Respond) EntityManager.find(hql);
	}

	public void save(Respond respond) throws Exception {
		EntityManager.save(respond);
	}

	public void update(Respond respond) throws Exception {
		EntityManager.update(respond);
	}

	public void delete(Respond respond) throws Exception {
		EntityManager.delete(respond);
	}
	
	public void deleteByQID(Integer qid)throws Exception{
		String hql = "delete from respond where qid = " + qid;
		EntityManager.updateOrdelete(hql);
	}

	public void delete(Integer id) throws Exception {
		String hql = "delete from respond where id = " + id;
		EntityManager.updateOrdelete(hql);
	}

	public void deleteAll() throws Exception {
		String hql = "delete from respond ";
		EntityManager.updateOrdelete(hql);
	}
	public Integer getNumber(Integer qid){
		String hql = "from Respond as r where r.qid = " + qid;
		List<Respond> list  =  EntityManager.getAllByHql(hql);
		return list==null?0:list.size();
	}

}
