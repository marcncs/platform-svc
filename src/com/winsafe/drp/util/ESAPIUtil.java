package com.winsafe.drp.util;

import org.owasp.esapi.ESAPI; 

import com.winsafe.common.util.StringUtil;


public class ESAPIUtil {
	public static String decodeForHTML(String value) {
		if(StringUtil.isEmpty(value)) {
			return "";
		}
		return ESAPI.encoder().decodeForHTML(value);
	}
}
