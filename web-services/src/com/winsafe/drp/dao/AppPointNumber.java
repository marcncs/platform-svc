package com.winsafe.drp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;
import com.winsafe.hbm.util.pager2.PageResult;

public class AppPointNumber {
	
	public static List jdbcSqlserverQuery(HttpServletRequest request, String sql) throws Exception{
		BasePage bp = new BasePage(request);		
		PageResult pr = jdbcSqlserverQuery(sql, bp.getPageNo(), -1);
		bp.setPage(pr.getPage());
		return pr.getContent();
	}
	
	
	public static List jdbcSqlserverQuery(HttpServletRequest request, String sql, int pageSize) throws Exception{
		BasePage bp = new BasePage(request, pageSize);		
		PageResult pr = jdbcSqlserverQuery(sql, bp.getPageNo(), bp.getPageSite());
		bp.setPage(pr.getPage());
		return pr.getContent();
	}
	
	private static PageResult jdbcSqlserverQuery(String sql, int currentPage, int pageSize) throws Exception {
		Properties p = JProperties.loadProperties("database.properties", JProperties.BY_CLASSLOADER);
		String url = p.getProperty("pointNumber.url");
		String user = p.getProperty("pointNumber.username");;
		String password = p.getProperty("pointNumber.password");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection(url, user, password);
		
		int totalCount = getJdbcCount(sql,conn);
		Page page = new Page(currentPage, pageSize, totalCount);
		List list =null;
		if(pageSize==-1){
			list = getJdbcSQLServerPageList(sql, "Points_Number", -1, page.getLastIndex(),conn);
		}else{
		  list = getJdbcSQLServerPageList(sql, "Points_Number", page.getStartIndex(), page.getLastIndex(),conn);
		}
		return new PageResult(page, list);
	}
	
	public static int getJdbcCount(String sql,Connection conn) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		  int c = 0;
		  try{
			int index = sql.toLowerCase().indexOf("order by");
			if ( index > 0 ){
				sql = sql.substring(0, index);
			}
			String countsql = "select count(*) from (" + sql + ") t";
			stmt = conn.createStatement();
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
	
	public static int getTotalCount(String sql) throws Exception {
		Properties p = JProperties.loadProperties("database.properties", JProperties.BY_CLASSLOADER);
		String url = p.getProperty("pointNumber.url");
		String user = p.getProperty("pointNumber.username");;
		String password = p.getProperty("pointNumber.password");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = null;
		ResultSet rs = null;
		  int c = 0;
		  try{
			int index = sql.toLowerCase().indexOf("order by");
			if ( index > 0 ){
				sql = sql.substring(0, index);
			}
			String countsql = "select count(*) from (" + sql + ") t";
			stmt = conn.createStatement();
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
				if(conn!=null){
					conn.close();
				}
		  }

		  return c;
		}
	
	
	private static List getJdbcSQLServerPageList(String sql, String orderField, int firstResult, int lastResult,Connection conn) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		List list = null;
		try{	
			StringBuffer querysql = new StringBuffer();
			querysql.append("SELECT *  ")
			.append("   FROM (select ROW_NUMBER() Over(order by ").append(orderField).append(") as rowNum, * ")
			.append("   from (").append(sql).append(") as j ) as t ");
			if(firstResult!=-1){
				querysql.append("   where rowNum>").append(firstResult).append(" and rowNum<=").append(lastResult);
			}
			stmt = conn.createStatement();
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
			if(conn!=null){
				conn.close();
			}
		}
		return list;
	}
	
	private static List converResultSetToList(ResultSet rs) throws SQLException{
		List list = new ArrayList();
		ResultSetMetaData meteData = rs.getMetaData();
		rs.setFetchSize(50);
		int columnCount = meteData.getColumnCount();
		Map map = null;
		while ( rs.next() ){
			map = new HashMap();
			for ( int i = 1 ; i <= columnCount ; i++ ){
				map.put(meteData.getColumnName(i), rs.getString(i));
			}
			list.add(map);
		}
		return list;
	}
}
