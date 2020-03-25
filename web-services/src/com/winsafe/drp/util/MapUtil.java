package com.winsafe.drp.util;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;

import com.winsafe.hbm.util.DateUtil;

public class MapUtil {
	
	private static Logger logger = Logger.getLogger(MapUtil.class);
	/**
	 * 将map中对应的值赋值结对应的类 
	 * @param map
	 * @param obj
	 * @throws Exception
	 */
	public static void mapToObject(Map map, Object obj) throws Exception {
		Method[] methods = obj.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				String key = method.getName().replace("set", "").toLowerCase();
				Object object = map.get(key);
				Class[] clazz = method.getParameterTypes();
				if (clazz == null || clazz.length != 1) {
					continue;
				}
				if ("java.lang.String".equals(clazz[0].getName())
						|| "java.lang.Integer".equals(clazz[0].getName())
						|| "java.lang.Double".equals(clazz[0].getName())
						|| "java.lang.Date".equals(clazz[0].getName())
						|| "java.util.Date".equals(clazz[0].getName()) ) {
					if ("java.lang.String".equals(clazz[0].getName())) {
						if (object == null) {
							object = "";
						}
					}else if ("java.lang.Integer".equals(clazz[0].getName())) {
						if (object == null) {
							object = 0;
						} else {
							object = Integer.valueOf((String)object);
						}
					}else if ("java.lang.Date".equals(clazz[0].getName())) {
						
						if (object == null) {
							object = null;
						} else {
							object = Dateutil.formatDatetime((String)object);
						}
						
					}else if ("java.lang.Double".equals(clazz[0].getName())) {
						if (object == null) {
							object = 0d;
						} else {
							object = Double.valueOf((String)object);
						}
					}
					else if ("java.util.Date".equals(clazz[0].getName())) { //tommy add for java.util.Date
						if (object == null) {
							object = null;
						} else {
							object = Dateutil.formatDatetime((String)object);
						}
					}
					
					try {
						method.invoke(obj, object);
					} catch (Exception e) {
						logger.debug(method.getName(),e);
					}
					
				}

			}
		}
	}
	
	public static void mapToObjectIgnoreCase(Map map, Object obj) throws Exception {
		Method[] methods = obj.getClass().getMethods();
		Map<String,Method> methodMap = new HashMap<String,Method>();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				String key = method.getName().replace("set", "").toLowerCase();
				methodMap.put(key, method);
			}
		}
		for(Object objKey : map.keySet()) {
			Object object = map.get(objKey);
			Method method = methodMap.get(objKey.toString().toLowerCase());
			if(method == null) {
				continue;
			}
			Class[] clazz = method.getParameterTypes();
			if (clazz == null || clazz.length != 1) {
				continue;
			}
			if ("java.lang.String".equals(clazz[0].getName())
					|| "java.lang.Integer".equals(clazz[0].getName())
					|| "java.lang.Double".equals(clazz[0].getName())
					|| "java.lang.Date".equals(clazz[0].getName())
					|| "java.util.Date".equals(clazz[0].getName())
					|| "java.lang.Long".equals(clazz[0].getName())) {
				if ("java.lang.String".equals(clazz[0].getName())) {
					if (object == null) {
						object = "";
					}
				}else if ("java.lang.Integer".equals(clazz[0].getName())) {
					if (object == null) {
						object = 0;
					} else {
						object = Integer.valueOf((String)object);
					}
				}else if ("java.lang.Long".equals(clazz[0].getName())) {
					if (object == null) { 
						object = 0l;
					} else {
						object = Long.valueOf((String)object);
					}
				}else if ("java.lang.Date".equals(clazz[0].getName())) {
					
					if (object == null) {
						object = null;
					} else {
						object = Dateutil.formatDatetime((String)object);
					}
					
				}else if ("java.lang.Double".equals(clazz[0].getName())) {
					if (object == null) {
						object = 0d;
					} else {
						object = Double.valueOf((String)object);
					}
				}
				else if ("java.util.Date".equals(clazz[0].getName())) { //tommy add for java.util.Date
					if (object == null) {
						object = null;
					} else {
						object = Dateutil.formatDatetime((String)object);
					}
				}
				
				try {
					method.invoke(obj, object);
				} catch (Exception e) {
					logger.debug(method.getName(),e);
				}
			}
		}
	}
	
	public static Map<String, String> objectToMap(Object obj) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Method[] methods = obj.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get")) {
				String key = method.getName().replace("get", "").toLowerCase();
				try {
					Object result = method.invoke(obj);
					String value = "";
					if(result instanceof Date) {
						value = DateUtil.formatDate((Date)result, "yyyy-MM-dd");
					} else {
						if(result != null) {
							value = result.toString();
						}
					}
					map.put(key, value);
				} catch (Exception e) {
					logger.debug(method.getName(),e);
				}
			}
		}
		return map;
	}
	/**
	 * 将所有key值转为小写
	 */
	public static Map<String,Object> changeKeyLow(Map<String,Object> map){
		Map<String,Object> result = new HashMap<String, Object>();
		for(String key : map.keySet()){
			if(key != null){
				result.put(key.toLowerCase(), map.get(key));
			}
		}
		return result;
	}

	public static Map changeStringKeyLow(Map<String, String[]> map) {
		Map<String,Object> result = new HashMap<String, Object>();
		for(String key : map.keySet()){
			if(key != null){
				result.put(key.toLowerCase(), map.get(key));
			}
		}
		return result;
	}
}
