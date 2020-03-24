package com.winsafe.drp.base.dao;

import java.io.Serializable; 
import java.util.List;
import java.util.Map;

import com.winsafe.drp.base.dao.db.PageBean;

public interface IBaseDao<T> {

	/** baseDao的方法 */
	public Serializable save(T t);
	/** baseDao的方法，逻辑删除 */
	public void delete(T t);
	/** baseDao的方法，逻辑删除 */
	public void delete(Serializable id);
	/** baseDao的方法，物理删除 */
	public void deletePhysical(T t);
	/** baseDao的方法 */
	public T update(T t);
	/** baseDao的方法 */
	public void saveOrUpdate(T t);
	/** baseDao的方法 */
	public List<T> getAll();
	/** baseDao的方法 */
	public T get(Serializable id);
	/** baseDao的方法 */
	public T queryForObject(String hql, Map<String, Object> paramMap);
	/** baseDao的方法 */
	public T queryForTopObject(String hql, Map<String, Object> paramMap);
	/** baseDao的方法 */
	public List<T> queryForList(String hql, Map<String, Object> paramMap);
	/** baseDao的方法 */
	public List<T> queryForList(String hql, Map<String, Object> paramMap, int recordNum);
	/** baseDao的方法 */
	public PageBean<T> queryForPage(String hql, Map<String, Object> paramMap, int currentPage, int pageSize);
	/** baseDao的方法，执行纯sql语句 */
	public List<T> sqlQueryForList(String sql, int recordNum);
	/** baseDao的方法，执行纯sql语句的翻页查询 */
	public PageBean<T> sqlQueryForPage(String sql, int currentPage, int pageSize);
}
