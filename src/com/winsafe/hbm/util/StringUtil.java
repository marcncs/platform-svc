package com.winsafe.hbm.util;

public class StringUtil {

	public static String fillBefore(String oldStr, int length, String fillStr) {
		return fillString(oldStr, length, fillStr, true);
	}

	public static String fillBack(String oldStr, int length, String fillStr) {
		return fillString(oldStr, length, fillStr, false);
	}

	/**
	 * 过滤查询关键字，关键字过滤条件不能包含全角或半角的单引号、双引号和百分号等，一般SQL语句用
	 * @Time 2011-8-1 下午01:08:52 create
	 * @param str 查询关键字
	 * @return String 转义后的查询关键字
	 * @author dufazuo
	 */
	public static String replaceStr(String str)
	{
		if (isEmpty(str))
		{
			return "";
		}
		//for sql server
		String replaceStr = str.replace("[", "[[]");// 此句一定要在最前面
		replaceStr = replaceStr.replace("_", "[_]");
		replaceStr = replaceStr.replace("%", "[%]");
		replaceStr = replaceStr.replace("'", "''");
		replaceStr = replaceStr.replace("‘", "\\‘");
		replaceStr = replaceStr.replace("’", "\\’");
		replaceStr = replaceStr.replace("{", "[{]");
		return replaceStr;
	}

	private static String fillString(String oldStr, int length, String fillStr,
			boolean before) {
		String str = (oldStr == null ? "" : oldStr);
		if (str.getBytes().length > length) {
			return substring(str, length, " ");
		}
		while (str.getBytes().length < length) {
			if (before) {
				str = fillStr + str;
			} else {
				str = str + fillStr;
			}
		}
		return str;
	}

	public static boolean isEmpty(String value){
		if(value==null || value.trim().length()==0){
			return true;
		}
		return false;
	}
	public static String removeNull(String obj){
		return obj == null || "null".equals(obj) ? "" : obj.toString();
	}
	
	public static Integer getInt(String obj,Integer defalVal){
		Integer value = defalVal;
		if(obj == null || obj.length()==0 ||"null".equals(obj) ){
			return defalVal;
		}
		try {
			value = Integer.valueOf(obj);
		} catch (NumberFormatException e) {
		}
		return value;
	}
	
	public static Double getDouble(String obj,Double defalVal){
		Double value = defalVal;
		if(obj == null || obj.length()==0 ||"null".equals(obj) ){
			return defalVal;
		}
		try {
			value = Double.valueOf(obj);
		} catch (NumberFormatException e) {
		}
		return value;
	}
	
	public static String substring(String str, int toCount, String more) {   
	    int reInt = 0;   
	    String reStr = "";   
	    if (str == null)   
	        return "";   
	    char[] tempChar = str.toCharArray();   
	    for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {   
	        String s1 = str.valueOf(tempChar[kk]);   
	        byte[] b = s1.getBytes();   
	        reInt += b.length;   
	        if(reInt<=toCount){   
	            reStr += tempChar[kk];   
	        }else{   
	            break;   
	        }   
	    }   
	    if ( toCount == reInt - 1 )   
	        reStr += more;   
	    return reStr;   
	}  
	
	public static String appendStr(String[] str, String fillStr){
		if ( str == null ){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for ( int i=0; i<str.length; i++ ){
			if ( i== 0 ){
				sb.append("'").append(str[i]).append("'");
			}else{
				sb.append(fillStr).append("'").append(str[i]).append("'");
			}			
		}
		return sb.toString();
	}

	public static String fillZero(Integer qty,int length){
		String zeroStr = "";
		for (int i = 0; i < (length - qty.toString().length()); i++) {
			zeroStr += "0";
		}
		zeroStr += (qty.toString());
		return zeroStr;
	}
 
	/**
	 * 把数组转换成以逗号分隔的字符串，从指定元素开始，并去除末尾的逗号
	 * @param array
	 * @param start 指定开始元素
	 * @param hasQuote
	 *            字符串是否要用单引号括起来
	 * @return
	 */
	public static String getString(Object[] array, int start, boolean hasQuote) {
		if(array == null || array.length == 0){
			return "";
		}
		
		StringBuilder sql = new StringBuilder();
		boolean isString = false;
		// 判断是否是字符串数组
		if (array[0] instanceof String) {
			isString = true;
		}
		// 合并数组
		for (int i = start; i < array.length; i++) {
			if (isString && hasQuote) {
				sql.append("'");
			}
			sql.append(array[i]);
			if (isString && hasQuote) {
				sql.append("'");
			}

			if (i != array.length - 1) {
				sql.append(",");
			}
		}
		return sql.toString();
	}
	
	public static String parseString(Object value,String defaultValue){
		if(value==null)return defaultValue;
		try{
			if(value instanceof String){
				return (String)value;
			}
			return value.toString();
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static String abbreviate(String str, int maxWidth)
    {
        return abbreviate(str, 0, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth)
    {
        if(str == null)
            return null;
        if(maxWidth < 4)
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        if(str.length() <= maxWidth)
            return str;
        if(offset > str.length())
            offset = str.length();
        if(str.length() - offset < maxWidth - 3)
            offset = str.length() - (maxWidth - 3);
        if(offset <= 4)
            return str.substring(0, maxWidth - 3) + "...";
        if(maxWidth < 7)
            throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
        if(offset + (maxWidth - 3) < str.length())
            return "..." + abbreviate(str.substring(offset), maxWidth - 3);
        else
            return "..." + str.substring(str.length() - (maxWidth - 3));
    }
}
