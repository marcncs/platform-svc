package com.winsafe.hbm.filters;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.winsafe.hbm.util.StringFilters;


public class HbmRequestWrapper extends HttpServletRequestWrapper {

	public HbmRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public String getParameter(String string) {
		String value = super.getParameter(string);
		if (value != null && value.indexOf("scripts") != -1) {			
			value = StringFilters.filter(value);
		}
		return value;
	}
	
	public String[] getParameterValues(String name) {    
          String values[] = super.getParameterValues(name);    
          if (values != null) {    
            for (int i = 0; i < values.length; i++) {    
            	values[i] = StringFilters.filter(values[i]);
            }    
          }    
          return values;    
    }    
	
	public Map getParameterMap(){
		Map pOrgMap = new HashMap(super.getParameterMap());
		Map tmpRstMap = new HashMap();
		Set tmpKeySet = pOrgMap.keySet();
		String endstr="";
		for (Iterator tmpIt = tmpKeySet.iterator(); tmpIt.hasNext();) {
			
			Object tmpKey = tmpIt.next();
			Object tmpVal = pOrgMap.get(tmpKey);			
			if (tmpVal instanceof Object[]) {
				tmpVal = ((Object[]) tmpVal)[0];
				endstr = StringFilters.filter((String)tmpVal);
			}
			tmpRstMap.put(tmpKey, endstr);
		}
		return tmpRstMap;
	}


}
