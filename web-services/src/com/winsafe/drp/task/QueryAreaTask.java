package com.winsafe.drp.task;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppQuery;
import com.winsafe.drp.dao.Query;
import com.winsafe.drp.util.HttpUtils;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

public class QueryAreaTask {

	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;
	private static Logger logger = Logger.getLogger(QueryAreaTask.class);

	private AppQuery appQuery = new AppQuery();
	Properties sysPro = null;

	/**
	 * 初始化要处理的任务
	 */
	public void init() throws Exception {
		// 获取配置文件
		sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start Auto Query Task.");
					this.init();
					isRunning = true;
					logger.info(DateUtil.getCurrentDate() + " 自动查询省份任务---开始---");
					execute();
				} catch (Exception e) {
					logger.error(e);
					logger.info(DateUtil.getCurrentDate() + " 自动查询省份发生异常" + e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate() + " 自动查询省份任务---结束---");
				}
			}
		}

	}

	/**
	 * @throws Exception
	 */
	public void execute() throws Exception {
		// 保存查询数据
		Map<String, String> map = null;
		// 保存连接地址
		String url = "";
		// 电话URl
		String telUrl = "";
		// ipUrl
		String ipUrl = "";
		// 从配置文件中获取url
		if (sysPro != null) {
			ipUrl = sysPro.getProperty("urlIpInterfaceAddress");
			telUrl = sysPro.getProperty("urlTelInterfaceAddress");
		}
		List<Query> list = appQuery.selectQueryByArea();
		logger.debug("需要查询的记录数量："+list.size());
		for (Query query : list) {
			map = new LinkedHashMap<String, String>();
			try {
				String queryKey = query.getTelNumber();// 得到查询值
				if (NumberUtil.isNumberic(queryKey)) {
					telQuery(map, telUrl, query, queryKey);// Tel查询
				} else {
					ipQuery(map, ipUrl, query, queryKey);// Ip查询
				}
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				HibernateUtil.rollbackTransaction();
				logger.debug("", e);
			}
		}
	}

	/**
	 * 电话查询
	 * 
	 * @param map
	 * @param ipUrl
	 * @param query
	 * @param queryKey
	 * @throws Exception
	 */
	private void telQuery(Map<String, String> map, String ipUrl, Query query, String queryKey) throws Exception {
		logger.debug("开始查询手机归属地："+queryKey);
		String url;
		url = ipUrl;
		map.put("mobile", queryKey);
		map.put("amount", "10000");
		map.put("callname", "getPhoneNumInfoExtCallback");

		String returnString = HttpUtils.URLGet(url, map, "utf-8");// 执行查询,得到返回值
		logger.debug("得到返回值："+returnString);
		// 假如电话号码错误，则不进行后续操作
		if (returnString.indexOf("{") == -1) {
			return;
		}

		String jasonString = returnString.substring(returnString.indexOf("{"), returnString.lastIndexOf("}") + 1);// 得到正确返回值

		JSONObject json = JSONObject.fromObject(jasonString);// 将返回值转换成jsonObject

		if (json.has("province")) {
			query.setAreas(json.getString("province"));
		} else {
			query.setAreas("未知");
		}
		if (json.has("cityname")) {
			query.setCity(json.getString("cityname"));
		} else {
			query.setCity("未知");
		}
		appQuery.updateQuery(query);// 更新省份
	}

	/**
	 * Ip查询
	 * 
	 * @param map
	 * @param telUrl
	 * @param query
	 * @param queryKey
	 * @throws Exception
	 */
	private void ipQuery(Map<String, String> map, String telUrl, Query query, String queryKey) throws Exception {
		logger.debug("开始查询IP归属地："+queryKey);
		String url;
		url = telUrl;

		// 封装传递数据
		map.put("ip", queryKey);
		map.put("format", "js");

		String returnString = HttpUtils.URLGet(url, map, "utf-8");// 执行查询,得到返回值
		logger.debug("得到返回值："+returnString);
//		// 假如IP错误，则不进行后续操作
//		if (returnString.indexOf("{") == -1) {
//			return;
//		}
//		String jasonString = returnString.substring(returnString.indexOf("{"), returnString.lastIndexOf("}") + 1)
//				.trim();// 得到正确返回值
//
//		JSONObject json = JSONObject.fromObject(jasonString);// 将返回值转换成jsonObject
//		if (json.has("province")) {
//			query.setAreas(json.getString("province"));
//		} else {
//			query.setAreas("未知");
//		}
//		if (json.has("city")) {
//			query.setCity(json.getString("city"));
//		} else {
//			query.setCity("未知");
//		}
		//返回值为空不进行其他操作
		if (StringUtil.isEmpty(returnString)) {
			return;
		}
		
		String[] rs = returnString.split("_");
		if (!StringUtil.isEmpty(rs[0])) {
			query.setAreas(rs[0]);
		}else {
			query.setAreas("未知");
		}
		if (!StringUtil.isEmpty(rs[1])) {
			query.setCity(rs[1]);
		} else {
			query.setCity("未知");
		}
		appQuery.updateQuery(query);// 更新省份
	}

}
