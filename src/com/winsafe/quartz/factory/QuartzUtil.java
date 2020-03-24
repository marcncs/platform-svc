package com.winsafe.quartz.factory;

public class QuartzUtil {
	
	public static String join(Object[] array, String separator) {

		int len = array.length;
		if (array == null || len == 0) {
			// 为空
			return "";
		}

		StringBuffer result = new StringBuffer();
		for (int i = 0; i < len - 1; i++) {
			// 循环添加
			result.append(array[i].toString());
			result.append(separator);
		}
		// 添加最后一个
		result.append(array[len - 1]);

		return result.toString();
	}
	
}
