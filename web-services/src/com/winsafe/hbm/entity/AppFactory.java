package com.winsafe.hbm.entity;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 * App工厂类
 */
public class AppFactory {
	
	private static  Map<String, Object> appMap = new HashMap<String, Object>();
	
	private static Logger logger = Logger.getLogger(AppFactory.class);
	
	public static <T>  T getApp(Class<T> appClazz) {
		Object obj = null;
		if(appClazz != null){
			obj = appMap.get(appClazz.getName());
			if (obj == null) {
				try {
					obj = appClazz.newInstance();
					appMap.put(appClazz.getName(), obj);
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		}
		return (T) obj;
	}
}
