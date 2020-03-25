package com.winsafe.drp.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Calendar;

import com.winsafe.hbm.entity.ConnectionPool;

public class Makecode {

	//private static ConnectionPool cp = new ConnectionPool();

	public synchronized static String getExcIDByRandomTableName(String tableName,int random,String titleName)
			throws Exception {
		ConnectionPool cp = new ConnectionPool();
		Connection conn = null;
		String resultValue = "";
		try {
			//conn = ConnectionManager.getHAloneConnection();
			conn = cp.getConnection();
			if(random==1){
				resultValue = getExecuteTableID(tableName, conn);
			}else if(random==2){
				resultValue = getYMDTableID(tableName,conn,titleName);
			}else if(random==3){
				resultValue = getExecuteCharTableID(tableName, conn,titleName);
			}else{
				resultValue = getExecuteNoRandomTableID(tableName, conn);
			}

		} catch (Exception ex) {
			throw ex;
		} finally {
			cp.freeConnection(conn);
		}
		return resultValue;
	}


	public static String getExecuteTableID(String tabName, Connection conn)
			throws Exception {
		Statement stmt = null;
		ResultSet rst = null;
		String resultValue = "";
		String updsql = " update make_conf set CurrentValue = CurrentValue+1 where TableName='"
				+ tabName + "'";
		String schsql = " select CurrentValue from make_conf where TableName = '"
				+ tabName + "'";

		try {
			conn.setAutoCommit(false);
			//conn.setAutoCommit(true);
			stmt = conn.createStatement();
			rst = stmt.executeQuery(schsql);
			while (rst.next()) {
				resultValue = rst.getString(1);
			}
			stmt.executeUpdate(updsql);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		resultValue = getRandom() + "" + resultValue;
		return resultValue;
	}


//	public synchronized static Long getExcIDByNoRandomTableName(String tableName)
//			throws Exception {
//		ConnectionPool cp = new ConnectionPool();
//		Connection conn = null;
//		Long resultValue = null;
//		try {
//			//conn = ConnectionManager.getHAloneConnection();
//			conn = cp.getConnection();
//			resultValue = getExecuteNoRandTableID(tableName, conn);
//
//		} catch (Exception ex) {
//			throw ex;
//		} finally {
//			cp.freeConnection(conn);
//		}
//		return resultValue;
//	}
	


	public static String getExecuteNoRandomTableID(String tabName, Connection conn)
			throws Exception {
		Statement stmt = null;
		ResultSet rst = null;
		String resultValue = "";
		String updsql = " update make_conf set CurrentValue = CurrentValue+1 where TableName='"
				+ tabName + "'";
		String schsql = " select CurrentValue from make_conf where TableName = '"
				+ tabName + "'";
		try {
			conn.setAutoCommit(false);
			//conn.setAutoCommit(true);
			stmt = conn.createStatement();
			rst = stmt.executeQuery(schsql);
			while (rst.next()) {
				resultValue = rst.getString(1);
			}
			stmt.executeUpdate(updsql);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return resultValue;
	}
	

	public static String getExecuteCharTableID(String tabName, Connection conn,String titleName)
			throws Exception {
		Statement stmt = null;
		ResultSet rst = null;
		String resultValue = "";
		String updsql = " update make_conf set CurrentValue = CurrentValue+1 where TableName='"
				+ tabName + "'";
		String schsql = " select CurrentValue from make_conf where TableName = '"
				+ tabName + "'";
		try {
			conn.setAutoCommit(false);
			//conn.setAutoCommit(true);
			stmt = conn.createStatement();
			rst = stmt.executeQuery(schsql);
			while (rst.next()) {
				resultValue = titleName+rst.getString(1);
			}
			stmt.executeUpdate(updsql);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return resultValue;
	}


	public static int getRandom() throws Exception {
		int a = (int) (Math.random() * (1111 - 9999)) + 9999;
		return a;
	}
	

	public static String getYMDTableID(String tabName, Connection conn,String titleName)
			throws Exception {
		Statement ystmt = null;
		String strcd = getCurrentDateString();
		ResultSet yst = null;
		String ymdValue = "";
		String tmpymdValue="";
		int noValue = 0;
		String strNo = "";
		String resultValue = "";
		String sql = " select CurrentValue from make_conf where TableName = '"+ tabName + "'";

		try {
			conn.setAutoCommit(false);
			ystmt = conn.createStatement();
			yst = ystmt.executeQuery(sql);
			while (yst.next()) {
				ymdValue = yst.getString(1);
			}
			tmpymdValue = ymdValue.substring(0, 8);

			if (strcd.equals(tmpymdValue)) {
				noValue = Integer.valueOf(ymdValue.substring(8, 12)) + 1;
				NumberFormat df = NumberFormat.getNumberInstance();
				df.setMinimumIntegerDigits(4);
				df.setGroupingUsed(false);
				strNo = df.format(noValue);
				resultValue = strcd + strNo;

			} else {
				resultValue = strcd + "0001";
			}

			String updsql = " update make_conf set CurrentValue = "
					+ resultValue + " where TableName='" + tabName + "'";
			ystmt.executeUpdate(updsql);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return titleName+resultValue;

	}

	public static String getCurrentDateString() {
		String currentTime = "";
		try {
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
					"yyyyMMdd");
			Calendar currentDate = Calendar.getInstance();
			currentTime = format.format(currentDate.getTime());
		} catch (Exception e) {
			return null;
		}
		return currentTime;
	}
	
	public static String getFormatNums(Integer id, int length){		
		NumberFormat df = NumberFormat.getNumberInstance();
		df.setMinimumIntegerDigits(length);
		df.setGroupingUsed(false);
		return df.format(id);
	}
}
