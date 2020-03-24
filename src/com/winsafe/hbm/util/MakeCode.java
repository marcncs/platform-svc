
package com.winsafe.hbm.util;

import java.text.NumberFormat;
import java.util.Calendar;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.RedisUtil;
import com.winsafe.drp.util.WfLogger;

import redis.clients.jedis.Jedis;


public class MakeCode {

	private static final String KEY_PREFIX ="tableId:";
	private static final int ID_LENGTH = 4;
	private static final String PRO_FIX = "0";
	private static final String ORG_FIX = "";
	private static final int PRO_EXTEND = 5;
	private static final int ORG_EXTEND = 8;

	public static String getExcIDByRandomTableName(
			String tableName, int random, String titleName) throws Exception {
		String resultValue = "";
		try (Jedis jedis = RedisUtil.getResource()){
			if (random == 1) {
				resultValue = getExecuteTableID(tableName, jedis);
			} else if (random == 2) {
				resultValue = getYMDTableID(tableName, jedis, titleName);
			} else if (random == 3) {
				resultValue = getExecuteCharTableID(tableName, jedis, titleName);
			} else if (random ==4){ 
				resultValue = getLimitTableID(tableName, jedis);
			}else {
				resultValue = getExecuteNoRandomTableID(tableName, jedis);
			}
		} catch (Exception ex) {
			WfLogger.error("", ex);
			throw ex;
		}
		return resultValue;
	}

	public static String getExecuteTableID(String tabName, Jedis jedis)
			throws Exception {
		return getRandom() + "" + getTableId(tabName, jedis);
	}


	public static String getExecuteNoRandomTableID(String tabName, Jedis jedis) throws Exception {
		return getTableId(tabName, jedis);
	}


	public static String getExecuteCharTableID(String tabName, Jedis jedis,
			String titleName) throws Exception {
		return titleName + getTableId(tabName, jedis);
	}


	private static String getTableId(String tabName, Jedis jedis) throws Exception {
		String key = KEY_PREFIX+tabName;
		if(jedis.get(key) == null) {
			initTableId(jedis, tabName, key);
		}
		return jedis.incr(key).toString();
	}

	public static String getYMDTableID(String tabName, Jedis jedis,
			String titleName) throws Exception {
		String ymd = getCurrentDateString();
		String key = KEY_PREFIX+tabName+":"+ymd;
		String prefix = titleName+ymd;
		if(jedis.get(key) == null) {
			initYMDTableId(jedis, tabName, key, prefix);
		}
		String id = jedis.incr(key).toString();
		return prefix+Constants.ZERO_PREFIX[ID_LENGTH-id.length()]+id;
	}
	
	private static void initYMDTableId(Jedis jedis, String tabName, String key, String prefix) {
		String id = getMaxIDFromTable(tabName, prefix);
		if(StringUtil.isEmpty(id)) {
			jedis.set(key,"0","nx", "ex", 24*60*60);
		} else {
			jedis.set(key,Integer.valueOf(id.substring(id.length()-ID_LENGTH)).toString(),"nx", "ex", 24*60*60);
		}
	}
	
	private static void initTableId(Jedis jedis, String tabName, String key) throws Exception {
		String id = getMaxIDFromTable(tabName);
		if(StringUtil.isEmpty(id)) {
			jedis.set(key,"0","nx");
		} else {
			if("product".equals(tabName)) {
				jedis.set(key,Integer.valueOf(id).toString(),"nx");
			} else {
				jedis.set(key,id,"nx");
			}
			
		}
	}

	private static String getMaxIDFromTable(String tableName) {
		String sql = "select max(id) id from "+tableName;
		return EntityManager.getStringBySql(sql);
	}

	private static String getMaxIDFromTable(String tableName, String prefix) {
		String sql = "select max(id) id from "+tableName+" where id like '"+prefix+"%'";
		return EntityManager.getStringBySql(sql);
	}

	public static String getLimitTableID(String tabName, Jedis jedis)throws Exception{
		Long curNo = Long.valueOf(getTableId(tabName, jedis));
		Integer obj3 = 0;
		String obj2 = "";
		if("product".equals(tabName)) {
			obj3 = PRO_EXTEND;
			obj2 = PRO_FIX;
		} else {
			obj3 = ORG_EXTEND;
			obj2 = ORG_FIX;
		}
		NumberFormat df = NumberFormat.getNumberInstance();
		df.setMinimumIntegerDigits(obj3-
				obj2.length());
		df.setGroupingUsed(false);
		return obj2+df.format(curNo);
	}

	public static int getRandom() throws Exception {
		int a = (int) (Math.random() * (11 - 99)) + 99;
		return a;
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

	public static String getFormatNums(Integer id, int length) {
		NumberFormat df = NumberFormat.getNumberInstance();
		df.setMinimumIntegerDigits(length);
		df.setGroupingUsed(false);
		return df.format(id);
	}
	
}
