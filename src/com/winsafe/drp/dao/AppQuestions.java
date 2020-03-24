package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-8-8 上午10:06:48 www.winsafe.cn
 */
public class AppQuestions {

	@SuppressWarnings("unchecked")
	public List<Questions> findAll(HttpServletRequest request, String whereStr,
			Integer pageSize) throws Exception {
		String hql = " from Questions as q " + whereStr
				+ " order by q.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	public Questions findByID(Integer id) throws Exception {
		String hql = " from Questions as q where q.id = " + id;
		return (Questions) EntityManager.find(hql);
	}

	public void save(Questions questions) throws Exception {
		EntityManager.save(questions);
	}

	public void update(Questions questions) throws Exception {
		EntityManager.update(questions);
	}

	public void delete(Questions questions) throws Exception {
		EntityManager.delete(questions);
	}

	public void delete(Integer id) throws Exception {
		String hql = "delete from questions as q where q.id = " + id;
		EntityManager.updateOrdelete(hql);
	}

	public void deleteAll() throws Exception {
		String hql = "delete from questions as q ";
		EntityManager.updateOrdelete(hql);
	}
}
