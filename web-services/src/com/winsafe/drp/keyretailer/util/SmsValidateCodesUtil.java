package com.winsafe.drp.keyretailer.util;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap; 

import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.StringUtil;

public class SmsValidateCodesUtil {
	private static Map<String, CodeAndTime> validateCodes = new ConcurrentHashMap<String, CodeAndTime>();
	private static Long RANGE = 60000L;
	static {
		try {
			Properties p = JProperties.loadProperties("", JProperties.BY_CLASSLOADER);
			String range = p.getProperty("periodOfCodeValidityInSecond");
			if(!StringUtil.isEmpty(range)) {
				RANGE = Long.valueOf(range) * 1000;
			}
		} catch (IOException e) {
			
		}
	}

	public static boolean isInRange(String mobile) {
		CodeAndTime cat = validateCodes.get(mobile);
		if(cat == null) {
			return false;
		}
		long lastTime = cat.getTime();
		long currentTime = new Date().getTime();
		if((currentTime - lastTime) > RANGE) {
			return false;
		}
		return true;
	}
	
	public static void checkAndRemove() {
		for(String mobile : validateCodes.keySet()) {
			CodeAndTime cat = validateCodes.get(mobile);
			long lastTime = cat.getTime(); 
			long currentTime = new Date().getTime();
			if((currentTime - lastTime) > RANGE) {
				validateCodes.remove(mobile);
			}
		}
	}
	
	public static void addValidateCode(String mobile, String code) {
		CodeAndTime cat = new CodeAndTime(code, new Date().getTime());
		validateCodes.put(mobile, cat);
	}
	
	public static String getValidateCode(String mobile) {
		CodeAndTime cat = validateCodes.get(mobile);
		if(cat == null) {
			return "";
		}
		return cat.getCode();
	}
}
