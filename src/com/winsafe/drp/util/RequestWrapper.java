package com.winsafe.drp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.winsafe.drp.dao.UserManager;
import com.winsafe.hbm.util.StringFilters;


public class RequestWrapper extends HttpServletRequestWrapper {

	private static final String NOPURVIEW = null;

	public RequestWrapper(HttpServletRequest arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public void windrpRequestWrapper(HttpServletRequest request) {
		//super(request);
	}
	
	public void PurviewFilter(String tmpPath, int userid){

		if (UserManager.isPermit(tmpPath, userid)) {

		} else {
			System.out.println("no pass and go filter.do");
			Object request;
			ServletRequest reqeust = null;
			reqeust.getRequestDispatcher(NOPURVIEW);//.forward(request,response);
		}
	}
	
	public String StringFilters (String str){
		String r="";
		r = str.replaceAll(",", "、");
		return r;
	}
	
	public String RequestTool(HttpServletRequest request, String key){
		return key;
		
	}
	
	public String RequestUtil(String str){
		return str;
	}
	
	public String SetCharacterEncodingFilter(String req){
		 String value = req;
		 String re = "";
	        if (value == null)
	        	re = "true";
	        else if (value.equalsIgnoreCase("true"))
	        	re = "true";
	        else if (value.equalsIgnoreCase("yes"))
	        	re = "true";
	        else
	        	re ="true";

	        return re;
	}

	public String getParameter(String string) {
		String value = super.getParameter(string);
		if (value != null && value.indexOf("js") != -1) {			
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
		String endstr="&nbsp";
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
