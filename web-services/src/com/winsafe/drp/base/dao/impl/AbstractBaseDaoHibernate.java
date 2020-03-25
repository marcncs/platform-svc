package com.winsafe.drp.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List; 
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winsafe.drp.base.dao.IBaseDao;
import com.winsafe.drp.base.dao.db.PageBean;
import com.winsafe.drp.base.dao.db.PageUtil;
import com.winsafe.hbm.entity.HibernateUtil;


public abstract class AbstractBaseDaoHibernate<T> implements IBaseDao<T> {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractBaseDaoHibernate.class);

	private Class<T> clazz;

	public AbstractBaseDaoHibernate(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Resource(name = "sessionFactory4main")
//	protected SessionFactory sessionFactory;

	@Override
	public Serializable save(T t) {
		if (t != null) {
			return HibernateUtil.currentSession(true).save(t);
		}
		return null;
	}

	@Override
	public void delete(T t) {
		if (t != null) {
			try {
				t.getClass().getMethod("delete").invoke(t);
			} catch (NoSuchMethodException e) {
				logger.error("can't find delete function in " + t.getClass().getName() + ", error:" + e);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				logger.error("invoke Delete Function error:" + e.getTargetException());
				e.getTargetException().printStackTrace();
			} catch (Exception e) {
				logger.error("invoke Delete Function error:" + e);
				e.printStackTrace();
			}
			HibernateUtil.currentSession(true).update(t);
		}
	}

	@Override
	public void delete(Serializable id) {
		T t = get(id);
		if (t != null) {
			delete(t);
		}
	}
	
	@Override
	public void deletePhysical(T t) {
		if (t != null) {
			HibernateUtil.currentSession(true).delete(t);
		}
	}

	@Override
	public T update(T t) {
		if (t != null) {
			HibernateUtil.currentSession(true).update(t);
		}
		return t;
	}

	@Override
	public void saveOrUpdate(T t) {
		if (t != null) {
			HibernateUtil.currentSession(true).saveOrUpdate(t);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(Serializable id) {
		if (id == null) {
			return null;
		}
		T t = (T) HibernateUtil.currentSession(true).get(clazz, id);
		if (t == null) {
			return null;
		} else {
			try {
				Integer isDeleted = (Integer)t.getClass().getMethod("getIsDeleted").invoke(t);
				if (isDeleted != null && isDeleted == 1) {
					//已经逻辑删除
					return null;
				} else {
					return t;
				}
			} catch (InvocationTargetException e) {
				logger.error("invoke Delete Function error:" + e.getTargetException());
				e.getTargetException().printStackTrace();
			} catch (Exception e) {
				logger.error("invoke Delete Function error:" + e);
				e.printStackTrace();
			}
			return t;
		}
	}

	@Override
	public List<T> getAll() {
		String hql = "FROM " + clazz.getSimpleName() + " WHERE isDeleted = 0";
		return queryForList(hql, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T queryForObject(String hql, Map<String, Object> paramMap) {
		hql = filterDeletedRecord(hql); //过滤已删除记录
		Query query = HibernateUtil.currentSession(true).createQuery(hql);
		setQueryParamMap(query, paramMap);
		return (T) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T queryForTopObject(String hql, Map<String, Object> paramMap) {
		hql = filterDeletedRecord(hql); //过滤已删除记录
		Query query = HibernateUtil.currentSession(true).createQuery(hql);
		setQueryParamMap(query, paramMap);
		return (T) query.setFirstResult(0).setMaxResults(1).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryForList(String hql, Map<String, Object> paramMap) {
		hql = filterDeletedRecord(hql); //过滤已删除记录
		Query query = HibernateUtil.currentSession(true).createQuery(hql);
		setQueryParamMap(query, paramMap);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryForList(String hql, Map<String, Object> paramMap, int recordNum) {
		hql = filterDeletedRecord(hql); //过滤已删除记录
		Query query = HibernateUtil.currentSession(true).createQuery(hql);
		setQueryParamMap(query, paramMap);
		return query.setFirstResult(0).setMaxResults(recordNum).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageBean<T> queryForPage(String hql, Map<String, Object> paramMap, int currentPage, int pageSize) {
		hql = filterDeletedRecord(hql); //过滤已删除记录
		//先取出页数
		int startIndex = PageUtil.countStartIndex(pageSize, currentPage);
		Query query = HibernateUtil.currentSession(true).createQuery(hql);
		setQueryParamMap(query, paramMap);
		List<T> list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		//再取出总记录数
		int totalRows = 0;
		int totalPage = 0;
		if(StringUtils.containsIgnoreCase(hql, "FROM ")){
			int usefulHqlStart = StringUtils.indexOfIgnoreCase(hql, "FROM ");
			int usefulHqlEnd = StringUtils.indexOfIgnoreCase(hql, " ORDER BY ");
			String countHql;
			if (usefulHqlEnd <0) {
				countHql = "SELECT COUNT(*) " + StringUtils.substring(hql, usefulHqlStart);
			} else {
				countHql = "SELECT COUNT(*) " + StringUtils.substring(hql, usefulHqlStart, usefulHqlEnd);
			}
			query = HibernateUtil.currentSession(true).createQuery(countHql);
			setQueryParamMap(query, paramMap);
			long rsCount = (Long) query.uniqueResult();
			totalRows = (int) rsCount;
			totalPage = PageUtil.countTotalPage(pageSize, totalRows);
		}
		PageBean<T> pageBean = new PageBean<T>();
		if (list != null && list.size() > 0) {
			pageBean.setList(list);
		}
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalRows(totalRows);
		pageBean.setTotalPage(totalPage);
		
		return pageBean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> sqlQueryForList(String sql, int recordNum) {
		Query query = HibernateUtil.currentSession(true).createSQLQuery(sql).addEntity(clazz);
		return (List<T>) query.setFirstResult(0).setMaxResults(recordNum).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageBean<T> sqlQueryForPage(String sql, int currentPage, int pageSize) {
		//先取出当前页记录集
		int startIndex = PageUtil.countStartIndex(pageSize, currentPage);
		Query query = HibernateUtil.currentSession(true).createSQLQuery(sql).addEntity(clazz);
		List<T> list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		//再取出总记录数
		int totalRows = 0;
		if(StringUtils.containsIgnoreCase(sql, " FROM ")){
			int usefulSqlStart = StringUtils.indexOfIgnoreCase(sql, " FROM ");
			int usefulSqlEnd = StringUtils.indexOfIgnoreCase(sql, " ORDER BY ");
			if (usefulSqlEnd <0) {
				usefulSqlEnd = StringUtils.lastIndexOfIgnoreCase(sql, " LIMIT ");
			}
			String countSql;
			if (usefulSqlEnd <0) {
				countSql = "SELECT COUNT(*) " + StringUtils.substring(sql, usefulSqlStart);
			} else {
				countSql = "SELECT COUNT(*) " + StringUtils.substring(sql, usefulSqlStart, usefulSqlEnd);
			}
			query = HibernateUtil.currentSession(true).createSQLQuery(countSql);
			totalRows = Integer.valueOf(query.list().get(0).toString());
		}
		//封装PageBean
		PageBean<T> pageBean = new PageBean<T>();
		pageBean.setList(list);
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalRows(totalRows);
		pageBean.setTotalPage(PageUtil.countTotalPage(pageSize, totalRows));
		
		return pageBean;
	}

	/** 过滤已删除记录 */
	private static String filterDeletedRecord(String hql) {
		if (StringUtils.contains(hql, " isDeleted = 0")) {
			//已经过滤过，则不需要处理
			return hql;
		}
		int start_where = StringUtils.indexOfIgnoreCase(hql, " WHERE ");
		if (start_where > 0) {
			StringBuffer sb = new StringBuffer(hql);
			return sb.insert(start_where + 6, " isDeleted = 0 AND").toString();
		} else {
			//没有where条件，则单独增加where语句
			int start_orderBy = StringUtils.indexOfIgnoreCase(hql, " ORDER BY ");
			if (start_orderBy > 0) {
				StringBuffer sb = new StringBuffer(hql);
				return sb.insert(start_orderBy, " WHERE isDeleted = 0").toString();
			} else {
				//没有orderby，则直接最后加where
				return hql + " WHERE isDeleted = 0";
			}
		}
	}
	
	private void setQueryParamMap(Query query, Map<String, Object> paramMap) {
		if (null == paramMap) {
			return;
		}
		Iterator<Entry<String, Object>> iter = paramMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
			if (entry.getValue() instanceof Collection) {
				query.setParameterList(entry.getKey(), (Collection<?>)entry.getValue());
			} else if (entry.getValue() instanceof Object[]) {
				query.setParameterList(entry.getKey(), (Object[])entry.getValue());
			} else {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}
	
}
