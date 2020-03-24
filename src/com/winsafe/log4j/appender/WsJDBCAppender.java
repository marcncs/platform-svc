package com.winsafe.log4j.appender;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.jdbc.JDBCAppender;

import com.winsafe.hbm.entity.ConnectionPool;

public class WsJDBCAppender extends JDBCAppender {

	protected Connection getConnection() throws SQLException {
		ConnectionPool cp = new ConnectionPool();
		connection = cp.getConnection();
		return connection;
	}
}
