package com.winsafe.hbm.filters;

import java.util.HashMap; 
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.owasp.esapi.ESAPI;
public class XSSRequestWrapper extends HttpServletRequestWrapper {
    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }
    
    
    @Override
    public Map<String, String[]> getParameterMap() {
    	Map<String, String[]> valuesMap = super.getParameterMap();
        if (valuesMap == null
        		|| valuesMap.size() == 0) {
            return valuesMap;
        }
        
        Map<String, String[]> paraMap = new HashMap<>();
        for (String key : valuesMap.keySet()) {
        	String[] values = valuesMap.get(key);
        	key = stripXSS(key);
        	if(values!=null && values.length > 0) {
        		for (int i = 0; i < values.length; i++) {
        			values[i] = stripXSS(values[i]);
            	}
        	}
        	paraMap.put(key, values);
        }
        return paraMap;
    }
    
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }
        return encodedValues;
    }
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        //密码字段不转码
        if(parameter.toLowerCase().indexOf("password") != -1
        		|| parameter.toLowerCase().indexOf("range") != -1
        		|| parameter.toLowerCase().indexOf("affichecontent") != -1) { 
        	return value; 
    	}
        value = stripXSS(value);
        return value;
    }
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        value = stripXSS(value);
        return value; 
    }
    private String stripXSS(String value) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
//        	value = ESAPI.encoder().canonicalize(value);
//        	System.out.println(value);
        	value = ESAPI.encoder().canonicalize(value);
//        	value = ESAPI.encoder().encodeForHTMLAttribute(value);
//        	System.out.println(value);
//        	value = ESAPI.encoder().encodeForJavaScript(value);
            // Avoid null characters
            value = value.replaceAll("", "");
            // Avoid anything between script tags
         // Avoid anything between script tags
		    Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            
		    scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
            scriptPattern = Pattern.compile("xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
         // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            
            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
            // Avoid eval(...) e­xpressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
            // Avoid javascript:... e­xpressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            
            // Avoid vbscript:... e­xpressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            
            // Avoid onload= e­xpressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
            // Avoid null characters
			value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//		    value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		    value = value.replaceAll("'", "&#39;");
            
            
        }
        return value;
    }
    
}