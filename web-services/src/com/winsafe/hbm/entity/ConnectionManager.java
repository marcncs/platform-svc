/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.winsafe.hbm.entity;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.winsafe.hbm.entity.ConnectionManager;
import com.winsafe.hbm.entity.HibernateUtil;

/**
 * @author Fox
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ConnectionManager {
	private static Logger logger = Logger.getLogger(ConnectionManager.class);

	private static DataSource dataSource = null;

	private static ThreadLocal threadConn = new ThreadLocal() {
		protected synchronized Object initialValue() {
			return null;
		}
	};

	public static void setDataSource(DataSource ds) throws Exception {
		if (ds == null) {
			//throwing Exception
			logger.warn("attempt set DataSource to null ! ");
		//	throw new Exception("method.exception",null,new String[]{"setDataSource(DataSource ds) of class ConnectionManager","attempt set DataSource to null"});
		}
		dataSource = ds;
	}

	public static Connection getConnection() throws Exception {
		logger.debug("getConnection ");
		Connection tmpConn = (Connection) threadConn.get();
//		System.out.println("connectionManager =============" + tmpConn);
		try
		{

			if (tmpConn == null || tmpConn.isClosed()) {
				tmpConn = dataSource.getConnection();
				logger.info("getConnection: " + tmpConn);
				threadConn.set(tmpConn);
			}
	//		Thread.sleep(2000);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		//	throw new Exception("method.exception",null,new String[]{"getConnection() of class ConnectionManager",ex.getMessage()});
		}

		return tmpConn;
	}

	public static Connection getAloneConnection()throws Exception{
	    logger.debug("getAloneConnection ");
	    Connection tmpConn = null;
		try
		{
	//		Thread.sleep(2000);
		    tmpConn=dataSource.getConnection();

			logger.debug("getAloneConnection: " + tmpConn);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
	//		throw new Exception("method.exception",null,new String[]{"getAloneConnection() of class ConnectionManager",ex.getMessage()});
		}
		return tmpConn;
	}

        public static Connection getHAloneConnection() throws Exception {
        logger.debug("getAloneConnection ");
        Connection tmpConn = null;
        try {
                tmpConn = HibernateUtil.getAloneSessionBySessionFactory()
                                .connection();

                logger.debug("getAloneConnection: " + tmpConn);
        } catch (Exception ex) {
                ex.printStackTrace();
        }
        return tmpConn;
      }


	public static ThreadLocal getThreadConn(){
		return threadConn;
	}

	public static void closeConnection() throws Exception {
	    logger.debug("closeConnection ");
		Connection tmpConn = (Connection) threadConn.get();
		if (tmpConn != null) {
			threadConn.set(null);
			try {
				if (!tmpConn.isClosed())
			//		System.out.println("tmpConn cnnection manager =========================  " + tmpConn);
		//			Thread.sleep(2000);
				    logger.info("closeConnection: " + tmpConn);
					tmpConn.close();
			} catch (Exception exc) {
			    logger.error(exc);
		//	    throw new Exception("method.exception",null,new String[]{"closeConnection() of class ConnectionManager",exc.getMessage()});
			}
		}
	}

}
