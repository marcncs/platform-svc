package com.winsafe.hbm.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AjaxDbCondition {
	
	public static String getDBWhereSQL(Map map)throws Exception{
		StringBuffer buf = new StringBuffer("WHERE ");
		Set tmpKeySet = map.keySet();
		for(Iterator tmpIt = tmpKeySet.iterator(); tmpIt.hasNext();){
			Object tmpKey = tmpIt.next();
			Object tmpVal = map.get(tmpKey);
            String tmpValue = null;
            if (!(tmpVal instanceof String)) {
                tmpValue = "" + tmpVal;
            } else {
                tmpValue = (String) tmpVal;
            }
			if(!tmpValue.equals("")&& (tmpValue.trim().length() > 0)&&
                    !tmpValue.trim().equals("all")){
			buf.append("(");
			buf.append(tmpKey +"='"+tmpVal+"'");
			buf.append(")");
            buf.append(" and ");
			}
		}
		String bufStr = buf.toString();
		return bufStr;
	}
}
