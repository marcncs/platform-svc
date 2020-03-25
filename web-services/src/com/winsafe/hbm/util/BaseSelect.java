package com.winsafe.hbm.util;
/*
 * Created on 2005-10-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;

import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.PropertysBean;


public class BaseSelect {

	protected static Log log = LogFactory.getLog(Internation.class);
	protected static Locale locale = null;
	private static ResourceBundle rb = null;
	protected static final int NOT_DEFAULT_VALUE = -99999;
	protected static final String NOT_DEFAULT_VALUE_STR = "-99999";
	protected static String i18n = null;

	protected static void init(HttpServletRequest request){
		if("true".equals(i18n)) {
			locale = getCurrentLocale(request);
		} else {
			locale = Locale.SIMPLIFIED_CHINESE;
		}
		try{
			rb = ResourceBundle.getBundle("global.sys.SystemResource", locale);
		}catch( Exception e ){
			log.debug("BaseSelect init error: "+e.getMessage());
		}
	}
	
	protected static void init(HttpServletRequest request, String language){
		if("true".equals(i18n)) {
			locale = getCurrentLocale(request);
		} else {
			locale = Locale.SIMPLIFIED_CHINESE;
		}
		try{
			if("en".equals(language)) {
				rb = ResourceBundle.getBundle("global.sys.SystemResource", locale);
			} else {
				rb = ResourceBundle.getBundle("global.sys.SystemResource_zh", locale);
			}
			
		}catch( Exception e ){
			log.debug("BaseSelect init error: "+e.getMessage());
		}
	}
	

	protected static void init(Locale locale){
		if(!"true".equals(i18n)) {
			locale = Locale.SIMPLIFIED_CHINESE;
		} 
		try{
			rb = ResourceBundle.getBundle("global.sys.SystemResource", locale);
		}catch( Exception e ){
			log.debug("BaseSelect init error: "+e.getMessage());
		}
	}
	

	private static String getProperty(String key){
		if ( key == null || "".equals(key) ){
			log.debug("error: BaseSelect key is null");
			return null;
		}
		return rb.getString(key);
	}
	
	private static PropertysBean getPropertysBean(String arg) throws Exception{
		PropertysBean bean = null;
		if ( arg == null || "".equals(arg) ){
			throw new Exception("error: BaseSelect getPropertysBean arg is null");
		}
		String[] obj = arg.split("\\|");
		if ( obj.length < 2 ){
			throw new Exception("BaseSelect property error:"+arg);
		}
		bean = new PropertysBean();		
		bean.setOrder(obj[0].trim());
		bean.setContent(obj[1].trim());
		return bean;
	}
	
	private static List<PropertysBean> getPBs(String arg) throws Exception {
		List<PropertysBean> pbs = new ArrayList<PropertysBean>();
		try{
			StringTokenizer token = new StringTokenizer(arg, ",");
			while ( token.hasMoreTokens() ){
				String s = token.nextToken().trim();				
				pbs.add(getPropertysBean(s));
			}
		}catch(Exception e ){
			throw new Exception("BaseSelect getPBs error:"+arg);
		}
		return pbs;
	}
	

	private static Locale getCurrentLocale(HttpServletRequest request) {
		//Locale tmpLocale = request.getLocale();
		 HttpSession session = request.getSession();
		 Locale tmpLocale=(Locale) session.getAttribute(Globals.LOCALE_KEY);

		return tmpLocale;
	}
	


	private static String getHtmlOption(List<PropertysBean> list, int position){
		StringBuffer sb = new StringBuffer();
		for ( PropertysBean bean:list ){			
			if ( position == Integer.parseInt(bean.getOrder()) ){				
				sb.append("<option value=\""+bean.getOrder()+"\" selected>").append(bean.getContent()).append("</option>");
			}else{
				sb.append("<option value=\""+bean.getOrder()+"\">").append(bean.getContent()).append("</option>");
			}			
		}
		return sb.toString();
	}
	

	private static String getHtmlOption(List<PropertysBean> list, String position){
		StringBuffer sb = new StringBuffer();
		for ( PropertysBean bean:list ){			
			if ( position.equals(bean.getOrder()) ){				
				sb.append("<option value=\""+bean.getOrder()+"\" selected>").append(bean.getContent()).append("</option>");
			}else{
				sb.append("<option value=\""+bean.getOrder()+"\">").append(bean.getContent()).append("</option>");
			}			
		}
		return sb.toString();
	}
	

	private static String getPleaseOption(HttpServletRequest request){
		String select =  getNameByOrder(request, "OptionSelect", 0);	
		return "<option value=\"\">"+select+"</option>";
	}
	

	protected static String getHtmlSelect(HttpServletRequest request, String selectName, int defaultValue, boolean hasSelect, List<PropertysBean> pbs){
		StringBuffer sb = new StringBuffer();
		sb.append("<select name=\""+selectName).append("\" id=\""+selectName.replaceAll("\\.","_")).append("\">");
		if ( hasSelect ){
			sb.append(getPleaseOption(request));
		}
		sb.append(getHtmlOption(pbs, defaultValue));
		sb.append("</select>");
		return sb.toString();
	}
	

	protected static String getHtmlSelect(HttpServletRequest request, String selectName, String defaultValue, boolean hasSelect, List<PropertysBean> pbs){
		StringBuffer sb = new StringBuffer();
		sb.append("<select name=\""+selectName).append("\" id=\""+selectName.replaceAll("\\.","_")).append("\">");
		if ( hasSelect ){
			sb.append(getPleaseOption(request));
		}
		sb.append(getHtmlOption(pbs, defaultValue));
		sb.append("</select>");
		return sb.toString();
	}
	

	protected static String getValueByOrder(Locale locale, String key, int position){
		String value = null;
		try{
			init(locale);
			List<PropertysBean> pbs = getPBs(getProperty(key));
			for ( PropertysBean bean: pbs ){				
				if ( Integer.parseInt(bean.getOrder()) == position ){
					value = bean.getContent();
					break;
				}					
			}
		}catch ( Exception e ){
			log.error("BaseSelect getValueByOrder error "+e.getMessage());
		}
		return value;
	}
	
	
	public static String getNameByOrder(HttpServletRequest request, String key, int position){
		String value = null;
		try{
			init(request);
			List<PropertysBean> pbs = getPBs(getProperty(key));
			for ( PropertysBean bean: pbs ){				
				if ( Integer.parseInt(bean.getOrder()) == position ){
					value = bean.getContent();
					break;
				}					
			}
		}catch ( Exception e ){
			log.error("BaseSelect getValueByOrder error "+e.getMessage());
		}
		return value;
	}
	
	public static String getNameByOrder(HttpServletRequest request, String key, int position, String language){
		String value = null;
		try{
			init(request, language);
			List<PropertysBean> pbs = getPBs(getProperty(key));
			for ( PropertysBean bean: pbs ){				
				if ( Integer.parseInt(bean.getOrder()) == position ){
					value = bean.getContent();
					break;
				}					
			}
		}catch ( Exception e ){
			log.error("BaseSelect getValueByOrder error "+e.getMessage());
		}
		return value;
	}
	

	public static String getSelect(HttpServletRequest request, String key, String selectName, String i18nFlag){
		i18n = i18nFlag;
		return getSelect(request, key, selectName, false);
	}
	

	public static String getCSelect(HttpServletRequest request, String key, String selectName, String i18nFlag){
		i18n = i18nFlag;
		return getSelect(request, key, selectName, true);
	}
	

	private static String getSelect(HttpServletRequest request, String key, String selectName, boolean hasSelect){
		String selecttag = null;
		try{
			init(request);
			List<PropertysBean> pbs = getPBs(getProperty(key));
			selecttag = getHtmlSelect(request, selectName, NOT_DEFAULT_VALUE, hasSelect, pbs);
		}catch ( Exception e){
			log.error("BaseSelect getSelect error "+e.getMessage());
		}
		
		return selecttag;
	}
	
	
	public static String getDefaultSelect(HttpServletRequest request, String key, String selectName, int defaultValue, String i18nFlag){
		i18n = i18nFlag;
		return getDefaultSelect(request, key, selectName, defaultValue, false);
	}

	public static String getDefaultCSelect(HttpServletRequest request, String key, String selectName, int defaultValue, String i18nFlag){
		i18n = i18nFlag;
		return getDefaultSelect(request, key, selectName, defaultValue, true);
	}

	private static String getDefaultSelect(HttpServletRequest request, String key, String selectName, int defaultValue, boolean hasSelect){
		String selecttag = null;
		try{
			init(request);
			List<PropertysBean> pbs = getPBs(getProperty(key));			
			selecttag = getHtmlSelect(request, selectName, defaultValue, hasSelect, pbs);
		}catch ( Exception e){
			log.error("BaseSelect getDefaultSelect error "+e.getMessage());
		}
		return selecttag;
	}
	
	
}
