package com.winsafe.drp.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils; 
import org.springframework.util.MultiValueMap;

public class ParameterUtil {


	/** 获得对应参数名称的参数值，没有对应参数则返回null */
	public static String getString(HttpServletRequest request, String parameterKey) {
		String value = request.getParameter(parameterKey);
		if (value == null) {
			return null;
		} else {
			return value.trim();
		}
	}

	/** 获得对应参数名称的参数值，没有对应参数则返回指定的默认值 */
	public static String getString(HttpServletRequest request, String parameterKey, String defaultValue) {
		String value = request.getParameter(parameterKey);
		if (value == null) {
			return defaultValue;
		} else {
			return value.trim();
		}
	}

	/** 获得对应参数名称的数组形式的参数值，将其转换成已","分隔的字符串形式，已便于数据库的保存，null返回空字符串 */
	public static String getStringValues(HttpServletRequest request, String s) {
		String[] tempArray = request.getParameterValues(s);
		String temp = "";
		if (tempArray != null) {
			for (int i = 0; i < tempArray.length; i++) {
				if (tempArray[i] != null) {
					if (i == 0) {
						temp = tempArray[i].trim();
					} else {
						temp = temp + "," + tempArray[i].trim();
					}
				}
			}
		}
		return temp;
	}

	/** 获得对应参数名称的参数值,null返回null */
	public static Integer getInteger(HttpServletRequest request, String parameterKey) {
		String value = request.getParameter(parameterKey);
		if (value == null) {
			return null;
		}
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/** 获得REST框架下PUT方式时，对应参数名称的参数值 */
	@Deprecated
	public static Integer getRestPutInteger(MultiValueMap<Integer, String> valueMap, String parameterKey) {
		if (valueMap == null || parameterKey == null) {
			return null;
		}
		List<String> valueList = valueMap.get(parameterKey);
		if (valueList != null && valueList.size() > 0){
//			valueMap.getFirst(parameterKey);
			try {
				return Integer.valueOf(valueList.get(0));
			} catch (NumberFormatException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/** 获得REST框架下PUT方式时，对应参数名称的参数值 */
	@Deprecated
	public static String getRestPutString(MultiValueMap<String, String> valueMap, String parameterKey) {
		if (valueMap == null || parameterKey == null) {
			return null;
		}
		List<String> valueList = valueMap.get(parameterKey);
		if (valueList != null && valueList.size() > 0){
			return valueList.get(0);
		} else {
			return null;
		}
	}


	/** 获得排序字符串,DataTables控件专用 */
	public static String getOrderStr4DataTables(HttpServletRequest request) {
		String sOrder = "";
		Integer sortColCount = ParameterUtil.getInteger(request, "iSortingCols");
		if (sortColCount != null && sortColCount > 0) {
			sOrder = "";
			for (int i = 0; i < sortColCount; i++) {
				Integer colIndex = ParameterUtil.getInteger(request, "iSortCol_" + i); // 列号
				String sortDiretion = ParameterUtil.getString(request, "sSortDir_" + i); // 排序方向
				String isSortable = ParameterUtil.getString(request, "bSortable_" + colIndex);// 是否可排序
				String mDataProp = ParameterUtil.getString(request, "mDataProp_" + colIndex);// 字段名
				if (isSortable.equalsIgnoreCase("true")) {
					sOrder += mDataProp + " " + sortDiretion + ",";
				}
			}
			if (StringUtils.isNotBlank(sOrder)) {
				sOrder = sOrder.substring(0, sOrder.length() - 1);
				sOrder = "order by " + sOrder;
			}
		}
		return sOrder;
	}


}