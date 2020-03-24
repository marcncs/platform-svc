/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.hbm.entity;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.winsafe.hbm.entity.ConnectionEntityManager;
import com.winsafe.hbm.entity.ConnectionManager;

public class ConnectionEntityManager {

    private static Logger logger = Logger
            .getLogger(ConnectionEntityManager.class);



    public static void populate(Object bean, ResultSet rs) throws Exception {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int ncolumns = metaData.getColumnCount();

            HashMap properties = new HashMap();
            // Scroll to next record and pump into hashmap
            for (int i = 1; i <= ncolumns; i++) {
                properties.put((metaData.getColumnName(i)), rs.getString(i));
            }
            // Set the corresponding properties of our bean
            try {
                BeanUtils.populate(bean, properties);
            } catch (InvocationTargetException ite) {
                ite.printStackTrace();
                throw new Exception(ite);
            } catch (IllegalAccessException iae) {
                iae.printStackTrace();

                throw new Exception(iae);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

         //   throw new Exception("method.exception",null,new String[]{"populate(Object bean, ResultSet rs) of class ConnectionEntityManager",ex.getMessage()});
        }
    }

    public static int getSize(String tableName, String condition) throws Exception{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT count(*) FROM " + tableName + " " + condition;
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            rs.next();
            int size = rs.getInt(1);
            close(rs);
            close(pstmt);
            return size;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw  sqle;
       //     throw new Exception("method.exception",null,new String[]{"getSize(String tableName, String condition) of class ConnectionEntityManager",sqle.getMessage()});
        }
        finally
        {
            close(rs);
            close(pstmt);
        }
    }

    public static Connection getConnection() throws Exception {
        return ConnectionManager.getConnection();
    }

    public static void close(ResultSet rs) throws Exception {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
      //          throw new Exception("method.exception",null,new String[]{"close(ResultSet rs) of class ConnectionEntityManager",e.getMessage()});
            }
            rs = null;
        }
    }

    public static void close(PreparedStatement pstmt) throws Exception {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
         //       throw new Exception("method.exception",null,new String[]{"close(PreparedStatement pstmt) of class ConnectionEntityManager",e.getMessage()});
            }
            pstmt = null;
        }
    }

    public static void close(Statement stmt) throws Exception {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
          //      throw new Exception("method.exception",null,new String[]{"close(Statement stmt) of class ConnectionEntityManager",e.getMessage()});
            }
            stmt = null;
        }
    }

    public static void close(CallableStatement cstmt) throws Exception {
        if (cstmt != null) {
            try {
                cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
         //       throw new Exception("method.exception",null,new String[]{"close(CallableStatement cstmt) of class ConnectionEntityManager",e.getMessage()});
            }
            cstmt = null;
        }
    }

    public static void close(Connection conn) throws Exception {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
              //          throw new Exception("method.exception",null,new String[]{"close(Connection conn) of class ConnectionEntityManager",ex.getMessage()});
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
        //        throw new Exception("method.exception",null,new String[]{"close(Connection conn) of class ConnectionEntityManager",e.getMessage()});
            }
            conn = null;
        }
    }

    public static void close() throws Exception {
        ConnectionManager.closeConnection();
    }

    public static void rollback(Connection conn) throws Exception {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
         //       throw new Exception("method.exception",null,new String[]{"rollback(Connection conn) of class ConnectionEntityManager",e.getMessage()});
            }
            conn = null;
        }
    }

    public static Connection getAloneConnection() throws Exception {
        return ConnectionManager.getAloneConnection();
    }
}
