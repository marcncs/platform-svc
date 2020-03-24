package com.winsafe.hbm.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.hbm.util.BaseSelect;
import com.winsafe.hbm.util.PropertysBean;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.server.BaseResourceService;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class HtmlSelect extends BaseSelect {

	
	
	
	private static Object[] getBaseSource(String tagname){
		Object[] obj = new Object[2];
		List<PropertysBean> pbs = new ArrayList<PropertysBean>();
		BaseResourceService abs = new BaseResourceService();
		int defaultvalue = NOT_DEFAULT_VALUE;
    	try{
    		List ls = abs.getBaseResourceIsUse(tagname);
    		PropertysBean pb = null;
    		for (int i=0; i<ls.size(); i++ ){
    			BaseResource br = (BaseResource)ls.get(i);
    			pb = new PropertysBean();
    			pb.setOrder(br.getTagsubkey().toString());    			
    			pb.setContent(br.getTagsubvalue());
    			
    			if ( br.getIsdefault()!= null && br.getIsdefault() == 1 ){
    				defaultvalue = br.getTagsubkey();
    			}
    			pbs.add(pb);    			
    		}    		
    	}catch ( Exception e ){
    		e.printStackTrace();
    		log.error("HtmlSelect getBaseSource() error:"+e.toString());
    	}	
    	obj[0] = pbs;
    	obj[1] = defaultvalue;
    	return obj;
	}
	
	private static String getBaseResourceValue(String tagname, int position){
		BaseResourceService abs = new BaseResourceService();
		String value = "";
    	try{
    		BaseResource br = abs.getBaseResourceValue(tagname, position);
			value = br.getTagsubvalue();    		
    	}catch ( Exception e ){
    		e.printStackTrace();
    		log.error("HtmlSelect getBaseResourceValue() error:"+e.toString());
    	}		
    	return value;
	}
	

	public static String getResourceName(HttpServletRequest request, String tagName, int position){
		String value = null;
		try{
			init(request);
			value = getBaseResourceValue(tagName, position);
		}catch ( Exception e ){
			log.error("HtmlSelect getBaseResourceName error "+e.getMessage());
		}
		return value;
	}

	public static String getResourceSelect(HttpServletRequest request, String tagName, String selectName){
		return getResourceSelect(request, tagName, selectName, false);
	}

	public static String getResourceCSelect(HttpServletRequest request, String tagName, String selectName){
		return getResourceSelect(request, tagName, selectName, true);
	}

	private static String getResourceSelect(HttpServletRequest request, String tagName, String selectName, boolean hasSelect){
		String selecttag = null;
		try{
			init(request);
			Object[] obj = getBaseSource(tagName);
			List<PropertysBean> pbs = (List<PropertysBean>)obj[0];
			int defaultvalue = 1;
			selecttag = getHtmlSelect(request, selectName, defaultvalue, hasSelect, pbs);
		}catch ( Exception e){
			log.error("HtmlSelect getBaseResourceSelect error "+e.getMessage());
		}		
		return selecttag;
	}
	

	public static String getDefaultResourceSelect(HttpServletRequest request, String tagName, String selectName, int defaultValue){
		return getDefaultResourceSelect(request, tagName, selectName, defaultValue, false);
	}	

	public static String getDefaultResourceCSelect(HttpServletRequest request, String tagName, String selectName, int defaultValue){
		return getDefaultResourceSelect(request, tagName, selectName, defaultValue, true);
	}

	private static String getDefaultResourceSelect(HttpServletRequest request, String tagName, String selectName, int defaultValue, boolean hasSelect){
		String selecttag = null;
		try{
			init(request);
			Object[] obj = getBaseSource(tagName);
			List<PropertysBean> pbs = (List<PropertysBean>)obj[0];			
			selecttag = getHtmlSelect(request, selectName, defaultValue, hasSelect, pbs);
		}catch ( Exception e){
			log.error("HtmlSelect getDefaultBaseResourceSelect error "+e.getMessage());
		}
		return selecttag;
	}
	
}
