package com.winsafe.hbm.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;
/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConnectionPool {

  private Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  private Session session;

  public Connection getConnection() {
    try {
    	Session session = HibernateUtil.getAloneSessionBySessionFactory();
	      conn = session.connection();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return conn;

  }
  
  public Session getSession() {
	    try {
	    	session = HibernateUtil.getAloneSessionBySessionFactory();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    return session;

	  }
  

  public void freeConnection(Connection conn) {
    try {
      if (conn != null) {
        conn.close();
      }
    }
    catch (Exception e) {

    }
  }
}
