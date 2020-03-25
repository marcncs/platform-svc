package com.winsafe.hbm.util.pager2;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;

import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;
import com.winsafe.hbm.util.pager2.PageResult;
import com.winsafe.hbm.entity.HibernateUtil;


public class PageQuery {
	
	public static List hbmQuery(HttpServletRequest request, String hql) throws Exception{
		BasePage bp = new BasePage(request);		
		PageResult pr = hbmQuery(hql, bp.getPageNo(), bp.getPageSite());
		bp.setPage(pr.getPage());
		return pr.getContent();
	}

	public static List hbmQuery(HttpServletRequest request, String hql, int pageSize) throws Exception{
		BasePage bp = new BasePage(request, pageSize);		
		PageResult pr = hbmQuery(hql, bp.getPageNo(), bp.getPageSite());
		bp.setPage(pr.getPage());
		return pr.getContent();
	}
	
	public static List hbmQuery(HttpServletRequest request, String hql, int pageSize, Map<String, Object> param) throws Exception{
		BasePage bp = new BasePage(request, pageSize);		
		PageResult pr = hbmQuery(hql, bp.getPageNo(), bp.getPageSite(), param);
		bp.setPage(pr.getPage()); 
		return pr.getContent();
	}

	private static PageResult hbmQuery(String hql, int currentPage, int pageSize) throws Exception {
		int totalCount = getHbmCount(hql);		
		Page page = new Page(currentPage,pageSize, totalCount);
		List list = getHbmPageList(hql, page.getStartIndex(), pageSize);
		return new PageResult(page, list);
	}
	
	private static PageResult hbmQuery(String hql, int currentPage, int pageSize, Map<String, Object> param) throws Exception {
		int totalCount = getHbmCount(hql, param);		
		Page page = new Page(currentPage,pageSize, totalCount);
		List list = getHbmPageList(hql, page.getStartIndex(), pageSize, param);
		return new PageResult(page, list);
	}
	
	public static List jdbcSqlserverQuery(HttpServletRequest request, String sql) throws Exception{
		BasePage bp = new BasePage(request);		
		PageResult pr = jdbcSqlserverQuery(sql, bp.getPageNo(), bp.getPageSite());
		bp.setPage(pr.getPage());
		return pr.getContent();
	}

	public static List jdbcSqlserverQuery(HttpServletRequest request, String sql, int pageSize) throws Exception{
		BasePage bp = new BasePage(request, pageSize);		
		PageResult pr = jdbcSqlserverQuery(sql, bp.getPageNo(), bp.getPageSite());
		bp.setPage(pr.getPage());
		return pr.getContent();
	}
	
	public static List jdbcSqlserverQuery2(HttpServletRequest request, String sql, int pageSize) throws Exception{
		BasePage bp = new BasePage(request, pageSize);		
		PageResult pr = jdbcSqlserverQuery2(sql, bp.getPageNo(), bp.getPageSite());
		bp.setPage(pr.getPage());
		return pr.getContent();
	}
	
	
	public static List jdbcSqlserverQuery(HttpServletRequest request, String keyid, String sql, int pageSize) throws Exception{
		BasePage bp = new BasePage(request, pageSize);		
		PageResult pr = jdbcSqlserverQuery(sql, keyid, bp.getPageNo(), bp.getPageSite());
		bp.setPage(pr.getPage());
		return pr.getContent();
	}
	
	public static List jdbcSqlserverQuery(HttpServletRequest request, String keyid, String sql, int pageSize, Map<String, Object> param) throws Exception{
		BasePage bp = new BasePage(request, pageSize);		
		PageResult pr = jdbcSqlserverQuery(sql, keyid, bp.getPageNo(), bp.getPageSite(), param);
		bp.setPage(pr.getPage());
		return pr.getContent();
	}
	
	private static PageResult jdbcSqlserverQuery(String sql, int currentPage, int pageSize) throws Exception {
		int totalCount = getJdbcCount(sql);
		Page page = new Page(currentPage, pageSize, totalCount);
		List list = getJdbcSQLServerPageList(sql, "id", page.getStartIndex(), page.getLastIndex());
		return new PageResult(page, list);
	}
	
	private static PageResult jdbcSqlserverQuery2(String sql, int currentPage, int pageSize) throws Exception {
		int totalCount = getJdbcCount(sql);
		Page page = new Page(currentPage, pageSize, totalCount);
		List list = getJdbcSQLServerPageList2(sql, "id", page.getStartIndex(), page.getLastIndex());
		return new PageResult(page, list);
	}
	

	
	private static PageResult jdbcSqlserverQuery(String sql, String keyid, int currentPage, int pageSize) throws Exception {
		int totalCount = getJdbcCount(sql);
		Page page = new Page(currentPage, pageSize, totalCount);
		List list = getJdbcSQLServerPageList(sql, keyid, page.getStartIndex(), page.getLastIndex());
		return new PageResult(page, list);
	}
	
	private static PageResult jdbcSqlserverQuery(String sql, String keyid, int currentPage, int pageSize, Map<String, Object> param) throws Exception {
		int totalCount = getJdbcCount(sql, param);
		Page page = new Page(currentPage, pageSize, totalCount);
		List list = getJdbcSQLServerPageList(sql, keyid, page.getStartIndex(), page.getLastIndex(), param);
		return new PageResult(page, list);
	}


	private static int getHbmCount(String hql) throws Exception {
		int size = 0;
		try {
			String counthql = " select count(*) " + removeSelect(removeOrders(hql));
			Session session = HibernateUtil.currentSession();
			size = ((Long)session.createQuery(counthql).iterate().next()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("PageQuery getCount error:" + e.getMessage());
		}
		return size;
	}
	
	private static int getHbmCount(String hql,  Map<String, Object> param) throws Exception {
		int size = 0;
		try {
			String counthql = " select count(*) " + removeSelect(removeOrders(hql));
			Session session = HibernateUtil.currentSession();
			Query query = session.createQuery(counthql);
			if (null != param && param.size() > 0)
			{
	            for (Map.Entry<String, Object> entry : param.entrySet()) {
	                query.setParameter(entry.getKey(), entry.getValue());
	            }
			}
			size = ((Long)query.iterate().next()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("PageQuery getCount error:" + e.getMessage());
		}
		return size;
	}
	

	public static int getJdbcCount(String sql) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		  int c = 0;
		  try{
			//int index = sql.toLowerCase().indexOf("order by");
			//if ( index > 0 ){
			//	sql = sql.substring(0, index);
			//}
			
			//如果order by后面还有)，则说明该order by 后面还有其他语句，如开窗函数。不能简单的删除之后的数据。
			int lastIndexOfOrderBy = sql.toLowerCase().indexOf("order by");
			if(lastIndexOfOrderBy > 0 && sql.toLowerCase().indexOf(")", lastIndexOfOrderBy) < 0){
				sql = sql.substring(0, lastIndexOfOrderBy);	
			}
			  
			String countsql = "select count(*) from (" + sql + ") t";
			Session session = HibernateUtil.currentSession();
			stmt = session.connection().createStatement();
			rs = stmt.executeQuery(countsql);
			rs.next();
			c = rs.getInt(1);	

		  }catch ( SQLException e ){			 
			  throw new Exception ("PageQuery getJdbcCount error:"+e.getMessage());
		  }finally{
			  if ( rs != null ){
				  rs.close();
			  }
			  if ( stmt != null ){
				  stmt.close();
			  }
		  }

		  return c;
		}
	
	public static int getJdbcCount(String sql, Map<String, Object> param) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		  int c = 0;
		  try{
			
			//如果order by后面还有)，则说明该order by 后面还有其他语句，如开窗函数。不能简单的删除之后的数据。
			int lastIndexOfOrderBy = sql.toLowerCase().indexOf("order by");
			if(lastIndexOfOrderBy > 0 && sql.toLowerCase().indexOf(")", lastIndexOfOrderBy) < 0){
				sql = sql.substring(0, lastIndexOfOrderBy);	
			}
			String countsql = "select count(*) from (" + sql + ") t";
			Session session = HibernateUtil.currentSession();
			stmt = session.connection().prepareStatement(countsql);
			DbUtil.setParamForSql(param, stmt);
			rs = stmt.executeQuery();
			rs.next();
			c = rs.getInt(1);	
		  }catch ( SQLException e ){			 
			  throw new Exception ("PageQuery getJdbcCount error:"+e.getMessage());
		  }finally{
			  if ( rs != null ){
				  rs.close();
			  }
			  if ( stmt != null ){
				  stmt.close();
			  }
		  }

		  return c;
		}


	private static  List getHbmPageList(String hql, int firstResult, int maxResults) throws Exception {
		List list = null;
		try {
			Session session = HibernateUtil.currentSession();
			Query query = session.createQuery(hql);
			//if(list!=null)
			list = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("PageQuery getPageList error:" + e.getMessage());
		}
		return list;
	}
	
	private static  List getHbmPageList(String hql, int firstResult, int maxResults, Map<String, Object> param) throws Exception {
		List list = null;
		try {
			Session session = HibernateUtil.currentSession();
			Query query = session.createQuery(hql);
			if (null != param && param.size() > 0)
			{
	            for (Map.Entry<String, Object> entry : param.entrySet()) {
	                query.setParameter(entry.getKey(), entry.getValue());
	            }
			}
			//if(list!=null)
			list = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("PageQuery getPageList error:" + e.getMessage());
		}
		return list;
	}
	

	private static List getJdbcSQLServerPageList(String sql, String orderField, int firstResult, int lastResult) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		List list = null;
		try{	
			StringBuffer querysql = new StringBuffer();
			querysql.append("SELECT *  ")
			.append("   FROM (select ROW_NUMBER() Over(order by ").append(orderField).append(") r, j.* ")
			.append("   from (").append(sql).append(") j ) t ")
			.append("   where r>").append(firstResult).append(" and r<=").append(lastResult);
			Session session = HibernateUtil.currentSession();
			stmt = session.connection().createStatement();
			rs = stmt.executeQuery(querysql.toString());
			list = converResultSetToList(rs);

		}catch ( Exception e ){
			throw new Exception ("PageQuery getJdbcSQLServerPageList error:"+e.getMessage());
		}finally{
			if ( rs != null ){
				rs.close();
			}
			if ( stmt != null ){
				stmt.close();
			}
		}
		return list;
	}
	
	private static List getJdbcSQLServerPageList(String sql, String orderField, int firstResult, int lastResult, Map<String, Object> param) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List list = null;
		try{
			StringBuffer querysql = new StringBuffer();
			querysql.append("SELECT *  ")
			.append("   FROM (select ROW_NUMBER() Over(order by ").append(orderField).append(") r, j.* ")
			.append("   from (").append(sql).append(") j ) t ")
			.append("   where r>").append(firstResult).append(" and r<=").append(lastResult);
			Session session = HibernateUtil.currentSession();
			stmt = session.connection().prepareStatement(querysql.toString());
			DbUtil.setParamForSql(param, stmt);
			rs = stmt.executeQuery();
			list = converResultSetToList(rs);

		}catch ( Exception e ){
			throw new Exception ("PageQuery getJdbcSQLServerPageList error:"+e.getMessage());
		}finally{
			if ( rs != null ){
				rs.close();
			}
			if ( stmt != null ){
				stmt.close();
			}
		}
		return list;
	}
	
	private static List getJdbcSQLServerPageList2(String sql, String orderField, int firstResult, int lastResult) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		List list = null;
		String order="";
		try{	

			int index = sql.toLowerCase().indexOf("order by");
			if ( index > 0 ){
				order= sql.substring(index);
				sql = sql.substring(0, index);
				
			}
						
			StringBuffer querysql = new StringBuffer();
			querysql.append("SELECT *  ")
			.append("   FROM (select ROW_NUMBER() Over( ").append(order).append(") as rowNum, * ")
			.append("   from (").append(sql).append(") as j ) as t ")
			.append("   where rowNum>").append(firstResult).append(" and rowNum<=").append(lastResult);
			Session session = HibernateUtil.currentSession();
			stmt = session.connection().createStatement();
			rs = stmt.executeQuery(querysql.toString());
			list = converResultSetToList(rs);

		}catch ( Exception e ){
			throw new Exception ("PageQuery getJdbcSQLServerPageList error:"+e.getMessage());
		}finally{
			if ( rs != null ){
				rs.close();
			}
			if ( stmt != null ){
				stmt.close();
			}
		}
		return list;
	}
	
	public static List getJdbcSQLServerAll(String sql, String orderField) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		List list = null;
		try{	
			StringBuffer querysql = new StringBuffer();
			querysql.append("SELECT *  ")
			.append("   FROM (select ROW_NUMBER() Over(order by ").append(orderField).append(") as rowNum, * ")
			.append("   from (").append(sql).append(") as j ) as t ");
			Session session = HibernateUtil.currentSession();
			stmt = session.connection().createStatement();
			rs = stmt.executeQuery(querysql.toString());
			list = converResultSetToList(rs);

		}catch ( Exception e ){
			throw new Exception ("PageQuery getJdbcSQLServerPageList error:"+e.getMessage());
		}finally{
			if ( rs != null ){
				rs.close();
			}
			if ( stmt != null ){
				stmt.close();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private static List converResultSetToList(ResultSet rs) throws SQLException{
		List list = new ArrayList();
		ResultSetMetaData meteData = rs.getMetaData();
		rs.setFetchSize(50);
		int columnCount = meteData.getColumnCount();
		Map map = null;
		while ( rs.next() ){
			map = new HashMap();
			for ( int i = 1 ; i <= columnCount ; i++ ){
				map.put(meteData.getColumnName(i).toLowerCase(), rs.getString(i));
			}
			list.add(map);
		}
		return list;
	}

	private static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	private static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 使用sql查询
	 * @param request
	 * @param sql 查询语句
	 * @param pageSize 指定页数
	 * @return
	 * @throws Exception
	 */
	public static List sqlQuery(HttpServletRequest request, String sql, int pageSize) throws Exception{
		BasePage bp = new BasePage(request, pageSize);		
		PageResult pr = sqlQuery(sql, bp.getPageNo(), bp.getPageSite());
		bp.setPage(pr.getPage());
		return pr.getContent();
	}
	
	/**
	 * 使用sql查询
	 * @param sql 查询语句
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	private static PageResult sqlQuery(String sql, int currentPage, int pageSize) throws Exception {
		int totalCount = getSqlCount(sql);		
		Page page = new Page(currentPage,pageSize, totalCount);
		List list = getSqlPageList(sql, page.getStartIndex(), pageSize);
		return new PageResult(page, list);
	}
	
	
	/**
	 * 使用sql查询
	 * @param sql 查询语句
	 * @return
	 * @throws Exception
	 */
	public static int getSqlCount(String sql) throws Exception {
		int size = 0;
		try {
			String counthql = " select count(*) " + removeSelect(removeOrders(sql));
			Session session = HibernateUtil.currentSession();
			BigDecimal bd = (BigDecimal) session.createSQLQuery(counthql).list().get(0);
			size = bd.intValue();
		} catch (Exception e) {
			if(Constants.isDebug){
				e.printStackTrace();
			}
			throw new Exception("PageQuery getSqlCount error:" + e.getMessage());
		}
		return size;
	}
	
	/**
	 * 使用sql查询
	 * @param sql 查询语句
	 * @param firstResult 开始行数
	 * @param maxResults 结束行数
	 * @return
	 * @throws Exception
	 */
	private static  List getSqlPageList(String sql, int firstResult, int maxResults) throws Exception {
		List list = null;
		try {
			Session session = HibernateUtil.currentSession();
			Query query = session.createSQLQuery(sql);
			list = query.setFirstResult(firstResult).setMaxResults(maxResults).list();
		} catch (Exception e) {
			if(Constants.isDebug){
				e.printStackTrace();
			}
			throw new Exception("PageQuery getSqlPageList error:" + e.getMessage());
		}
		return list;
	}
	
	public static List getSqlList(String sql) throws Exception {
		List list = null;
		try {
			Session session = HibernateUtil.currentSession();
			Query query = session.createSQLQuery(sql);
			list = query.list();
		} catch (Exception e) {
			throw new Exception("PageQuery getSqlList error:" + e.getMessage());
		}
		return list;
	}
}
