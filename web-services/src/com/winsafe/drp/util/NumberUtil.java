package com.winsafe.drp.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.winsafe.hbm.util.StringUtil;

public class NumberUtil {

	public static Double formatDouble(String d) throws Exception{
		Double f = Double.valueOf(d);
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.valueOf(df.format(f));
	}
	
	public static Double getDouble(String d) throws Exception{
		Double f;
		try {
			f = Double.valueOf(d);
		} catch (Exception e) {
			return 0d;
		}
		return f;
	}
	
	public static Integer getInteger(String d) throws Exception{
		Integer f;
		try {
			f = Integer.valueOf(d);
		} catch (Exception e) {
			return 0;
		}
		return f;
	}
	
	public static String formatDouble(Double a, Double b) {
		if(b==0){
			return "";
		}
		try {
			NumberFormat df = NumberFormat.getInstance();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);
			return df.format(a/b*100);
		} catch (Exception e) {
			return "";
		}
		
	}
	public static Double formatDouble(Double d) throws Exception{
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.valueOf(df.format(d));
	}
	
	public static Integer removeNull(Integer i){
		return i == null ? 0 : i;
	}
	
	public static Double removeNull(Double i){
		return i == null ? 0d : i;
	}
	
	
	/**判断字符串中字符是否全为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumberic(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
}
